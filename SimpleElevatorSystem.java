import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Iterator;

interface ElevatorControlSystem {
    public static final int UP = 1;
    public static final int DOWN = -1;
    public static final int MAX_CAPACITY = 10;

    public void printStatus();

    public void update(int elevatorId, int goalFloor);

    public void pickUp(int pickupFloor, int direction);

    public void step();

    class Status {
        int elevatorId;
        int currentFloor;
        TreeSet<Integer> goalFloors;

        public Status(int elevatorId, int currentFloor, TreeSet<Integer> goalFloors) {
            this.elevatorId = elevatorId;
            this.currentFloor = currentFloor;
            this.goalFloors = goalFloors;
        }

        @Override
        public String toString() {
            return "Elevator : " + elevatorId
                    + " \tcurrentFloor : " + currentFloor
                    + " \ngoalFloors : \n" + goalFloors.toString();
        }
    }
}

class Elevator {
    int id;
    int currentFloor;
    int capacity;
    TreeSet<Integer> goalFloors;
    int direction;

    public Elevator(int elevatorId) {
        this.id = elevatorId;
        currentFloor = 0;
        capacity = 0;
        goalFloors = new TreeSet<>();
        direction = ElevatorControlSystem.UP;
    }
}

class ElevatorControlSystemImpl implements ElevatorControlSystem {

    public int MAX_FLOORS;
    Elevator[] elevators;

    //Need not be concurrent. volatile is sufficient condition.
    volatile TreeSet<Integer> upRequests = new TreeSet<Integer>();
    volatile TreeSet<Integer> downRequests = new TreeSet<Integer>();

    public ElevatorControlSystemImpl(int numOfElevators, int MAX_FLOORS) {
        this.elevators = new Elevator[numOfElevators];
        for (int i = 0; i < numOfElevators; i++) {
            elevators[i] = new Elevator(i);
        }
        this.MAX_FLOORS = MAX_FLOORS;
    }

    public void printStatus() {
        System.out.println();
        Status[] st = status();
        int[][] config = new int[MAX_FLOORS][elevators.length];
        for (Status s : st) {
            config[s.currentFloor][s.elevatorId] = 1;
            for (Integer x : s.goalFloors) {
                config[x][s.elevatorId] = 9;
            }
        }
        for (int i = config.length - 1; i >= 0; i--) {
            System.out.println(Arrays.toString(config[i]));
        }
    }

    Status[] status() {
        Status[] status = new Status[elevators.length];
        for (int i = 0; i < elevators.length; i++) {
            Elevator e = elevators[i];
            status[i] = new Status(e.id, e.currentFloor, e.goalFloors);
        }
        return status;
    }

    public void update(int elevatorId, int goalFloor) {
        Elevator e = elevators[elevatorId];
        e.goalFloors.add(goalFloor);
    }

    /*
    To pick up a passenger, the algorithm can consider three options.
        1) An elevator is right now at the pickUpFloor. Just open its doors.
        2) n elevators are halted at n floors. Choose one amongst them to travel
            toward the pickUpFloor. Pick the nearest possible elevator.
        3) m elevators are in transit. Add the current destination to all transit
            elevators. We add this to all the elevators because we do not know which
            one will arrive first.
    */

    public void pickUp(int pickUpFloor, int direction) {

        for (Elevator e : elevators) {
            if (e.currentFloor == pickUpFloor) {
                //Open door and break. Nothing more to do.
                //Covers cases of door just about to close or open as well.
                return;
            }
        }

        if (direction > 0) upRequests.add(pickUpFloor);
        else downRequests.add(pickUpFloor);
    }

    boolean assignToBestIdle(Integer request) {

        Integer bestId = null;
        int minDelta = Integer.MAX_VALUE;

        for(Elevator e : elevators) {
            if(e.goalFloors.size() > 0) continue;
            if(minDelta < Math.abs(e.currentFloor - request)) {
                minDelta = Math.abs(e.currentFloor - request);
                bestId = e.id;
            }
        }

        if (bestId != null) {
            elevators[bestId].goalFloors.add(request);
            return true;
        }
        return false;
    }

    // Move to next possible configuration.
    public void step() {
        
        for (Iterator iterator = upRequests.iterator(); iterator.hasNext();) {
            if(assignToBestIdle((Integer) iterator.next()))
                iterator.remove();
        }

        for (Iterator iterator = downRequests.iterator(); iterator.hasNext();) {
            if(assignToBestIdle((Integer) iterator.next()))
                iterator.remove();
        }

        for (Elevator e : elevators) {

            if (e.goalFloors.size() == 0) {
                /*
                This elevator is empty. Make it move towards the nearest
                request either up or down.
                 */
                int myFloor = e.currentFloor;

                if (upRequests.contains(myFloor)) {
                    upRequests.remove(myFloor);
                    continue;
                }

                if (downRequests.contains(myFloor)) {
                    downRequests.remove(myFloor);
                    continue;
                }

                //Find the minimum delta among above four values and move
                // towards that point.
                if (upRequests.size() == 0 && downRequests.size() == 0) {
                    continue; // Do nothing.
                } else if (e.currentFloor == 0) {
                    e.direction = UP;
                    e.currentFloor++;
                } else if (e.currentFloor == MAX_FLOORS - 1) {
                    e.direction = DOWN;
                    e.currentFloor--;
                } else {
                    int upDelta = Integer.MAX_VALUE;
                    int downDelta = Integer.MAX_VALUE;

                    if (upRequests.ceiling(myFloor) != null) {
                        upDelta = upRequests.ceiling(myFloor) - myFloor;
                    }

                    if (downRequests.ceiling(myFloor) != null) {
                        upDelta = Math.min(upDelta, downRequests.ceiling
                                (myFloor));
                    }

                    if (upRequests.floor(myFloor) != null) {
                        downDelta = myFloor - upRequests.floor(myFloor);
                    }

                    if (downRequests.floor(myFloor) != null) {
                        downDelta = Math.min(downDelta, myFloor - downRequests
                                .floor((myFloor)));
                    }

                    if (upDelta < downDelta) {
                        e.direction = UP;
                        e.currentFloor++;
                    } else if (downDelta < upDelta) {
                        e.direction = DOWN;
                        e.currentFloor--;
                    }
                }
            } else if (e.direction == UP) {

                if (e.currentFloor == MAX_FLOORS - 1) {
                    e.goalFloors.remove(MAX_FLOORS - 1);
                    downRequests.remove(MAX_FLOORS - 1);
                    e.direction = DOWN;
                } else {
                    if (e.goalFloors.ceiling(e.currentFloor) == null) {
                        e.direction = DOWN;
                        e.currentFloor--;
                    } else {
                        //Going up. Increase current floor count.
                        e.currentFloor++;

                        //Should I stop at next floor ?
                        if (e.goalFloors.contains(e.currentFloor)) {
                            //Open doors and let the passengers in/out.
                            e.goalFloors.remove(e.currentFloor);
                            //New passengers may enter here. When they press their floor button,
                            //invoke the update method for this elevator id.
                        }

                        //Close the request as no other elevator has to travel towards this location.
                        if (upRequests.contains(e.currentFloor)) {
                            upRequests.remove(e.currentFloor);
                        }
                    }
                }
            } else if (e.direction == DOWN) {

                if (e.currentFloor == 0) {
                    e.goalFloors.remove(0);
                    upRequests.remove(0);
                    e.direction = UP;
                } else {
                    if (e.goalFloors.floor(e.currentFloor) == null) {
                        e.direction = UP;
                        e.currentFloor++;
                    } else {
                        e.currentFloor--;

                        if (e.goalFloors.contains(e.currentFloor))
                            e.goalFloors.remove(e.currentFloor);

                        if (downRequests.contains(e.currentFloor))
                            downRequests.remove(e.currentFloor);
                    }
                }
            }
        }
    }
}

public class SimpleElevatorSystem {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========================================");
        System.out.println("Available commands : [pick] or [update]");
        System.out.println("pick needs a floor number and direction");
        System.out.println("update needs elevatorId and goalFloor");
        System.out.println("========================================");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of elevators : ");
        int elevators = sc.nextInt();
        System.out.println("Enter number of floors : ");
        int maxFloors = sc.nextInt();
        ElevatorControlSystemImpl sys = new ElevatorControlSystemImpl(elevators,
                maxFloors);
        sc.nextLine();
        sys.printStatus();

        while (true) {
            System.out.println("Input command [Enter to step]: ");
            String cmd = sc.nextLine();
            switch (cmd) {
                case "pick":
                    int floor = sc.nextInt();
                    int dir = sc.nextInt();
                    sys.pickUp(floor, dir);
                    break;
                case "update":
                    int ele = sc.nextInt();
                    int goal = sc.nextInt();
                    sys.update(ele, goal);
                    break;
                case "exit":
                    System.exit(0);
                default:
                    sys.step();
            }
            sys.printStatus();
        }
    }

}
