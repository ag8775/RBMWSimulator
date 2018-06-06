package simulator.element;

/**
 * <p>Title: Role-based Middleware Simulator (RBMW Simulator)</p>
 *
 * <p>Description: A simulator to test several role functionalities such as
 * role-assignment, role-monitoring, role-repair, role-execution scheduling,
 * role state machine, and role load-balancing algorithms. Also, we want to
 * experiment with two domain-specific models such as the Role-Energy (RE) model
 * and Role/Resource Allocation Marginal Utility (RAMU) model.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: Networking Wireless Sensors (NeWS) Lab, Wayne State University
 * <http://newslab.cs.wayne.edu/></p>
 *
 * @author Manish M. Kochhal <manishk@wayne.edu>
 * @version 1.0
 */
public class Timer {
  private int timerId;
  private boolean timerState;
  private String timerName;
  private double expiryDuration;

  public Timer(int timerId_, String timerName_) {
    this.timerId = timerId_;
    this.timerName = timerName_;
    this.timerState = false;
    this.expiryDuration = (double)0;
  }

  public Timer(int timerId_, boolean timerState_, String timerName_) {
    this.timerId = timerId_;
    this.timerName = timerName_;
    this.timerState = timerState_;
    this.expiryDuration = (double)0;
  }

  public Timer(int timerId_, boolean timerState_, double expiryDuration_) {
    this.timerId = timerId_;
    this.timerState = timerState_;
    this.expiryDuration = expiryDuration_;
  }

  public int getTimerId() {
    return this.timerId;
  }

  public String getTimerName() {
    return this.timerName;
  }

  public double getTimerExpiryDuration() {
    return this.expiryDuration;
  }

  public boolean isTimerInUse() {
    return this.timerState;
  }

  public void setTimerInUse() {
    this.timerState = true;
  }

  public void setTimerFree() {
    this.timerState = false;
  }

  public void setTimerExpiryDuration(double expiryDuration_) {
    this.expiryDuration = expiryDuration_;
  }
}
