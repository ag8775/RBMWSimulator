package rbmwsimulator.protocol.message;

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
public abstract class Message {

 public int msgType;
 public double timeStamp;
 public int sender;
 public int receiver;

 public int getMessageType() {
    return this.msgType;
  }

  public double getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(double time) {
    this.timeStamp = time;
  }

  public void setSender(int sender) {
    this.sender = sender;
  }

  public int getSender() {
    return this.sender;
  }

  public void setReceiver(int receiver) {
    this.receiver = receiver;
  }

  public int getReceiver() {
    return this.receiver;
  }

  public int getMessageSize() {
    return MessageConstants.getMessageSize(this.msgType);
  }

 public abstract String msgString();

} // End of Message Class
