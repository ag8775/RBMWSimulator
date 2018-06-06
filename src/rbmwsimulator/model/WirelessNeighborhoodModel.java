package rbmwsimulator.model;
import rbmwsimulator.element.*;
import rbmwsimulator.protocol.message.*;
import rbmwsimulator.protocol.Status;
import rbmwsimulator.util.NeighborManager;
import rbmwsimulator.util.Preferences;
import rbmwsimulator.model.Mica2EnergyParameters;
import rbmwsimulator.command.*;
import simulator.Simulator;
import simulator.util.Output;
import java.util.*;
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
public class WirelessNeighborhoodModel {
  private Node nodes[];
  private Vector activeNeighbors[];
  private int numNodes;
  private NeighborManager neighborManager;

  public WirelessNeighborhoodModel(NeighborManager neighborManager_, Node[] nodes_, int numNodes_) {
    this.nodes = nodes_;
    this.numNodes = numNodes_;
    this.neighborManager = neighborManager_;
    this.activeNeighbors = new Vector[this.numNodes];
    for(int nodeId = 0; nodeId < this.numNodes; nodeId++) {
      this.nodes[nodeId].setWirelessNeighborhoodModel(this);
      this.activeNeighbors[nodeId] = new Vector();
      this.neighborManager.getActiveNeighborsFor(nodeId, activeNeighbors[nodeId]);
    }
  }

  public void updateNodeAsUp(int nodeId) {
    //Remove old neighbors ...
    this.activeNeighbors[nodeId].removeAllElements();
    //If I am UP, it means that I am going to Discover my *UP* neighbors ...
    this.neighborManager.getActiveNeighborsFor(nodeId, activeNeighbors[nodeId]);
    nodeStateUpdate(nodeId, Status.UP);
  }

  public void updateNodeAsDown(int nodeId) {
    nodeStateUpdate(nodeId, Status.DOWN);
    //since the node is down, I should as well remove all its neighbors ...
    this.activeNeighbors[nodeId].removeAllElements();
  }

  private void nodeStateUpdate(int nodeId, int nodeState) {
    //check to see if this node is a neighbor of its neighbors
    int neighborId;
    Vector neighbors = this.activeNeighbors[nodeId];
    for(int index = 0; index < neighbors.size(); index++) {
      neighborId = ((Integer)(neighbors.elementAt(index))).intValue();
      Vector neighborsNeighbors = this.activeNeighbors[neighborId];
      if(neighborsNeighbors.contains(new Integer(nodeId))) {
        if(nodeState == Status.DOWN) {
          this.activeNeighbors[neighborId].remove(new Integer(nodeId));
        }
      } else {
        if(nodeState == Status.UP) {
          this.activeNeighbors[neighborId].add(new Integer(nodeId));
        }
      }
    }
    Output.SIMINFO(this.toString()+", "+Simulator.getInstance().getTime()+": active neighbors for "+nodeId+" are "+this.neighborManager.toString(this.activeNeighbors[nodeId]), Preferences.PRINT_WIRELESS_NEIGHBORHOOD_MODEL_DETAILS);
  }


  public boolean isMediumBusyAroundNode(int nodeId) {
    //if me or any of my neighbor(s) is transmitting or receiving then it implies that the medium is BUSY ...
    //Returns TRUE if medium is BUSY, FALSE otherwise ...
    if(this.nodes[nodeId].getTransceiver().isTransmitting() || this.nodes[nodeId].getTransceiver().isReceiving())
      return true;
    else {
      Vector neighbors = this.activeNeighbors[nodeId];
      int neighborId;
      for(int index = 0; index < neighbors.size(); index++) {
        neighborId = ((Integer)(neighbors.elementAt(index))).intValue();
        //if neighbor sending or receiving ...
        if(this.nodes[neighborId].getTransceiver().isTransmitting() || this.nodes[neighborId].getTransceiver().isReceiving())
          return true;
      }//end for
    }//end else
    return false;
  }

  private void setMediumBusyAsSenderForNode(int nodeId) {
    this.nodes[nodeId].getTransceiver().switchToTxMode();
  }

  private void setMediumBusyAsReceiverForNode(int nodeId) {
   this.nodes[nodeId].getTransceiver().switchToRxMode();
 }

  private void setMediumFreeForNode(int nodeId) {
    this.nodes[nodeId].getTransceiver().switchToIdleMode();
  }

  public Vector getActiveNeighborsFor(int nodeId) {
    return this.activeNeighbors[nodeId];
  }

  public void signalUnicastSendMessageFor(int senderNodeId, int receiverNodeId, Message msg) {
    //XXX: receiverNodeId may not be a 1-hop neighbor of senderNodeId ...
    if(this.activeNeighbors[senderNodeId].contains(new Integer(receiverNodeId))) {
        setMediumBusyAsSenderForNode(senderNodeId);
        setMediumBusyAsReceiverForNode(receiverNodeId);
        double radioSwitchingDelay = this.nodes[senderNodeId].getTransceiver().
                                     getRadioSwitchingDelay() +
                                     this.nodes[receiverNodeId].getTransceiver().
                                     getRadioSwitchingDelay();

        int numMTUs = msg.getMessageSize();
        double receiveTime = Simulator.getInstance().getTime()+ numMTUs * (radioSwitchingDelay+Preferences.DEFAULT_MAX_WIRELESS_SIGNAL_PROPAGATION_DELAY);
        ReceiveMessageCommand receiveCommand = new ReceiveMessageCommand(receiveTime, senderNodeId, receiverNodeId, this, msg);
        Simulator.getInstance().schedule(receiveCommand);
    } else {
        Output.SIMINFO(this.toString()+", "+Simulator.getInstance().getTime()+": in signalUnicastSendMessageFor() "+receiverNodeId+" is not a neighbor of "+senderNodeId+". Converting unicast to broadcast", Preferences.PRINT_WIRELESS_NEIGHBORHOOD_MODEL_DETAILS);
        signalBroadcastSendMessageFor(senderNodeId, msg);
    }
  }

  public void signalBroadcastSendMessageFor(int senderNodeId, Message msg) {
    Vector receivers = getActiveNeighborsFor(senderNodeId);
    int receiverIndex = 0;
    int receiverId = 0;
    double switchingDelay = this.nodes[senderNodeId].getTransceiver().getRadioSwitchingDelay();
    setMediumBusyAsSenderForNode(senderNodeId);
    for(receiverIndex = 0; receiverIndex < receivers.size(); receiverIndex++) {
      receiverId = ((Integer)(receivers.elementAt(receiverIndex))).intValue();
      //XXX: Do we need to add the switchingDelay of all the receivers ...
      //Assume that currently it takes care of the delay for bit-level synch between radio receivers & senders
      switchingDelay+= this.nodes[receiverId].getTransceiver().getRadioSwitchingDelay();
      setMediumBusyAsReceiverForNode(receiverId);
    }
    //Include delay for receiving messages that spread over multiple MTUs (or packets)
    int numMTUs = msg.getMessageSize();
    double receiveTime = Simulator.getInstance().getTime()+ numMTUs * (switchingDelay+Preferences.DEFAULT_MAX_WIRELESS_SIGNAL_PROPAGATION_DELAY);
    for(receiverIndex = 0; receiverIndex < receivers.size(); receiverIndex++) {
      receiverId = ((Integer)(receivers.elementAt(receiverIndex))).intValue();
      ReceiveMessageCommand receiveCommand = new ReceiveMessageCommand(receiveTime, senderNodeId, receiverId, this,  msg);
      Simulator.getInstance().schedule(receiveCommand);
    }
  }

  public void signalMessageReceptionFor(int senderNodeId, int receiverNodeId, Message msg) {
     this.nodes[receiverNodeId].receiveMessage(msg);
     setMediumFreeForNode(senderNodeId);
     setMediumFreeForNode(receiverNodeId);
  }
}
