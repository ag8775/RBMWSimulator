package rbmwsimulator.model;
import rbmwsimulator.element.Coordinates;
import java.util.Vector;

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
public class PointSensingExposureModels extends SensingModel {
  private Coordinates currentEventPosition;
  private int numNodes;
  private double sensingRange;
  private double[][] sensorReadingRanges;
  private Vector exposedNodes;
  private Vector exposedDistances;

  public PointSensingExposureModels(int modelType_, int numNodes_, long maxX_, long maxY_, long gridDimX_, long gridDimY_, Coordinates[] nodePositions_, double sensingRange_, double[] sensingThresholds_, double[] distanceThresholds_, Coordinates eventPosition_) {
    super(modelType_, maxX_, maxY_, gridDimX_, gridDimY_, nodePositions_, sensingThresholds_, distanceThresholds_);
    this.currentEventPosition = eventPosition_;
    this.numNodes = numNodes_;
    this.sensingRange = sensingRange_;
    this.exposedNodes = new Vector();
    this.exposedDistances = new Vector();
    this.sensorReadingRanges = new double[numNodes][2];
    for(int nodeId = 0; nodeId < this.numNodes; nodeId++) {
      this.sensorReadingRanges[nodeId] = new double[2];
      this.sensorReadingRanges[nodeId][0] = (double)0.0;
      this.sensorReadingRanges[nodeId][1] = (double)0.0;
    }
    updateSensorReadingRanges();
  }

  public void updateSensorReadingRanges() {
    int nodeId;
    double distance;

    //Find nodes that are within the sensing range of the event ...
    findNodesAroundPosition(this.currentEventPosition, this.sensingRange, this.exposedNodes, this.exposedDistances);

    //Initialize every sensingReadingRange to zero ...
    for(nodeId = 0; nodeId < this.numNodes; nodeId++) {
      this.sensorReadingRanges[nodeId][0] = (double)0.0;
      this.sensorReadingRanges[nodeId][1] = (double)0.0;
    }

    //Iterate over the list of exposedNodes and update *ONLY* their sensingReadingRanges ...
    for(int index = 0; index < this.exposedNodes.size(); index++) {
      nodeId = ((Integer)(this.exposedNodes.elementAt(index))).intValue();
      distance = ((Double)(this.exposedDistances.elementAt(index))).doubleValue();
      getSensingReadingRangeFor(distance, this.sensorReadingRanges[nodeId]);
    }

  }

  public Vector getExposedNodesVector() {
    return this.exposedNodes;
  }

  public double[] getSensingReadingRangeFor(int nodeId) {
    return this.sensorReadingRanges[nodeId];
  }

  public void updateEventPosition(Coordinates newEventPosition_) {
    this.currentEventPosition = newEventPosition_;
    updateSensorReadingRanges();
  }
}
