/**
 * This class implements a Process object with instance variables process id, priority, duration,
 * and arrival time.
 */
class Process {

  public int id;
  public int priority;
  public int duration;
  public int arrival;
  public int wait_time = 0;

  /**
   * parameterize constructor.
   * @param id int
   * @param priority int
   * @param duration int
   * @param arrival int
   */

  public Process(int id, int priority, int duration, int arrival) {
    this.id = id;
    this.priority = priority;
    this.duration = duration;
    this.arrival = arrival;
  }
  // Accessor Methods
  public int getID() {
    return id;
  }

  public int getPriority() {
    return priority;
  }

  public int getDuration() {
    return duration;
  }

  public int getArrivalTime() {
    return arrival;
  }

  public int getWaittime() {
    return wait_time;
  }

  public void Waittimeupdation() {
    wait_time = wait_time + 1;
  }

  /** process wait_time is > max_wait_time then DecrementPriority() is called. */
  public void Decrementpriority() {
    priority = priority - 1;
  }
  // ----------------------------------------------------------------------------------
  public String toString() {
    String s =
        "\tid = "
            + id
            + "\tpriority = "
            + priority
            + "\tduration = "
            + duration
            + "\tarrival time = "
            + arrival;
    return s;
  }
}
