package rbmwsimulator.command;
import simulator.command.Command;
import rbmwsimulator.protocol.message.Message;
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
public class BroadcastMessageCommand extends Command {
    private Message message;
    private int senderNodeId;
    private WirelessNeighborhoodModel wirelessModel;

    public BroadcastMessageCommand(double sendTime, int senderNodeId_, WirelessNeighborhoodModel wirelessModel_, Message msg) {
      super("SendCommand",sendTime);
      this.message = msg;
      this.senderNodeId = senderNodeId_;
      this.wirelessModel = wirelessModel_;
    }

    public void execute() {
      this.wirelessModel.signalBroadcastSendMessageFor(this.senderNodeId, this.message);
    }
}
