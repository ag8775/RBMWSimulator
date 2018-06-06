package rbmwsimulator.command;
import simulator.command.Command;
import rbmwsimulator.element.Node;
import simulator.Simulator;
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
   A StopCommand can be schedule at any time to forcefully interrupt the
   simulator. Some simulations can go on forever so this is quite useful.
*/
public class StopCommand extends Command {
    Node nodes[];
    public StopCommand(double time, Node nodes[]) {
      super("Stop",time);
      this.nodes = nodes;
    }

    /**
       Just set the finished flag in the simulator.
    */
    public void execute() {
      for (int nodeId = 0; nodeId < nodes.length; nodeId++)
        nodes[nodeId].setFinished();
      Simulator.getInstance().setFinished();
    }

}
