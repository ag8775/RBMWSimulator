package rbmwsimulator.command;
import simulator.command.Command;
import rbmwsimulator.element.Node;
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
   NodeStateCommand can be used to schedule a change to the state of a Node
   at any time. Nodes can be taken down or brought up like this.
*/
public class NodeStateCommand extends Command {

    private Node m_node;
    private int m_state;

    /**
       @param state either Status.UP or Status.DOWN, as defined in
       p2psimulator.util.Status.
    */
    public NodeStateCommand(double time, Node node, int state) {
      super("NodeState",time);
      m_node=node;
      m_state=state;
    }

    public void execute() {
      m_node.setStatus(m_state);
    }

}

