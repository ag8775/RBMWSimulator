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
/**
   Element is the abstract superclass of all the static elements in the
   simulator, such as Nodes, Links, etc.
*/
public abstract class Element {

    /**
       Ask the element to update itself.
    */
    public abstract void update();


    /**
       Dump some descriptive information about the element for debugging
       purposes. This will most of the time display information about contained
       elements as well.
    */
    public abstract void dump();


}
