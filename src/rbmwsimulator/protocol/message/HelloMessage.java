package rbmwsimulator.protocol.message;
import rbmwsimulator.protocol.message.*;
import rbmwsimulator.util.Preferences;

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
public class HelloMessage extends Message {

  public HelloMessage(int sender, int receiver, double timestamp) {
    this.sender = sender;
    this.receiver = receiver;
    this.timeStamp = timestamp;
    this.msgType = MessageConstants.HELLO;
  }

 public String msgString() {
   if(this.receiver == Preferences.NETWORK_LOCAL_BROADCAST)
      return "Node "+this.sender+": at time "+this.timeStamp+" broadcast a <HELLO> message to its local neighbors";
   return "Node "+this.sender+": at time "+this.timeStamp+" unicast a <HELLO> message to "+this.receiver;
 }
}
