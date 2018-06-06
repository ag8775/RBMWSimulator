package rbmwsimulator.protocol;
import rbmwsimulator.element.Node;
import rbmwsimulator.util.Preferences;
import simulator.util.Output;
import simulator.Simulator;
import rbmwsimulator.protocol.message.*;
import rbmwsimulator.protocol.TDMAFrame;
import simulator.command.*;
import simulator.element.Timer;

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
public abstract class ProtocolAgent {
    public Node node;
    public int protocolId;
    public int currentProtocolState;
    public Timer[] timers;

    public ProtocolAgent(int protocolId_, Node node_) {
      this.protocolId = protocolId_;
      this.node = node_;
      Output.SIMINFO("Node " + node.getNodeId() + ": Starting protocol agent " + Preferences.protocol_name[this.protocolId] + " with protocol id "+this.protocolId+" ...", Preferences.PRINT_PROTOCOL_AGENT_ACTIVITY);
    }

   /**
    * The initialize function. Has to be overridden by subclasses.
    * Sets the protocol to the initial states.
    */
    public abstract void initialize();

    /**
     * Create Timers. Call this in the respective constructor of the derived classes.
     * @param numTimers int
     */
    public void createTimers(int numTimers) {
      timers = new Timer[numTimers];
      String timerNames[] = Timers.getProtocolTimersNames(this.protocolId);
      if(timerNames[0].compareTo(Preferences.NULL_STRING) != 0) {
        for(int timerIndex = 0; timerIndex < numTimers; timerIndex++) {
          timers[timerIndex] = new Timer(timerIndex, timerNames[timerIndex]);
        }
      }
      else
        Output.ERR(this.protocolId+", Node " + node.getNodeId() + ": Null timer names for " + Preferences.protocol_name[this.protocolId], true);
    }

    /**
     * The startTimer() function. Needs to be overridden by subclasses
     * @param timerId int
     */
    public abstract void startTimer(int timerId);

    /**
      * The timerExpired() function. Needs to be overridden by subclasses
      * @param timerId int
      */
    public abstract void timerExpired(int timerId);

    /**
     * Send functions
     */
    public void send(Command sendmsgCommand) {
      Simulator.getInstance().schedule(sendmsgCommand);
    }

   /**
    * Recv functions
    */
    public abstract void recv(Message msg);

   /**
    * The run function.
    */
    public void run() {
      int nodeStatus;
      double time;
      if (node.continueSimulation()) {
          nodeStatus = node.getStatus();
          time = Simulator.getInstance().getTime();
          switch (nodeStatus) {
            case Status.UP:
              uptimeProtocol(time);
              break;

            case Status.DOWN:
              downtimeProtocol(time);
              break;
          }
       }
    } // End of ProtocolAgent.run()

    /**
     * The uptimeProtocol() function. Needs to be overridden by subclasses
     * @param time double
     */
    public abstract void uptimeProtocol(double time);

    /**
     * The downtimeProtocol() function. Needs to be overridden by subclasses
     * @param time double
     */
    public abstract void downtimeProtocol(double time);

    /**
     * Returns currentSimulationTime
     * @return double
     */
    public double currentSimulationTime() {
      return Simulator.getInstance().getTime();
    }

    /**
     * Returns protocol name
     * @return String
     */
    public String getProtocolName() {
      return Preferences.protocol_name[this.protocolId];
    }

  }
