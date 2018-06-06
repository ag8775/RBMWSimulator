package rbmwsimulator.util;
import java.io.*;
import java.util.Vector;
import java.util.StringTokenizer;
import rbmwsimulator.element.Coordinates;
import rbmwsimulator.util.Preferences;
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
public class TopologyReader {
  private String topologyFileName;
  private int numNodes;
  private Coordinates coordinates[];

  public TopologyReader(String topologyFileName_, int numNodes_) {
    this.topologyFileName = topologyFileName_;
    this.numNodes = numNodes_;
    this.coordinates = new Coordinates[this.numNodes];
  }

  public Coordinates getCoordinatesFor(int nodeId) {
    //Output.SIMINFO(this.coordinates[nodeId].toString(), this.logTrace);
    return this.coordinates[nodeId];
  }

  public long xPos(int nodeId) {
      return this.coordinates[nodeId].getX();
  }

  public long yPos(int nodeId)  {
      return this.coordinates[nodeId].getY();
  }

  public Coordinates[] getNodePositions() {
    return this.coordinates;
  }

  public void readTopologyScenarioFile() {
    String record = null;
    String delimiters = new String(": ,;");
    int nodeId, coordX, coordY;
    try {
      FileReader fr = new FileReader(this.topologyFileName);
      BufferedReader br = new BufferedReader(fr);
      record = new String();
      while ((record = br.readLine()) != null) {
        StringTokenizer st = new StringTokenizer(record, delimiters);
        while(st.hasMoreTokens()) {
          nodeId = Integer.valueOf(st.nextToken()).intValue();
          coordX = Integer.valueOf(st.nextToken()).intValue();
          coordY = Integer.valueOf(st.nextToken()).intValue();
          this.coordinates[nodeId] = new Coordinates(coordX, coordY);
        } //end of Inner While
      } // end of Outer While
      fr.close();
      br.close();
    }
    catch (IOException e) {
      // catch possible io errors from readLine()
      Output.ERR(this.toString()+": Uh oh, got an IOException error!", Preferences.PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS);
      e.printStackTrace();
    }
  }
}
