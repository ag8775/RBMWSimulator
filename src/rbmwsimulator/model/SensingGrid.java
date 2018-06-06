package rbmwsimulator.model;
import java.util.Vector;
import rbmwsimulator.element.Coordinates;
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

public class SensingGrid {
  private long maxGridX, maxGridY;
  private long minGridX, minGridY;
  private Vector gridNodeIds;
  private int gridId;
  private int [] neighboringSensingGrids;

  public SensingGrid(int gridId_, long minGridX_, long minGridY_, long maxGridX_, long maxGridY_) {
    this.gridId = gridId_;
    this.minGridX = minGridX_;
    this.minGridY = minGridY_;
    this.maxGridX = maxGridX_;
    this.maxGridY = maxGridY_;
    this.gridNodeIds = new Vector();
    //8 neighbors max: Left, Left-UP, Up, Right-up, Right, Right-down, down, left-down
    this.neighboringSensingGrids = new int[8];
  }

  public void setNeighborGridAtDirection(int direction, int neighborGridId) {
    this.neighboringSensingGrids[direction] = neighborGridId;
  }

  public int getNeighboringGridAtDirection(int direction) {
    return this.neighboringSensingGrids[direction];
  }

  public int getGridId() {
    return this.gridId;
  }

  public long getMinGridX() {
    return this.minGridX;
  }

  public long getMinGridY() {
    return this.minGridY;
  }

  public long getMaxGridX() {
    return this.maxGridX;
  }

  public long getMaxGridY() {
    return this.maxGridY;
  }

  public int getNumNodes() {
    return this.gridNodeIds.size();
  }

  public Vector getNodesIds() {
    return this.gridNodeIds;
  }

  public void findNodesInGrid(Coordinates[] nodePositions_) {
    int numNodes = nodePositions_.length;
    int nodeId = 0;
    long coordX, coordY;
    this.gridNodeIds.removeAllElements();
    for(nodeId = 0; nodeId < numNodes; nodeId++) {
      coordX = nodePositions_[nodeId].getX();
      coordY = nodePositions_[nodeId].getY();
      if(coordX <= this.maxGridX) {
        if(coordX >= this.minGridX) {
          if(coordY <= this.maxGridY) {
            if(coordY >= this.minGridY) {
              gridNodeIds.addElement(new Integer(nodeId));
            }
          }
        }
      }
    }//end for ...
  }
}
