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
public class EventTypes {
    public static final String[] events = {"Fire", "Light", "Pressure", "Humidity", "Sound", "Vibration"};
    public static final int FIRE_EVENT = 0;
    public static final int LIGHT_EVENT = 1;
    public static final int PRESSURE_EVENT = 2;
    public static final int HUMIDITY_EVENT = 3;
    public static final int SOUND_EVENT = 4 ;
    public static final int VIBRATION_EVENT = 5;

    public static int getEventType(String eventName) {
      for(int eventIndex = 0; eventIndex < events.length; eventIndex++)
        if(eventName.compareTo(events[eventIndex]) == 0)
          return eventIndex;
       return -1;
    }
}
