package rbmwsimulator.model;
import rbmwsimulator.model.Direction;
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
public abstract class SensingModel {
  private double [] sensingThresholds;
  private double [] distanceThresholds;
  private Coordinates[] nodePositions;
  private long maxX, maxY;
  private long gridDimX, gridDimY;
  private SensingGrid [] sensingGrids;
  private int numGrids;
  private int numXGrids;
  private int numYGrids;
  private int modelType; //Point Exposure Model, Wave Exposure Model

  public SensingModel(int modelType_, long maxX_, long maxY_, long gridDimX_, long gridDimY_, Coordinates[] nodePositions_, double[] sensingThresholds_, double[] distanceThresholds_) {
    this.modelType = modelType_;
    this.maxX = maxX_;
    this.maxY = maxY_;
    this.gridDimX = gridDimX_;
    this.gridDimY = gridDimY_;
    this.nodePositions = nodePositions_;
    this.sensingThresholds = sensingThresholds_;
    this.distanceThresholds = distanceThresholds_;
    this.numGrids = 0;
    gridifySensingDeploymentArea();
    calculateSensingGridNeighbors();
  }

  public int getModelType() {
    return this.modelType;
  }

  public double[] getSensingThresholds() {
    return this.sensingThresholds;
  }

  public double[] getDistanceThresholds() {
    return this.distanceThresholds;
  }

  public Coordinates[] getNodePositions() {
    return this.nodePositions;
  }

  public Coordinates getNodePositionFor(int nodeId) {
    return this.nodePositions[nodeId];
  }

  private void gridifySensingDeploymentArea() {
    //First determine the number of sensing grids ...
    long minGridX = 0, minGridY = 0, maxGridX = 0, maxGridY = 0;
    int xGridIndex = 0, yGridIndex = 0, gridId = 1; //Initializing gridId to 1 ...
    this.numXGrids = Math.round(((float)this.maxX)/((float)this.gridDimX));
    this.numYGrids = Math.round(((float)this.maxY)/((float)this.gridDimY));
    this.numGrids = this.numXGrids * this.numYGrids;
    //System.out.println("numXGrids = "+numXGrids+" numYGrids = "+numYGrids+" numGrids = "+numGrids);
    sensingGrids = new SensingGrid[numGrids+1];

    for(yGridIndex = 0; yGridIndex < this.numYGrids; yGridIndex++) {
      if(maxGridY == this.maxY) {
        minGridY = 0;
        maxGridY = 0;
      }
      else
        minGridY = maxGridY;

      maxGridY += this.gridDimY;

      if(maxGridY > this.maxY)
        maxGridY = this.maxY;

      for(xGridIndex = 0; xGridIndex < this.numXGrids; xGridIndex++) {
        if(maxGridX == this.maxX) {
          minGridX = 0;
          maxGridX = 0;
        }
        else
          minGridX = maxGridX;

        maxGridX += this.gridDimX;

        if(maxGridX > this.maxX)
          maxGridX = this.maxX;

        //System.out.println("gridId = "+gridId+" minGridX = "+minGridX+" maxGridX = "+maxGridX+" minGridY = "+minGridY+" maxGridY = "+maxGridY);

        sensingGrids[gridId] = new SensingGrid(gridId, minGridX, minGridY, maxGridX, maxGridY);
        sensingGrids[gridId].findNodesInGrid(this.nodePositions);
        ++gridId;
      }
    }
  }

  private void calculateSensingGridNeighbors() {
    long minGridX = 0, minGridY = 0, maxGridX = 0, maxGridY = 0;
    for(int gridId = 1; gridId <= this.numGrids; gridId++) {
      minGridX = sensingGrids[gridId].getMinGridX();
      minGridY = sensingGrids[gridId].getMinGridY();
      maxGridX = sensingGrids[gridId].getMaxGridX();
      maxGridY = sensingGrids[gridId].getMaxGridY();

      //Fill in the grid id using simple relative indexing based on directions ...
      sensingGrids[gridId].setNeighborGridAtDirection(Direction.WEST, gridId - 1);
      sensingGrids[gridId].setNeighborGridAtDirection(Direction.NORTH_WEST, gridId + 4);
      sensingGrids[gridId].setNeighborGridAtDirection(Direction.NORTH, gridId + 5);
      sensingGrids[gridId].setNeighborGridAtDirection(Direction.NORTH_EAST, gridId + 6);
      sensingGrids[gridId].setNeighborGridAtDirection(Direction.EAST, gridId + 1);
      sensingGrids[gridId].setNeighborGridAtDirection(Direction.SOUTH_EAST, gridId - 4);
      sensingGrids[gridId].setNeighborGridAtDirection(Direction.SOUTH, gridId - 5);
      sensingGrids[gridId].setNeighborGridAtDirection(Direction.SOUTH_WEST, gridId - 6);

      //Prune the non-neighbors under certain conditions, such as:
      // if minGridX == 0 then West = 0
      // if minGridX == 0 || maxGridY == maxY then NORTH_WEST = 0
      // if maxGridY == maxY then NORTH = 0
      // if maxGridY == maxY || maxGridX == maxX then NORTH_EAST = 0
      // if maxGridX == maxX then EAST = 0
      // if maxGridX == maxX || minGridY == 0 then SOUTH_EAST = 0
      // if minGridY == 0 then SOUTH = 0
      // if minGridY == 0 || minGridX = 0 then SOUTH_WEST = 0
      if(minGridX == 0)
        sensingGrids[gridId].setNeighborGridAtDirection(Direction.WEST, 0);

      if(minGridX == 0 || maxGridY == this.maxY)
        sensingGrids[gridId].setNeighborGridAtDirection(Direction.NORTH_WEST, 0);

      if(maxGridY == this.maxY)
        sensingGrids[gridId].setNeighborGridAtDirection(Direction.NORTH, 0);

      if(maxGridY == this.maxY || maxGridX == this.maxX)
        sensingGrids[gridId].setNeighborGridAtDirection(Direction.NORTH_EAST, 0);

      if(maxGridX == this.maxX)
        sensingGrids[gridId].setNeighborGridAtDirection(Direction.EAST, 0);

      if(maxGridX == this.maxX || minGridY == 0)
        sensingGrids[gridId].setNeighborGridAtDirection(Direction.SOUTH_EAST, 0);

      if(minGridY == 0)
        sensingGrids[gridId].setNeighborGridAtDirection(Direction.SOUTH, 0);

      if(minGridY == 0 || minGridX == 0)
        sensingGrids[gridId].setNeighborGridAtDirection(Direction.SOUTH_WEST, 0);

    }
  }

 private void findGridsEnclosedBetween(long minEnclosingGridLineX, long maxEnclosingGridLineX, long minEnclosingGridLineY, long maxEnclosingGridLineY, Vector enclosedGrids) {
   //First determine the number of sensing grids ...
   long minGridX = 0, minGridY = 0, maxGridX = 0, maxGridY = 0;
   int xGridIndex = 0, yGridIndex = 0, gridId = 1; //Initializing gridId to 1 ...
   enclosedGrids.removeAllElements();
   for(yGridIndex = 0; yGridIndex < this.numYGrids; yGridIndex++) {
     if(maxGridY == this.maxY) {
       minGridY = 0;
       maxGridY = 0;
     }
     else
       minGridY = maxGridY;

     maxGridY += this.gridDimY;

     if(maxGridY > this.maxY)
       maxGridY = this.maxY;

     for(xGridIndex = 0; xGridIndex < this.numXGrids; xGridIndex++) {
       if(maxGridX == this.maxX) {
         minGridX = 0;
         maxGridX = 0;
       }
       else
         minGridX = maxGridX;

       maxGridX += this.gridDimX;

       if(maxGridX > this.maxX)
         maxGridX = this.maxX;

       if((minGridX >= minEnclosingGridLineX)&&(maxGridX > minEnclosingGridLineX)&&(maxGridX <= maxEnclosingGridLineX)&&(minGridY >= minEnclosingGridLineY)&&(maxGridY > minEnclosingGridLineY)&&(maxGridY <= maxEnclosingGridLineY)) {
         enclosedGrids.add(new Integer(gridId));
       }
       ++gridId;
     }
   }
 }

 public void findNodesAroundPosition(Coordinates eventPosition, double sensingRange, Vector exposedNodes, Vector exposedDistances) {
    exposedNodes.removeAllElements(); //includes the neighboring sensors within the largest exposed distance ...
    exposedDistances.removeAllElements(); //includes the distance to which the event is away from the neighboring sensors ...
    Vector enclosedGrids = new Vector();
    //Find the maximum-enclosing grid-lines for an event at eventPosition
    int xGridIndex = 0, yGridIndex = 0, gridId = 1, nodeId;
    long minGridX = 0, minGridY = 0, maxGridX = 0, maxGridY = 0;
    double distance = this.maxX;
    double sensingRangeSquared = Math.pow(sensingRange, 2);
    boolean foundMinX = false, foundMaxX = false, foundMinY = false, foundMaxY = false;
    long minEnclosingGridLineX = 0, maxEnclosingGridLineX = this.maxX, minEnclosingGridLineY = 0, maxEnclosingGridLineY = this.maxY;

    for(yGridIndex = 0; yGridIndex < this.numYGrids; yGridIndex++) {
      if(maxGridY == this.maxY) {
        minGridY = 0;
        maxGridY = 0;
      }
      else
        minGridY = maxGridY;

      maxGridY += this.gridDimY;

     if(maxGridY > this.maxY)
       maxGridY = this.maxY;

     if(!foundMinY) {
       distance = minGridY - eventPosition.getY();
       if(distance <= 0) {
         if(Math.abs(distance) <= sensingRange) {
           if((minGridY - this.gridDimY) >= 0) //initialize to previous gridLine
             minEnclosingGridLineY = minGridY - this.gridDimY;
           else
             minEnclosingGridLineY = minGridY; //else use the current minGridX
           foundMinY = true;
        }//if distance is within the sensingRange
      } //if we are on the bottom side of the eventPosition
    }//end of if(!foundMinY)

    if(!foundMaxY) {
      distance = maxGridY - eventPosition.getY();
      if(distance >= 0) {
        if(distance <= sensingRange) {
          if((maxGridY + this.gridDimY) <= this.maxY) //initialize to next gridLine
            maxEnclosingGridLineY = maxGridY + this.gridDimY;
          else
            maxEnclosingGridLineY = maxGridY; //else use the current minGridX
          foundMaxY = true;
        }//if distance is within the sensingRange
      } //if we are on the up side of the eventPosition
    }//end of if(!foundMaxY)

     for(xGridIndex = 0; xGridIndex < this.numXGrids; xGridIndex++) {
       if(maxGridX == this.maxX) {
         minGridX = 0;
         maxGridX = 0;
       }
       else
         minGridX = maxGridX;

     maxGridX += this.gridDimX;

     if(maxGridX > this.maxX)
       maxGridX = this.maxX;

     if(!foundMinX) {
       distance = minGridX - eventPosition.getX();
       if(distance <= 0) {
         if(Math.abs(distance) <= sensingRange) {
           if((minGridX - this.gridDimX) >= 0) //initialize to previous gridLine
             minEnclosingGridLineX = minGridX - this.gridDimX;
           else
             minEnclosingGridLineX = minGridX; //else use the current minGridX
           foundMinX = true;
         }//if distance is within the sensingRange
       } //if we are on the Left side of the eventPosition
     }//end of if(!foundMinX)

     if(!foundMaxX) {
       distance = maxGridX - eventPosition.getX();
       if(distance >= 0) {
         if(distance <= sensingRange) {
           if((maxGridX + this.gridDimX) <= this.maxX) //initialize to next gridLine
             maxEnclosingGridLineX = maxGridX + this.gridDimX;
           else
             maxEnclosingGridLineX = maxGridX; //else use the current minGridX
           foundMaxX = true;
         }//if distance is within the sensingRange
       } //if we are on the Left side of the eventPosition
     }//end of if(!foundMaxX)
    }//end of for loop
   } //end of for loop

   //Find grids that are enclosed by these gridlines ...
   findGridsEnclosedBetween(minEnclosingGridLineX, maxEnclosingGridLineX, minEnclosingGridLineY, maxEnclosingGridLineY, enclosedGrids);

   //Add only those nodes whose distance <= sensingRange
   for(int gridIndex = 0; gridIndex < enclosedGrids.size(); gridIndex++) {
     gridId = ((Integer)(enclosedGrids.elementAt(gridIndex))).intValue();
     Vector nodesInGrid = sensingGrids[gridId].getNodesIds();
     for(int nodeIndex = 0; nodeIndex < nodesInGrid.size(); nodeIndex++) {
       nodeId = ((Integer)(nodesInGrid.elementAt(nodeIndex))).intValue();
       distance = Math.pow(nodePositions[nodeId].getX() - eventPosition.getX(), 2) + Math.pow(nodePositions[nodeId].getY() - eventPosition.getY(), 2);
       if(distance <= sensingRangeSquared) {
         exposedNodes.add(new Integer(nodeId));
         exposedDistances.add(new Double(Math.sqrt(distance)));
       }
     }
   }
  }

  public void getSensingReadingRangeFor(double distance, double[] readingRanges) {
    int index = 0;
    int length = this.sensingThresholds.length;
    //For sensors that are very far from the event ...
    if(distance > this.distanceThresholds[length-1]) {
      readingRanges[0] = this.sensingThresholds[length-1];//high reading range
      readingRanges[1] = 0.0;//low reading range
    } else {
      //For sensors that are nearby to the event ...
      int foundIndex = 0;
      for(index = length-1; index > 0; index--) { //No need to compare the distance for index == 0
        if(distance <= this.distanceThresholds[index]) {
          foundIndex = index;
          //break;
        }
      }
      if(foundIndex > 0) {
        //For sensors that have a distance between two distance thresholds ...
        readingRanges[0] = this.sensingThresholds[foundIndex-1]; //high reading range
        readingRanges[1] = this.sensingThresholds[foundIndex]; //low reading range
      } else {
        //For events that are very close to the sensor, we take the high and the low range to be the highest sensor reading ...
        readingRanges[0] = this.sensingThresholds[0];
        readingRanges[1] = this.sensingThresholds[0];
      }
    }
  }

  /**
   * These update functions have to be overridden by subclasses.
   */
   public abstract void updateSensorReadingRanges();
   public abstract Vector getExposedNodesVector();
   public abstract double[] getSensingReadingRangeFor(int nodeId);
   public abstract void updateEventPosition(Coordinates newEventPosition_);

}
