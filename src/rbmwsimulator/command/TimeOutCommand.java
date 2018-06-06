package rbmwsimulator.command;
import simulator.command.Command;
import rbmwsimulator.element.Node;
import simulator.element.Timer;
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
public class TimeOutCommand extends Command {
  private Node node;
  private int timerId;
  private double time_wait;
  private Timer timer;

  public TimeOutCommand(double futureTime, double time_wait, Node node, int timerId, Timer timer_) {
    super("TimeOut",futureTime);
    this.time_wait = time_wait;
    this.node = node;
    this.timerId = timerId;
    this.timer = timer_;
    this.timer.setTimerExpiryDuration(time_wait);
    this.timer.setTimerInUse();
  }

  public void execute() {
    this.timer.setTimerFree();
    this.timer.setTimerExpiryDuration((double)0);
    this.node.timerExpired(this.timerId);
  }
}
