# Process Scheduling Simulation

A process Scheduler schedules different processes to be assigned to the CPU based on particular scheduling algorithm.
Every process has a different task like ready, wait and run. Process scheduling algorithm is implemented based on
priority.

        •   Process Scheduling simulation is implemented with Java collection API ArrayList and Priority
            Queue.
        •   A priority is associated with each process.Process with smallest priority is to be executed
            first and so on.
        •   If process is in running stage and it waits maximum time unit like 30 milliseconds then 
            respected process priority is decreased by one and each process get chance to execute it.
        •   A program uses a logical time to keep track of the simulation process and the same logical
            time is used to represent the arrivaltime and duration.
        •   The simulation goes through a series of iterations and each iteration represents the passage
            of one logical time unit.
        •   At the beginning,the current time is set to zero.At the end of each iteration,the current
            time is incremented.
        •   Calculate the total wait time and average wait time of the all process.
        •   When program run successfully process_scheduling_output file generated the desired output
            as per the PsExpectedOutput document file.



# Environment
	• Java version 1.8.0
	• Ecliplse IDE
