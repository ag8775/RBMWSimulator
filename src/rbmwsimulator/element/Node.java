package rbmwsimulator.element;

import simulator.element.*;
import rbmwsimulator.mote.*;
import rbmwsimulator.util.TopologyReader;
import rbmwsimulator.util.NodeScenarioReader;
import rbmwsimulator.protocol.Status;
import rbmwsimulator.util.Preferences;
import simulator.util.Output;
import simulator.Simulator;
import rbmwsimulator.generators.SensingEvent;
import rbmwsimulator.protocol.message.Message;
import rbmwsimulator.model.WirelessNeighborhoodModel;
import cern.jet.random.Uniform;
import edu.cornell.lassp.houle.RngPack.RandomElement;
import java.util.Vector;
import rbmwsimulator.protocol.*;

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

/**
 Node implements a generic sensor network node, i.e. a mica2, micaz, ..., mote
 */

public class Node extends Element {
    //Node basics
    private String nodeName;
    private int nodeStatus; // The current status of the Node, either Preferences.STATUS_UP or Preferences.STATUS_DOWN
    private boolean simulationRunning;
    private int nodeId;
    private Coordinates coordinates;
    private Mote mica2mote;
    private Vector neighbors;
    private Vector msgQueue;

    //Scenario readers
    private TopologyReader topoReader;
    private NodeScenarioReader nodeScenarioReader;

    //Wireless channel
    private WirelessNeighborhoodModel wirelessModel;

    //Energy Monitor

    //Random numbers
    private RandomElement randomNumberGenerator;
    private Uniform uniformRandomNumberGenerator;

    //Protocols
    private SimpleCSMAMacProtocol macProtocolAgent; //Wireless MAC Protocol
    private NeighborDiscoveryProtocol neighborDiscoveryProtocolAgent; //Neighbor Discovery
    private RBMWProtocol rbmwProtocolAgent; //Middleware Protocol
    //Sub-middleware protocols ...
    private RoleAdvertisementProtocol radvProtocolAgent; //Role Advertisement
    private RoleAssignmentProtocol raProtocolAgent; //Role Assignment
    private RoleExecutionProtocol reProtocolAgent; //Role Execution
    private RolePerformanceFeedbackProtocol rfProtocolAgent; //Role Feedback

    public Node(int nodeId_, Coordinates coordinates_, Mote mica2mote_,
                int nodeStatus_) {
        this.nodeId = nodeId_;
        this.coordinates = coordinates_;
        this.mica2mote = mica2mote_;
        this.nodeStatus = nodeStatus_;
        this.nodeName = (new Integer(nodeId)).toString();

    }

    public Node(int nodeId_, TopologyReader topoReader_,
                NodeScenarioReader nodeScenarioReader_, int nodeStatus_) {
        this.nodeId = nodeId_;
        this.topoReader = topoReader_;
        this.nodeScenarioReader = nodeScenarioReader_;
        this.mica2mote = this.nodeScenarioReader.getMoteObjectFor(this.nodeId);
        this.coordinates = this.topoReader.getCoordinatesFor(this.nodeId);
        this.nodeStatus = nodeStatus_;
        this.nodeName = (new Integer(nodeId)).toString();
    }

    public void initialize() {
      this.simulationRunning = true;
      this.neighbors = new Vector();
      this.msgQueue = new Vector();
      initializeRandomNumberGenerator();
      initializeProtocols();
    }

    private void initializeRandomNumberGenerator() {
        randomNumberGenerator = new cern.jet.random.engine.MersenneTwister(this.nodeId);
        uniformRandomNumberGenerator = new Uniform(randomNumberGenerator);
    }

    private void initializeProtocols() {
       Output.SIMINFO(this.toString() + ", " + Simulator.getInstance().getTime() +
                      " seconds: Node " +
                      this.nodeId + " initializing !!!",
                      Preferences.PRINT_CURRENT_NODE_STATE_DETAILS);
       //Initialize CSMA MAC Protocol
       macProtocolAgent = new SimpleCSMAMacProtocol((Node)this);
       // Initialize RBMW Protocol Agent ...
       rbmwProtocolAgent = new RBMWProtocol((Node)this);
       //Initialize Neighbor Discovery Protocol Agent ...
       neighborDiscoveryProtocolAgent = new NeighborDiscoveryProtocol((Node)this);
   }



    /**
     * Returns a uniformly distributed random number in the open interval <tt>(from,to)</tt> (excluding <tt>from</tt> and <tt>to</tt>).
     * Pre conditions: <tt>from &lt;= to</tt>.
     */
    private double getRandomNumberWithinOpenInterval(double from, double to) {
        return this.uniformRandomNumberGenerator.nextDoubleFromTo(from, to);
    }

    public WirelessNeighborhoodModel getWirelessNeighborHoodModel() {
        return this.wirelessModel;
    }

    public TopologyReader getTopologyReader() {
        return this.topoReader;
    }

    public Transceiver getTransceiver() {
        return this.mica2mote.radio;
    }

    public void setWirelessNeighborhoodModel(WirelessNeighborhoodModel
                                             wirelessModel_) {
        this.wirelessModel = wirelessModel_;
    }

    public void update() {

    }

    public void dump() {
        //System.out.println("Neighbors Info "+vtoString(this.neighbors));
        Output.SIMINFO(this.toString() + ", [Node " + this.nodeId + ", " +
                       Status.node_status[this.nodeStatus] + "], " +
                       this.coordinates.toString() + ", " +
                       this.mica2mote.toString(),
                       Preferences.PRINT_CURRENT_NODE_STATE_DETAILS);
    }

    public int getNodeId() {
        return this.nodeId;
    }

    public int getStatus() {
        return this.nodeStatus;
    }

    public void setFinished() {
        Output.SIMINFO(this.toString() + ", " + Simulator.getInstance().getTime() +
                       " seconds: Node " +
                       this.nodeId + " finishing !!!",
                       Preferences.PRINT_CURRENT_NODE_STATE_DETAILS);
        this.simulationRunning = false;
    }

    public boolean continueSimulation() {
        return this.simulationRunning;
    }

    /**
         Set the status of this Node, either 'up' or 'down'. Use the integer
         constants provided in rbmwsimulator.util.Status.
         @param status the new status of the node element
     */
    public void setStatus(int status) {
        this.nodeStatus = status;
        if (status == Status.UP) {
            this.wirelessModel.updateNodeAsUp(nodeId);
            Output.SIMINFO(this.toString() + ", " +
                           Simulator.getInstance().getTime() +
                           " seconds: Node " + nodeId + " is UP",
                           Preferences.PRINT_CURRENT_NODE_STATE_DETAILS);
        } else {
            this.wirelessModel.updateNodeAsDown(nodeId);
            Output.SIMINFO(this.toString() + ", " +
                           Simulator.getInstance().getTime() +
                           " seconds: Node " + nodeId + " is DOWN",
                           Preferences.PRINT_CURRENT_NODE_STATE_DETAILS);
        }
        rbmwProtocolAgent.run();
    }


    /**
     *  Set the reading for an event sensed by an on-board sensor ...
     * @param msg Message
     */

    public void setSensorReadingForEvent(SensingEvent sensingEvent,
                                         long eventPosX, long eventPosY,
                                         double sensorReading) {
        Output.SIMINFO(this.toString() + ", " + Simulator.getInstance().getTime() +
                       " seconds: Node " + nodeId +
                       " got an updated reading of " + sensorReading +
                       " for event " + sensingEvent.toString() + "{" +
                       eventPosX + ", " + eventPosY + "}", false);
        //if the event can be sensed by an on-board sensor
    }

    public void receiveMessage(Message msg) {
        //Need to make sure if the node is in receive mode. If not i.e. sleep or transmitting,
        //then we need to drop this message and also garble our transmission
        rbmwProtocolAgent.recv(msg);
    }

    public void timerExpired(int timerId) {
        this.rbmwProtocolAgent.timerExpired(timerId);
        this.rbmwProtocolAgent.run();
    }

    public Vector processReceivedMessages(int msgType) {
        //XXX: Delete messages that have the same reception timestamp to signify collision ...
        Vector messages = new Vector();
        for (int index = 0; index < this.msgQueue.size(); index++) {
            Message msg = (Message)this.msgQueue.elementAt(index);
            if (msg.getMessageType() == msgType) {
                messages.addElement(msg);
            }
        }
        this.msgQueue.removeAll(messages);
        return messages;
    }


}
