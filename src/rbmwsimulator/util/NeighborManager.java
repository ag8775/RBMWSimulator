package rbmwsimulator.util;
import java.util.Vector;
import rbmwsimulator.util.TopologyReader;
import rbmwsimulator.element.*;
import rbmwsimulator.mote.*;
import rbmwsimulator.protocol.Status;
import simulator.util.Output;
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
public class NeighborManager {
  private Node node[];
  private Vector neighbors[];
  private int numNodes;
  private TopologyReader topoReader;
  private NodeScenarioReader nodeScenarioReader;

  public NeighborManager(int numNodes_, Node[] nodes_, TopologyReader topoReader_, NodeScenarioReader nodeScenarioReader_) {
    this.numNodes = numNodes_;
    this.node = nodes_;
    this.neighbors = new Vector[this.numNodes];
    for(int nodeId = 0; nodeId < this.numNodes; nodeId++) {
      //First allocate a new vector for every node ...
      this.neighbors[nodeId] = new Vector();
    }
    this.topoReader = topoReader_;
    this.nodeScenarioReader = nodeScenarioReader_;
  }

  public void findNeighbors() {
    int nodeId, neighborId;
    Coordinates nodeCoordinate;
    Coordinates neighborCoordinate;
    int nodeRadioRange, neighborRadioRange;
    long nodeNeighborDistanceSquared;
    long nodeRadioRangeSquared, neighborRadioRangeSquared;
    for(nodeId = 0; nodeId < this.numNodes; nodeId++) {

      //Get this node coordinates
      nodeCoordinate = topoReader.getCoordinatesFor(nodeId);
      //Get its radioRange ...
      //XXX: I am assuming that the transmission range is the same as the reception range ...
      nodeRadioRange = ((Mote)(this.nodeScenarioReader.getMoteObjectFor(nodeId))).radio.getRadioRange();
      nodeRadioRangeSquared = nodeRadioRange * nodeRadioRange;
      for(neighborId = (nodeId+1); neighborId < this.numNodes; neighborId++) {
        neighborCoordinate = topoReader.getCoordinatesFor(neighborId);
        neighborRadioRange = ((Mote)(this.nodeScenarioReader.getMoteObjectFor(neighborId))).radio.getRadioRange();
        neighborRadioRangeSquared = neighborRadioRange*neighborRadioRange;
        //Calculate the square of the distance between this node and every other node ...
        Coordinates diffCoordinates = new Coordinates((neighborCoordinate.getX() - nodeCoordinate.getX()), (neighborCoordinate.getY() - nodeCoordinate.getY()));
        nodeNeighborDistanceSquared = diffCoordinates.getX()*diffCoordinates.getX() + diffCoordinates.getY()*diffCoordinates.getY();
        if(nodeRadioRangeSquared >= nodeNeighborDistanceSquared) {
          //neighborId is a neighbor of nodeId
          this.neighbors[nodeId].add(new Integer(neighborId));
        }
        if(neighborRadioRangeSquared >= nodeNeighborDistanceSquared) {
          //nodeId is the neighbor of neighborId
          this.neighbors[neighborId].add(new Integer(nodeId));
        }
      }
    }
  }

  public Vector getNeighborsFor(int nodeId) {
    return this.neighbors[nodeId];
  }

  public void getActiveNeighborsFor(int nodeId, Vector activeNeighbors) {
    activeNeighbors.removeAllElements();
    //Enumerate over all my neighbors and return only those that are STATUS.UP
    for(int neighborId = 0; neighborId < this.neighbors[nodeId].size(); neighborId++) {
      if(this.node[neighborId].getStatus() == Status.UP) {
        activeNeighbors.add(new Integer(neighborId));
      }
    }
  }

  public String toString(Vector neighbors) {
    String str = new String();
    int size = neighbors.size();
    if(size != 0) {
      for (int i = 0; i < (size - 1) ; i++) {
        Integer neighObject = (Integer) neighbors.elementAt(i);
        str += neighObject.toString()+ ", " ;
      }
      Integer neighObject = (Integer) neighbors.elementAt(size-1);
      str += neighObject.toString()  ;
    }
    return str;
  }

  public void dump() {
    int nodeId;
    for(nodeId = 0; nodeId < this.numNodes; nodeId++) {
      Output.SIMINFO(this.toString()+", "+nodeId+": ["+this.neighbors[nodeId].size()+" {"+toString(this.neighbors[nodeId])+"}]", Preferences.PRINT_WIRELESS_NEIGHBORHOOD_MODEL_DETAILS);
    }
  }


}
