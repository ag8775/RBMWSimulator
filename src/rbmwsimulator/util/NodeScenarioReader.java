package rbmwsimulator.util;
import java.io.*;
import java.util.Vector;
import java.util.StringTokenizer;
import simulator.util.Output;
import rbmwsimulator.mote.Mote;
import rbmwsimulator.mote.SensorTypes;
import rbmwsimulator.protocol.Status;
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
public class NodeScenarioReader {
  private String nodeScenarioFileName;
  private int numNodes;
  private Mote mote[];
  private long seed;
  private int luxury;

  public NodeScenarioReader(String nodeScenarioFileName_, int numNodes_, long seed_, int luxury_) {
    this.nodeScenarioFileName = nodeScenarioFileName_;
    this.numNodes = numNodes_;
    this.mote = new Mote[this.numNodes];
    this.seed = seed_;
    this.luxury = luxury_;
  }

  public Mote getMoteObjectFor(int nodeId) {
   //System.out.println(" nodeId = "+nodeId);
   //Output.SIMINFO(this.mote[nodeId].toString(), this.logTrace);
   return this.mote[nodeId];
 }

 public void readNodeScenarioFile() {
   String record = null;
   String delimiters = new String(": { } , ;");
   String sensorName;
   int nodeId, numSensors;
   int sensingRange; //in meters
   int memory; //in MB
   int cpuSpeed; //in Mhz
   float radioBR; //in Kbps
   int radioRange; //in meters
   float energy; //in Joules

   try {
     FileReader fr = new FileReader(this.nodeScenarioFileName);
     BufferedReader br = new BufferedReader(fr);
     record = new String();
     while ((record = br.readLine()) != null) {
       StringTokenizer st = new StringTokenizer(record, delimiters);
       //nodeId: sensingRange, radioRange, radioBR, cpuSpeed, memory, energy, numSensors, {sensor1, sensor2, };
       while(st.hasMoreTokens()) {
         nodeId = Integer.valueOf(st.nextToken()).intValue();

         //Parse sensingRange, radioRange, radioBR, cpuSpeed, memory, energy
         sensingRange = Integer.valueOf(st.nextToken()).intValue();
         radioRange = Integer.valueOf(st.nextToken()).intValue();
         radioBR = Float.valueOf(st.nextToken()).floatValue();
         cpuSpeed = Integer.valueOf(st.nextToken()).intValue();
         memory = Integer.valueOf(st.nextToken()).intValue();
         energy = Float.valueOf(st.nextToken()).floatValue();

         //Parse the number of sensors ...
         numSensors = Integer.valueOf(st.nextToken()).intValue();
         //Output.SIMINFO(nodeId+": "+numSensors);
         //Instantiate a new Mote Object ...
         this.mote[nodeId] = new Mote(numSensors);

         //Parse the sensor names ...
         int sensorIndex = 0;
         while(sensorIndex < numSensors) {
           sensorName = st.nextToken();
           //Configure the sensors ...
           this.mote[nodeId].configureSensor(sensorIndex, SensorTypes.getSensorType(sensorName), sensingRange, this.seed, this.luxury);
           sensorIndex++;
         }
         //Configure the battery ...
         this.mote[nodeId].configureBattery(energy, Preferences.DEFAULT_IDLE_BATTERY_DISSIPATION_RATE);
         //Configure the memory ...
         this.mote[nodeId].configureMemory(memory);
         //Configure the radio ...
         this.mote[nodeId].configureTransceiver(Status.RADIO_IDLE, Preferences.DEFAULT_RADIO_SWITCHING_DELAY, radioBR, radioRange);
         //Configure the processor ...
         this.mote[nodeId].configureProcessor(cpuSpeed);
       } //end of Inner While
     } // end of Outer While
     fr.close();
     br.close();
   }
   catch (IOException e) {
     // catch possible io errors from readLine()
     Output.ERR(this.toString()+": Uh oh, got an IOException error!", true);
     e.printStackTrace();
   }
 }
}
