package rbmwsimulator.protocol;
import rbmwsimulator.util.Preferences;
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
public class Timers {
   /********* Neighbor Discovery Timers **************/
   public final static String NEIGHBOR_DISCOVERY_TIMER_NAMES[] = {"Neighbor Discovery Timer", "Neighbor Discovery Idle Backoff Timer", "Neighbor Discovery Contention Backoff Timer"};
   public final static int NEIGHBOR_DISCOVERY_TIMER = 0;
   public final static int NEIGHBOR_DISCOVERY_PROTOCOL_RADIO_IDLE_SENSE_BEFORE_TRANSMISSION_BACKOFF_TIMER = 1;
   public final static int NEIGHBOR_DISCOVERY_PROTOCOL_RADIO_CONTENTION_RANDOM_BACK_OFF_TIMER = 2;

   /**** RBMW Timers *****/
   public final static String RBMW_TIMER_NAMES[] = {"Neighbor Discovery Timer", "Neighbor Discovery Idle Backoff Timer", "Neighbor Discovery Contention Backoff Timer"};

   public static String[] getProtocolTimersNames(int protocolId) {
     String names[] = {Preferences.NULL_STRING}; //Initialize with "NULL" String. See Preferences.NULL_STRING
     switch(protocolId) {
       case Preferences.NEIGHBOR_DISCOVERY_PROTOCOL:
         names = NEIGHBOR_DISCOVERY_TIMER_NAMES;
         break;
       case Preferences.ROLE_BASED_MIDDLEWARE_PROTOCOL:
         names = RBMW_TIMER_NAMES;
         break;
     }
     return names;
   }


}
