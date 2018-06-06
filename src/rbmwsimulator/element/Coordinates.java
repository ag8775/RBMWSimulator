package rbmwsimulator.element;

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

public class Coordinates {
  private long coordX;
  private long coordY;

  public Coordinates(long coordX, long coordY) {
    this.coordX = coordX;
    this.coordY = coordY;
  }

  public void setCoords(long coordX, long coordY) {
    this.coordX = coordX;
    this.coordY = coordY;
  }

  public void setX(long coordX) {
    this.coordX = coordX;
  }

  public void setY(long coordY) {
     this.coordY = coordY;
   }

  public long getX() {
     return this.coordX;
   }

  public long getY() {
    return this.coordY;
  }

  public String toString() {
    return "{"+new Long(this.coordX).toString()+", "+new Long(this.coordY).toString()+"}";
  }

}
