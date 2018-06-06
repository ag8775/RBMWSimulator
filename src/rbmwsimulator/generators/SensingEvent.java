package rbmwsimulator.generators;
import rbmwsimulator.mote.SensorTypes;
import rbmwsimulator.element.Coordinates;
import rbmwsimulator.model.SensingModel;
import rbmwsimulator.model.MobilityModel;

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
public class SensingEvent {
  private String e_name;
  private int eventId;
  private int sensorType;
  private Coordinates originalPosition;
  private int maxX, maxY;
  private MobilityModel eventMobilityModel;
  private SensingModel sensingModel;
  private MobilityStatus eventMobilityStatus;

  private long seed;
  private int luxury;
  //private RandomElement e;

  public SensingEvent(String e_name_, int eventId_, int sensorType_, Coordinates originalPosition_, long seed_, int luxury_, int maxX_, int maxY_, int maxSpeed_, int minSpeed_, float minPauseTime_, float maxPauseTime_, float minMobilityTime_, float maxMobilityTime_, SensingModel sensingModel_, MobilityModel mobilityModel_) {
    this.e_name = e_name_;
    this.eventId = eventId_;
    this.sensorType = sensorType_;
    this.originalPosition = originalPosition_;
    this.maxX = maxX_;
    this.maxY = maxY_;
    this.seed = seed_;
    this.luxury = luxury_;
    //this.e =  new Ranlux(luxury,seed);
    //Two Models: Sensing Model and Event Mobility Model ...
    this.sensingModel = sensingModel_;
    this.eventMobilityModel = mobilityModel_;
    this.eventMobilityStatus = new MobilityStatus(eventId_, originalPosition_, this.maxX, this.maxY, maxSpeed_, minSpeed_, minPauseTime_, maxPauseTime_, minMobilityTime_, maxMobilityTime_);
  }

  //Get Methods ...
  public String getEventName() {
    return this.e_name;
  }

  public int getEventId() {
    return this.eventId;
  }

  public int getSensorType() {
    return this.sensorType;
  }

  public Coordinates getEventOrigin() {
    return this.originalPosition;
  }

  public int getMaxDeploymentAlongXAxis() {
    return this.maxX;
  }

  public int getMaxDeploymentAlongYAxis() {
    return this.maxY;
  }

  public SensingModel getSensingModel() {
    return this.sensingModel;
  }

  public MobilityModel getMobilityModel() {
    return this.eventMobilityModel;
  }

  public MobilityStatus getCurrentEventStatus() {
    return this.eventMobilityStatus;
  }

  public long getSeed() {
    return this.seed;
  }

  public String toString() {
    return this.e_name+" with id = "+this.eventId+" sensed by "+SensorTypes.sensors[this.sensorType]+" sensor at original position "+this.originalPosition.toString();
  }
}
