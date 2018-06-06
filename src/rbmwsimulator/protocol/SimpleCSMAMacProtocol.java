package rbmwsimulator.protocol;

import rbmwsimulator.util.Preferences;
import rbmwsimulator.element.Node;
import rbmwsimulator.protocol.message.Message;

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

public class SimpleCSMAMacProtocol extends ProtocolAgent {
    public SimpleCSMAMacProtocol(Node node_) {
        super(Preferences.WIRELESS_CSMA_MAC_PROTOCOL, node_);
        //Note: Create Timers in the constructor of the derived class of ProtocolAgent
    }

    //Implementing abstract interfaces from super class ProtocolAgent
    public void initialize() {
        this.currentProtocolState = Status.RBMW_PROTOCOL_DISCOVER_NEIGHBORS;
    }

    public void startTimer(int timerId) {

    }

    public void timerExpired(int timerId) {

    }

    public void recv(Message msg) {

    }

    public void uptimeProtocol(double time) {

    }

    public void downtimeProtocol(double time) {

    }

}
