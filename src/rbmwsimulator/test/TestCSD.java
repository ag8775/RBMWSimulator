package rbmwsimulator.test;
import java.util.*;
import rbmwsimulator.protocol.metric.SensingCell;

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
 * @version 2.0
 */
public class TestCSD {
  private  int myId;
  private SensingCell myCell;


  public TestCSD(int myId, SensingCell myCell) {
    this.myId = myId;
    this.myCell = myCell;
  }

  public void getEqualUnequalSPVs() {
    this.myCell.parseEqualUnequalSPVs(this.myCell.listAllSPVs());
  }

  public void calculateMaxCSD() {
    this.myCell.cspvWithAllNeighbors();
  }

  public void printMaxCSD() {
    System.out.println("Max CSD = "+this.myCell.getMaxCSPV());
  }

  public static void main(String[] args) {
     int nodeId = 1;
     SensingCell cell = new SensingCell(nodeId, 1.02, 1);
     SensingCell temp_cell = new SensingCell(nodeId+1, 2.02, 2);

     cell.addMatchingCell(temp_cell);
     temp_cell = new SensingCell(nodeId+2, 3.04, 3);
     cell.addMatchingCell(temp_cell);

     temp_cell = new SensingCell(nodeId+3, 4.067, 4);
     cell.addMatchingCell(temp_cell);

     temp_cell = new SensingCell(nodeId+4, 5.094, 5);
     cell.addMatchingCell(temp_cell);

     temp_cell = new SensingCell(nodeId+5, 6.087, 6);
     cell.addMatchingCell(temp_cell);

     temp_cell = new SensingCell(nodeId+6, 7.78, 7);
     cell.addMatchingCell(temp_cell);

     temp_cell = new SensingCell(nodeId+7, 8.98, 8);
     cell.addMatchingCell(temp_cell);

     temp_cell = new SensingCell(nodeId+8, 9.12, 9);
     cell.addMatchingCell(temp_cell);

     temp_cell = new SensingCell(nodeId+9, 6.12, 6);
    cell.addMatchingCell(temp_cell);

    temp_cell = new SensingCell(nodeId+10, 6.78, 6);
    cell.addMatchingCell(temp_cell);

    temp_cell = new SensingCell(nodeId+11, 6.78, 6);
     cell.addMatchingCell(temp_cell);

     temp_cell = new SensingCell(nodeId+12, 3.78, 3);
    cell.addMatchingCell(temp_cell);

    temp_cell = new SensingCell(nodeId+13, 3.21, 3);
    cell.addMatchingCell(temp_cell);

    temp_cell = new SensingCell(nodeId+14, 4.21, 4);
    cell.addMatchingCell(temp_cell);

    TestCSD test = new TestCSD(nodeId, cell);
    test.getEqualUnequalSPVs();
    test.calculateMaxCSD();
    test.printMaxCSD();
  }
}
