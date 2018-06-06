package rbmwsimulator.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.cornell.lassp.houle.RngPack.RandomSeedable;
import cern.jet.random.engine.RandomSeedTable;
import java.util.Random;

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
public class Preferences {
  public static final String VERSION = "1.0";
  private static final Date date = new Date();
  public static final String NULL_STRING = "NULL";

  /**
   * Enable/Disable Logging options ...
   */

  public static final boolean DEFAULT_LOG_OPTION = true;

  /**
   * Default Pseudo Random Number Generator Options ...
   */
  public static final int DEFAULT_MULTIPLICATIVE_FACTOR_NRANDOM_NUMBERS = 100;
  public static final int DEFAULT_DISTRIBUTION_TYPE = 14; //Default is Mersenne Twister ... See cern.jet.random.engine.MersenneTwister.java
  public static final long DEFAULT_LONG_SEED = RandomSeedable.ClockSeed();
  private static final Random prng = new Random(DEFAULT_LONG_SEED);
  public static final int DEFAULT_RANDOM_SEED_TABLE_ROW = prng.nextInt();
  public static final int DEFAULT_RANDOM_SEED_TABLE_COL = prng.nextInt();
  public static final int DEFAULT_INT_SEED = RandomSeedTable.getSeedAtRowColumn(DEFAULT_RANDOM_SEED_TABLE_ROW, DEFAULT_RANDOM_SEED_TABLE_COL);
  public static final int DEFAULT_LUXURY_LEVEL = 4;   //For ranlux prng
  public static final int DEFAULT_LOW_RANGE = 0; //For uniform random with range {low, high}
  public static final int DEFAULT_HIGH_RANGE = 1;
  public static final double DEFAULT_PARETO_K_VALUE = 1500; // 0 < k <= 1500
  public static final double DEFAULT_PARETO_P_VALUE = Math.pow(10, 10); // k <<< p
  public static final double DEFAULT_PARETO_ALPHA_VALUE = 1.0; // 0 < alpha < 2. The lower the parameter alpha, the more variable the distribution, and the more pronounced is the heavy-tailed property
  public static final double DEFAULT_PARETO_SHAPE_VALUE = 1.5; //scale and shape have to be greater than 0
  public static final double DEFAULT_PARETO_SCALE_VALUE = 1;
  public static final double DEFAULT_EXPONENTIAL_LAMBDA_VALUE = 1; // should be greater than 0; if it is 1 => Poisson process ...

  /**
   *  Default options for the Topology Scenario Generator
   */
  public static final int DEFAULT_NUM_NODES = 100;
  public static final int DEFAULT_MAX_X = 640;
  public static final int DEFAULT_MAX_Y = 640;

  /**
   *  Default options for the Node Scenario Generator
   */
  public static final float DEFAULT_MAX_BATTERY_ENERGY = 400; //in Joules
  public static final int DEFAULT_MAX_RADIO_RANGE = 60; // in meters
  public static final int DEFAULT_MAX_SENSING_RANGE = 60; // in meters
  public static final float DEFAULT_MAX_RADIO_BIT_RATE = 1; //in Kbps
  public static final int DEFAULT_MAX_STORAGE_CAPACITY = 1; //in MB
  public static final int DEFAULT_MAX_COMPUTING_POWER = 10; //in mips (Million Instructions Per Second)
  public static final int DEFAULT_MAX_SENSORS = 4;

  public static final float DEFAULT_DELTA_BATTERY_ENERGY = 50; //min = max - delta
  public static final int DEFAULT_DELTA_RADIO_RANGE = 5; // in meters
  public static final int DEFAULT_DELTA_SENSING_RANGE = 5; // in meters
  public static final float DEFAULT_DELTA_RADIO_BIT_RATE = (float)0.2; //in Kbps
  public static final int DEFAULT_DELTA_STORAGE_CAPACITY = 0; //in MB
  public static final int DEFAULT_DELTA_COMPUTING_POWER = 2; //in mips (Million Instructions Per Second)
  public static final int DEFAULT_DELTA_SENSORS = 2;


  public static final boolean DEFAULT_EQUALIZE_ENERGY_OPTION = true;
  public static final boolean DEFAULT_EQUAL_RADIO_RANGE_OPTION = false;
  public static final boolean DEFAULT_EQUAL_SENSING_RANGE_OPTION = false;
  public static final boolean DEFAULT_EQUAL_RADIO_BIT_RATE_OPTION = false;
  public static final boolean DEFAULT_EQUAL_STORAGE_OPTION = false;
  public static final boolean DEFAULT_EQUAL_COMPUTING_POWER_OPTION = false;
  public static final boolean DEFAULT_EQUAL_SENSORS_OPTION = false;

  /**
   * DEFAULT RADIO PARAMETERS
   *
   */
  public static final int RADIO_RX_MODE = 0;
  public static final int RADIO_TX_MODE = 1;
  public static final int DEFAULT_RADIO_MODE = RADIO_RX_MODE;
  public static final double DEFAULT_RADIO_SWITCHING_DELAY = 0.001; // 1 ms delay
  public static final double SPEED_OF_LIGHT = 3.0e+8;
  public static final double DEFAULT_MAX_WIRELESS_SIGNAL_PROPAGATION_DELAY = calculateSignalSpeed(DEFAULT_MAX_RADIO_RANGE);
  public static final double DEFAULT_TINYOS_MTU = 29; //in bytes

  /**
   * DEFAULT BATTERY PARAMETERS
   *
   */
  public static final double DEFAULT_IDLE_BATTERY_DISSIPATION_RATE = 0.00001; //10^-5 Joules/second

  /**
   * Mobility Model
   *
   */
  public static final int STATIONARY = 0;
  public static final int MOBILE = 1;

  /**
   *  Print preferences for various classes ...
   *
   */
  public static final boolean PRINT_COMMAND_DETAILS = true;
  public static final boolean PRINT_EVENT_MODEL_DETAILS = false;
  public static final boolean PRINT_MOBILITY_MODEL_DETAILS = false;
  public static final boolean PRINT_WIRELESS_NEIGHBORHOOD_MODEL_DETAILS = false;
  public static final boolean PRINT_CURRENT_NODE_STATE_DETAILS = true;
  public static final boolean PRINT_METRIC_CALCULATION_DETAILS = false;
  public static final boolean PRINT_NODE_SCENARIO_MODEL_DETAILS = false;
  public static final boolean PRINT_PROTOCOL_AGENT_ACTIVITY = true;
  public static final boolean PRINT_RA_PROTOCOL_ACTIVITY = true;
  public static final boolean PRINT_RBMW_PROTOCOL_ACTIVITY = true;
  public static final boolean PRINT_RBMW_SIMULATOR_ACTIVITY = true;
  public static final boolean PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS = false;
  public static final boolean PRINT_TDMA_FRAME_DETAILS = true;

  /**
   *  Protocol Addresses ...
   */
  public static final int NETWORK_BASESTATION_ADDRESS = 0;
  public static final int NETWORK_LOCAL_BROADCAST = 65535;

  /**
   * Protocol types ...
   */
  public static final int WIRELESS_CSMA_MAC_PROTOCOL = 0;
  public static final int NEIGHBOR_DISCOVERY_PROTOCOL = 1;
  public static final int ROLE_BASED_MIDDLEWARE_PROTOCOL = 2;
  public static final int ROLE_ADVERTISEMENT_PROTOCOL = 3;
  public static final int ROLE_ASSIGNMENT_PROTOCOL = 4;
  public static final int ROLE_EXECUTION_PROTOCOL = 5;
  public static final int ROLE_PERF_FEEDBACK_PROTOCOL = 6;
  public static final String protocol_name[] = {"CSMA", "ND", "RBMW", "RADV", "RA", "RE", "RPF"};

   /**
   *  DEFAULT PROTOCOL TDMA FRAME, SLOT, SUBFRAME PARAMETERS ...
   *
   */
  public static final long DEFAULT_NEIGHBOR_DISCOVERY_SUBFRAME_SLOTS = 50;
  public static final long DEFAULT_ROLE_ADVERTISEMENT_SUBFRAME_SLOTS = 30;
  public static final long DEFAULT_ROLE_ASSIGNMENT_SUBFRAME_SLOTS = 80;
  public static final long DEFAULT_ROLE_EXECUTION_SUBFRAME_SLOTS = 300;
  public static final long DEFAULT_ROLE_FEEDBACK_SUBFRAME_SLOTS = 100;
  public static final long DEFAULT_TOTAL_TDMA_FRAME_SLOTS = DEFAULT_NEIGHBOR_DISCOVERY_SUBFRAME_SLOTS +
                                                           DEFAULT_ROLE_ADVERTISEMENT_SUBFRAME_SLOTS +
                                                           DEFAULT_ROLE_ASSIGNMENT_SUBFRAME_SLOTS +
                                                           DEFAULT_ROLE_EXECUTION_SUBFRAME_SLOTS +
                                                           DEFAULT_ROLE_FEEDBACK_SUBFRAME_SLOTS;
  public static final double DEFAULT_TDMA_SLOT_DURATION = 5.0; //in seconds ...

  //Subframe Indices ...
  public static final int NEIGHBOR_DISCOVERY_SUBFRAME_INDEX = 0;
  public static final int ROLE_ADVERTISEMENT_SUBFRAME_INDEX = 1;
  public static final int ROLE_ASSIGNMENT_SUBFRAME_INDEX = 2;
  public static final int ROLE_EXECUTION_SUBFRAME_INDEX = 3;
  public static final int ROLE_FEEDBACK_SUBFRAME_INDEX = 4;



  /**
   * DEFAULT SERVICE TDMA FRAME, SLOT, SUBFRAME PARAMETERS ...
   *
   */

   //Neighbor Discovery Protocol Parameters ...
   public static final long DEFAULT_NEIGHBOR_DISCOVERY_PROTOCOL_RADIO_IDLE_SENSE_BACKOFF_SLOTS_BEFORE_TRANSMISSION = 4;
   public static final long DEFAULT_NEIGHBOR_DISCOVERY_PROTOCOL_RADIO_CONTENTION_RANDOM_BACK_OFF_SLOTS_BEFORE_IDLE_RESENSE = 10;

  /**
  *  Default Protocol Timeout Values ...
  */
  public static final long MAX_MEDIUM_CONTENTION_BACK_OFF_SLOTS = 5;
  public static final long MAX_SENSE_RADIO_MEDIUM_IDLE_BACK_OFF_SLOTS = 2;


  /* Miscellaneous Utility Functions */

  public static String getDateString() {
    SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM_dd_yy_HH_mm_ss");
    return bartDateFormat.format(date);
  }

  public static double calculateSignalSpeed(int maxDistance) {
     return ((double)maxDistance)/SPEED_OF_LIGHT;
  }
}
