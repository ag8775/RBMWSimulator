package rbmwsimulator.protocol;

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
public class Status {
  /**
   *  Node Status ...
   */
  public final static int UP = 1;
  public final static int DOWN = 2;
  public final static String node_status[] = {" ", "UP", "DOWN"};

  /**
   * Processor Status ...
   */
  public final static int PROCESSOR_IDLE = 1;
  public final static int PROCESSOR_BUSY = 2;
  public final static String processor_status[] = {" ", "IDLE", "BUSY"};

  /**
   * Sensor Status
   */
  public final static int SENSOR_IDLE = 1;
  public final static int SENSOR_BUSY_SAMPLING = 2;
  public final static String sensor_status[] = {" ", "IDLE", "BUSY"};

  /**
   *  Radio Status ...
   */
  public final static int RADIO_IDLE = 1;
  public final static int RADIO_SLEEP = 2;
  public final static int RADIO_TRANSMITTING = 3;
  public final static int RADIO_RECEIVING = 4;
  public final static String radio_status[] = {" ", "IDLE", "SLEEP", "TRANSMITTING", "RECEIVING"};

  /**
   * Protocol States ...
   */
  public final static int RBMW_PROTOCOL_DISCOVER_NEIGHBORS = 1;
  public final static int RBMW_PROTOCOL_BACK_OFF_RADIO_TRANSMISSION = 2;
  public final static int RBMW_PROTOCOL_LISTEN_NEIGHBOR_DISCOVERY = 3;
  public final static String protocol_status[] = {" ", "DISCOVERING NEIGHBORS", "RADIO TRANSMISSION BACK OFF", "LISTENING TO NEIGHBOR DISCOVERY", "RECEIVING"};

  /**
   * Subprotocol states ...
   */

  //Neighbor Discovery Subprotocol states
  public final static int NEIGHBOR_DISCOVERY_PROTOCOL_SENSE_WIRELESS_NEIGHBORHOOD_IDLE = 1;
  public final static int NEIGHBOR_DISCOVERY_PROTOCOL_RADIO_IDLE_SENSE_FOR_RANDOM_BACKOFF_DURATION_BEFORE_TRANSMISSION = 2;
  public final static int NEIGHBOR_DISCOVERY_PROTOCOL_RADIO_CONTENTION_RANDOM_BACKOFF_BEFORE_SENSING_RADIO_IDLE = 3;

  /**
   * Protocol Constants
   *
   */
   public static final int LOCAL_MESSAGE_BROADCAST = (int)Math.pow(2, 32-1);



}
