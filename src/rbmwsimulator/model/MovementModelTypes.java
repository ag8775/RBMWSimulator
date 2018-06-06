package rbmwsimulator.model;
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
public class MovementModelTypes {
  public static final String[] models = {"Waypoint", "Direction"};
  /**
  * Sensing Model Parameters
  *
  */
  public static final int RANDOM_WAYPOINT_MOBILITY_MODEL = 0;
  public static final int RANDOM_DIRECTION_MOBILITY_MODEL = 1;

  public static int getMovementModelType(String modelName) {
    for(int modelIndex = 0; modelIndex < models.length; modelIndex++)
      if(modelName.compareTo(models[modelIndex]) == 0)
        return modelIndex;
     return -1;
  }
}
