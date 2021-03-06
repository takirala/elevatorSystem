# Elevator System
A simple elevator control system implemented in java.

# Components

Below classes are developed :
* Elevator 
* Passenger (Can control, number of passengers, their weights etc..)
* Interface
* Implementation of Interface
* Main class to run the code
* Testcases

# How this works?

## Initial Setup

* The system asks how many elevators and how many floors the system covers.
* Initializes the Elevator Control System.
* Each elevator will be at floor 0 initially. (i have used 0 based index. Hence, range will be 0 to MAX_FLOORS-1)
* It can be assumed that whenever an elevator halts at a certain floor, the doors will be kept open.

## Functionalities provided
* An user can stand in the lobby of certain floor and can request a **pickup** by pressing either UP or DOWN from the lobby.
  * For bottom most floor and top most floor there should be only one direction.
* The user can **update** the elevator (after entering in to it) by calling the update method. update method needs only two parameters, elevator id and the destination floor.
* **step** function should simulate the entire ecosystem and keep moving the elevator system. If there is no traffic, system should by idle.

## Notes:

* I have used a integer array to represent the configuration of elevators and floors in a bottom up fashion.
	* 0 -> Default value.
	* 1 -> Elevator is here.
	* 9 -> This is in elevator goal floors list
* I have used zero based index.
* The program initially requires two values :
	* Number of elevators and
	* Number of floors.
* Then, the program accepts tow commands :
	* pick 
		* This needs two arguments : floorNumber and direction.
	* update
		* This needs tow arguments as well : elevatorId and destination floor.
* Due to time constraints of 4 hour period, the code may be a bit redundant, but I have optimized for complexity & functionality.
* Also, I am not checking for basic input validation and always assuming that the inputs are valid.
	* E.g: If you enter pickup floor as 20 in a 15 floor system, system will crash. This behavior can be changed with very minimal code change.

## How to Run the code?
	
#### To compile the code:

	$ javac -cp . SimpleElevatorSystem.java Test.java             

#### Execute the test cases:

	$ java Test             

#### Example execution of main program : 
	                                                              
	$ java SimpleElevatorSystem                                   
	========================================                      
	Available commands : [pick] or [update]                       
	pick needs a floor number and direction                       
	update needs elevatorId and goalFloor                         
	========================================                      
	Enter number of elevators :                                   
	4                                                             
	Enter number of floors :                                      
	4                                                             
	                                                              
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	[1, 1, 1, 1]                                                  
	Input command [Enter to step]:                                
	pick                                                          
	3                                                             
	-1                                                            
	                                                              
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	[1, 1, 1, 1]                                                  
	Input command [Enter to step]:                                
	                                                              
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	[1, 1, 1, 1]                                                  
	[0, 0, 0, 0]                                                  
	Input command [Enter to step]:                                
	                                                              
	                                                              
	[0, 0, 0, 0]                                                  
	[1, 1, 1, 1]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	Input command [Enter to step]:                                
	                                                              
	                                                              
	[1, 1, 1, 1]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	Input command [Enter to step]:                                
	update                                                        
	3                                                             
	0                                                             
	                                                              
	[1, 1, 1, 1]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 9]                                                  
	Input command [Enter to step]:                                
	                                                              
	[1, 1, 1, 1]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 9]                                                  
	Input command [Enter to step]:                                
	                                                              
	                                                              
	[1, 1, 1, 0]                                                  
	[0, 0, 0, 1]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 9]                                                  
	Input command [Enter to step]:                                
	                                                              
	                                                              
	[1, 1, 1, 0]                                                  
	[0, 0, 0, 0]                                                  
	[0, 0, 0, 1]                                                  
	[0, 0, 0, 9]                                                  
	Input command [Enter to step]:
