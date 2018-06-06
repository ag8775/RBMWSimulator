package rbmwsimulator.mote;
import rbmwsimulator.protocol.Status;
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
public class Processor {
  private int cpuSpeed;
  private int processorStatus;
  public Processor(int cpuSpeed_) {
    this.cpuSpeed = cpuSpeed_;
    this.processorStatus = Status.PROCESSOR_IDLE;
  }

  public boolean isProcessorFree() {
    return (this.processorStatus == Status.PROCESSOR_IDLE);
  }

  public float timetoProcess(int requiredProcessingSpeed) {
    return (requiredProcessingSpeed/cpuSpeed);
  }

  public void setProcessorBusy() {
    this.processorStatus = Status.PROCESSOR_BUSY;
  }

  public void setProcessorFree() {
    this.processorStatus = Status.PROCESSOR_IDLE;
  }

  public String toString() {
    return "[Processor: "+Status.processor_status[this.processorStatus]+", "+cpuSpeed+" Mhz]";
  }

}
