package rbmwsimulator.mote;

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
public class SensorTypes {
  public static final String[] sensors = {"Temperature", "Photo", "Pressure", "Humidity", "Sound", "Vibration"};
  public static final int TEMPERATURE_SENSOR = 0;
  public static final int PHOTO_SENSOR = 1;
  public static final int PRESSURE_SENSOR = 2;
  public static final int HUMIDITY_SENSOR = 3;
  public static final int SOUND_SENSOR = 4 ;
  public static final int VIBRATION_SENSOR = 5;

  public static int getSensorType(String sensorName) {
    //System.out.println("Sensor name = "+sensorName);
    for(int sensorIndex = 0; sensorIndex < sensors.length; sensorIndex++)
      if(sensorName.compareTo(sensors[sensorIndex]) == 0)
        return sensorIndex;
     return -1;
  }




}
