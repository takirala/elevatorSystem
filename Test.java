
class Test {
    public static void main(String[] args) {
        int elevators = 4;
        int floors = 8;
        System.out.println("System is created with " + elevators + " elevators and " + floors + " floors");

        ElevatorControlSystemImpl sys = new ElevatorControlSystemImpl(elevators, floors);
        testIdle(sys);
        testPickUp(sys);
        testUpdate(sys);
        testPickUpAndUpdate(sys);
    }

    private static void testPickUpAndUpdate(ElevatorControlSystemImpl sys) {
        System.out.println("################## PickUp And UPDATE ##################");
        System.out.println("pickUp at " + 7 + " DOWN ");
        sys.pickUp(7, -1);

        System.out.println("pickUp at " + 5 + " UP ");
        sys.pickUp(5, 1);
        sys.printStatus();
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
    }

    private static void testUpdate(ElevatorControlSystemImpl sys) {
        System.out.println("################## UPDATE ##################");
        System.out.println("Update to elevator " + 0 + " to reach : " + 4);
        sys.update(0, 4);
        sys.step();
        sys.printStatus();
        System.out.println("Update to elevator " + 1 + " to reach : " + 6);
        sys.update(1, 6);
        sys.step();
        sys.printStatus();
        System.out.println("Update to elevator " + 3 + " to reach : " + 7);
        sys.update(3, 2);
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
    }

    private static void testPickUp(ElevatorControlSystemImpl sys) {
        System.out.println("################## PICK UP ##################");
        System.out.println("pickUp at " + 7 + " UP ");
        sys.pickUp(7, 1);
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
        // In this below case, the first one should keep going up although it
        // is near. Another halted one should start to pick 2 up.
        System.out.println("pickUp at " + 2 + " UP ");
        sys.pickUp(2, 1);
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
    }

    private static void testIdle(ElevatorControlSystemImpl sys) {
        System.out.println("################## IDLE ##################");
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
        sys.step();
        sys.printStatus();
        sys.step();
    }


}