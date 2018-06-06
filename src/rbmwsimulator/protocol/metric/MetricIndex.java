package rbmwsimulator.protocol.metric;

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
 * @version 2.0
 */
public class MetricIndex {
  public static final int NODE_ID_METRIC = 0;
  public static final int NEIGHBOR_DISTANCE_METRIC = 1;
  public static final int NODE_CUMULATIVE_SENSING_DEGREE_METRIC = 2;
  public static final int NODE_AVAILABLE_ENERGY_METRIC = 3;
  public static final int CUMULATIVE_NEIGHBORHOOD_AVAILABLE_ENERGY = 4;
  public static final int NODE_DEGREE_METRIC = 5;
  public static final int MAX_NODE_METRICS = 6;
}
