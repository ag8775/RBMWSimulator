package rbmwsimulator.command;
import rbmwsimulator.protocol.message.Message;
import simulator.command.Command;
import rbmwsimulator.model.WirelessNeighborhoodModel;
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
/**
   SendMessageCommand can be used send message from one node i.e. the source to
   another node i.e. the destination.
*/
public class SendMessageCommand extends Command {
    private Message message;
    private int senderNodeId;
    private int receiverNodeId;
    private WirelessNeighborhoodModel wirelessModel;

    public SendMessageCommand(double sendTime, int senderNodeId_, int receiverNodeId_, WirelessNeighborhoodModel wirelessModel_, Message msg) {
      super("SendCommand",sendTime);
      this.message = msg;
      this.receiverNodeId = receiverNodeId_;
      this.senderNodeId = senderNodeId_;
      this.wirelessModel = wirelessModel_;
    }

    public void execute() {
      this.wirelessModel.signalUnicastSendMessageFor(this.senderNodeId, this.receiverNodeId, this.message);
    }
}
