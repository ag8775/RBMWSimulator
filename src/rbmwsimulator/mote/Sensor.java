package rbmwsimulator.mote;
import rbmwsimulator.protocol.Status;
import edu.cornell.lassp.houle.RngPack.*;
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
public class Sensor {
  private int sensorType;
  private double[] sensingExposureRange; //0 => min value, 1 => max value
  private double sensorReading;
  private int sensingRange;
  private float threshold;
  private double timeStamp;
  private int sensingStatus;
  private long seed;
  private int luxury;
  private RandomElement e;

  public Sensor(int sensorType_, long seed_, int luxury_) {
    this.sensorType = sensorType_;
    this.sensingStatus = Status.SENSOR_IDLE;
    this.seed = seed_;
    this.luxury = luxury_;
    this.e = new Ranlux(luxury,seed);
  }

  public int getSensorType() {
    return this.sensorType;
  }

  public void setSensingRange(int sensingRange_) {
    this.sensingRange = sensingRange_;
  }

  public int getSensingRange() {
    return this.sensingRange;
  }

  public void setSensorReading(float sensorReading_, double timeStamp_) {
    this.sensorReading = sensorReading_;
    this.timeStamp = timeStamp_;
  }

  public void setSensorThreshold(float threshold_) {
    this.threshold = threshold_;
  }

  public boolean generateEvent() {
    if(this.sensorReading >= this.threshold)
      return true;
    return false;
  }

  public double getTimeStamp() {
    return this.timeStamp;
  }

  public double getSensorReading() {
    return this.sensorReading;
  }

  public String toString() {
    return "{Sensor: "+SensorTypes.sensors[this.sensorType]+", "+Status.sensor_status[this.sensingStatus]+", "+sensingRange+"m, "+this.getSensorReading()+", "+this.timeStamp+"s},";
  }

}
