package simulator.command;
import simulator.element.Element;
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
   ElementUpdateCommand is a command class that can be used genericly to
   schedule a call to any element's update() function at a specific time.
*/
public class ElementUpdateCommand extends Command {

    private Element m_element;

    public ElementUpdateCommand(Element element,double time) {
      super("ElementUpdate",time);
      m_element=element;
    }

    /**
       Call the update function of the element.
    */
    public void execute() {
      m_element.update();
    }

}
