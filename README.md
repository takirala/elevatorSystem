# Elevator System
A simple elevator control system implemented in java.

# Components

Below classes are required :
* Elevator 
* Passenger 
* Constants class (to hold direction and other classes)
* Interface
* Implementation of Interface
* Main class to run the code
* Testcases

# How this works?

## Initial Setup

* The system asks how many elevators and how many floors the system covers.
* Initializes the Elevator Control System (#TODO 1) to implement this.
* Each elevator will be at floor 0 initially.
* It can be assumed that whenever an elevator halts at a certain floor, the doors will be kept open.

## Functionalities provided
* An user can stand in the lobby of certain floor and can request a **pickup** by pressing either UP or DOWN from the lobby.
  * For bottom most floor and top most floor there should be only one direction.
* The user can **update** the elevator (after entering in to it) by calling the update method. update method needs only two parameters, elevator id and the destination floor.
* **step** function should simulate the entire ecosystem and keep moving the elevator system. If there is no traffic, system should by idle.


