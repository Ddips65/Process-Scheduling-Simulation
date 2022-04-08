/**
 * A process Scheduler schedules different processes to be assigned to the CPU based on scheduling
 * algorithm. A priority is associated with each process.Process with smallest priority is to be
 * executed first and so on. If process is in running stage it waits for its turn more than maximum
 * time unit like 30 ms then respected process priority is decreased by one and each process get
 * chance to execute it.
 */
import java.io.*;
import java.util.*;

public class ProcessScheduling {

  static double tot_wait_time = 0;
  public static final int max_wait_time = 30;
  static int temp_pro_finish_time = 0;

  public static void main(String[] args) throws IOException {
    ArrayList<Process> al = new ArrayList<Process>();
    Scanner Inputfile = new Scanner(new File("process_scheduling_input.txt"));
    File outputFile = new File("process_scheduling_output.txt");
    PrintWriter fileOutput = new PrintWriter(outputFile);

    while (Inputfile.hasNext()) {
      String[] words = Inputfile.nextLine().split(" ");
      int id = Integer.parseInt(words[0]);
      int priority = Integer.parseInt(words[1]);
      int duration = Integer.parseInt(words[2]);
      int arrivaltime = Integer.parseInt(words[3]);

      Process p = new Process(id, priority, duration, arrivaltime);
      al.add(p);
    }
    // Below loop printing process line by line
    for (int m = 0; m < al.size(); m++) {
      fileOutput.println(al.get(m) + " ");
    }

    fileOutput.println("\nMaximum wait time = " + max_wait_time);

// ----- SIMULATION BASED ON GIVEN PSEUDOCODE --------------------------------------
    /** PriorityComparator implemented to check priority of the process. */
    class PriorityComparator implements Comparator<Process> {
      public int compare(Process p1, Process p2) {
        if (p1.equals(p2)) {
          return 0;
        } else if (p1.getPriority() > p2.getPriority()) {
          return 1;
        } else return -1;
      }
    }

    ArrayList<Integer> pro_wait_time = new ArrayList<Integer>();

    int current_time = 0;
    int pro_finish_time = 1;
    Process current_process = new Process(0, 0, 0, 0);
    boolean running = false;
    PriorityQueue<Process> pq = new PriorityQueue<Process>(10, new PriorityComparator());
    while (al.isEmpty() != true) {
      Process p = al.get(0);
      if (p.getArrivalTime() <= current_time) {
        pq.add(al.remove(0));
      }

      if (al.isEmpty() == true) {

        fileOutput.println("\nD becomes empty at time " + current_time);
      }

      if (pq.isEmpty() != true && running == false) {
        current_process = pq.remove();
        running = true;
        pro_finish_time = current_process.getDuration() + current_time;
        DisplayProcess(fileOutput, current_process, current_time);
        pro_wait_time.add(current_process.getWaittime());
      }

      current_time = current_time + 1;
      if (running == true) {
        if (current_time == pro_finish_time) {
          running = false;
          fileOutput.println(
              "Process " + current_process.getID() + " finished at time " + current_time);
          temp_pro_finish_time = current_time;
          fileOutput.println("\nUpdate Priority:");
          Priorityupdate(fileOutput, pq);
        }
        Updatewaittime(pq);
      }
    }

    if (running == true) {
      if (current_time == pro_finish_time) {
        running = false;
        fileOutput.println(
            "Process " + current_process.getID() + " finished at time" + current_time);
        Priorityupdate(fileOutput, pq);
      } else {
        Updatewaittime(pq);
        current_time = current_time + 1;
      }
    }

    while (pq.isEmpty() != true) {
      if (running == false) {
        current_process = pq.remove();
        running = true;
        pro_finish_time = current_process.getDuration() + current_time;
        DisplayProcess(fileOutput, current_process, current_time);
        pro_wait_time.add(current_process.getWaittime());
      }
      current_time = current_time + 1;
      if (running == true) {
        if (current_time == pro_finish_time) {
          running = false;
          fileOutput.println(
              "Process " + current_process.getID() + " finished at time " + current_time);
          temp_pro_finish_time = current_time;
          fileOutput.println("\nUpdate Priority:");
          Priorityupdate(fileOutput, pq);
        }
        Updatewaittime(pq);
      }
    }

    while (running == true) {
      if (current_time == pro_finish_time) {
        running = false; // process is not running
        fileOutput.println(
            "Process " + current_process.getID() + " finished at time" + current_time);
        Priorityupdate(fileOutput, pq);
      } else {
        Updatewaittime(pq);
        current_time = current_time + 1;
      }
    }
    // -----------------------------------------------------------------------------------------------------------
    double avg_wait_time = (tot_wait_time / pro_wait_time.size());
    fileOutput.println("\nTotal wait time = " + tot_wait_time);
    fileOutput.println("Average wait time = " + avg_wait_time);
    fileOutput.close();
  }

  // --------------------------- MAIN METHOD IS OVER----------------------------------------------

  /**
   * Display the process.
   * @param fileOutput PrintWriter
   * @param p Process
   * @param current_time int
   */
  public static void DisplayProcess(PrintWriter fileOutput, Process p, int current_time) {
    double wt = current_time - p.getArrivalTime();
    tot_wait_time = tot_wait_time + wt;
    fileOutput.println(
        "\nProcess removed from queue is: id = "
            + p.getID()
            + ", at time "
            + current_time
            + ", wait time = "
            + p.getWaittime()
            + ", Total wait time = "
            + tot_wait_time);
    fileOutput.println("Process id = " + p.getID());
    fileOutput.println("\tPriority = " + p.getPriority());
    fileOutput.println("\tArrival = " + p.getArrivalTime());
    fileOutput.println("\tDuration = " + p.getDuration());
  }

  /**
   * This method update wait times of processes that waiting in priority queue. Time is incremented
   * by one.It stores size of pq in length variable when pq remove size is change so store it.
   * @param pq PriorityQueue
   */
  public static void Updatewaittime(PriorityQueue<Process> pq) {
    ArrayList<Process> temp_hold_process = new ArrayList<Process>();
    int len_pq = pq.size();
    for (int i = 1; i <= len_pq; i++) {
      Process p = pq.remove();
      p.Waittimeupdation();
      temp_hold_process.add(p);
    }
    for (int u = 0; u < temp_hold_process.size(); u++) {
      pq.add(temp_hold_process.get(u));
    }
  }

  /**
   * This method updates priorities of the processes that wait longer than max wait time 30. Process
   * who waits longer than max time their priority is decremented by one.
   * @param fileOutput PrintWriter
   * @param pq PriorityQueue
   */
  public static void Priorityupdate(PrintWriter fileOutput, PriorityQueue<Process> pq) {
    int ut_prio_wait_time = 0;
    ArrayList<Process> temp_hold_process = new ArrayList<Process>();
    int len_pq = pq.size();
    for (int i = 1; i <= len_pq; i++) {
      Process p = pq.remove();
      ut_prio_wait_time = temp_pro_finish_time - p.getArrivalTime();
      if (p.getWaittime() > max_wait_time) {
        fileOutput.println(
            "PID = "
                + p.getID()
                + ", Wait time = "
                + ut_prio_wait_time
                + ", Current Priority = "
                + p.getPriority());
        if (p.getPriority() > 1) {
          p.Decrementpriority();
          fileOutput.println("PID = " + p.getID() + ", New Priority = " + p.getPriority());
        }
      }
      temp_hold_process.add(p);
    }

    for (int u = 0; u < temp_hold_process.size(); u++) {
      pq.add(temp_hold_process.get(u));
    }
  }
}
// ----------PESEDUCODE OF SIMULATION IS OVER ----------------------------------
