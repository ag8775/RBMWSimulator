package rbmwsimulator.protocol.rasim.grdsrt;

import rbmwsimulator.util.TopologyReader;
import rbmwsimulator.util.NeighborManager;
import rbmwsimulator.protocol.metric.CumulativeSensingDegreeMetric;
import rbmwsimulator.util.Preferences;
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
 * @version 2.0
 */
public class SensingInfoDatabase {

    int numNodes;
    int sensingRange;
    int sensingCellDimension;
    private TopologyReader topology;
    private NeighborManager nbrManager;
    private CumulativeSensingDegreeMetric nodeCumulativeSensingDegree[];

    public SensingInfoDatabase(int numNodes, int sensingRange,
                               int sensingCellDimension, NeighborManager nbrManager_, TopologyReader topo) {
        this.numNodes = numNodes;
        this.sensingRange = sensingRange;
        this.sensingCellDimension = sensingCellDimension;
        this.nbrManager = nbrManager_;
        this.topology = topo;
        this.nodeCumulativeSensingDegree = new CumulativeSensingDegreeMetric[this.
                                           numNodes];
        //Output.SIMINFO(" Initializing Sensing Info Database ... ", Preferences.PRINT_RA_PROTOCOL_ACTIVITY);
        for (int nodeId = 1; nodeId < this.numNodes; nodeId++) {
            this.nodeCumulativeSensingDegree[nodeId] = new
                    CumulativeSensingDegreeMetric(nodeId, this.sensingRange,
                                                  this.sensingCellDimension,
                                                  nbrManager.getNeighborsFor(
                    nodeId), this.topology);
        }
    }

    public Vector getSensingRegionFor(int nodeId) {
        return this.nodeCumulativeSensingDegree[nodeId].getSensingRegion();
    }

    public void calculateContributingSensingCells() {
        for (int nodeId = 1; nodeId < this.numNodes; nodeId++) {
            this.nodeCumulativeSensingDegree[nodeId].
                    updateMatchingCellsIncludingAllNeighbors();
        }
    }

    public void calculateNodesMaxCSD() {
        for (int nodeId = 1; nodeId < this.numNodes; nodeId++) {
            this.nodeCumulativeSensingDegree[nodeId].calculateMaxCSD();
        }
    }

    public void calculateMaxPercentageCoverageForAllNodes() {
        for (int nodeId = 1; nodeId < this.numNodes; nodeId++) {
            Output.SIMINFO("Node " + nodeId + ": percentageCoverage = " +
                           this.nodeCumulativeSensingDegree[nodeId].
                           calculatePercentageCoverage(),
                           Preferences.PRINT_RA_PROTOCOL_ACTIVITY);
        }
    }

    public void calculatePercentageCoverageFor(int nodeId, Vector neighbors) {
        this.nodeCumulativeSensingDegree[nodeId].calculatePercentageCoverage(
                neighbors);
    }

    public double getPercentageCoverageFor(int nodeId) {
        return this.nodeCumulativeSensingDegree[nodeId].getPercentageCoverage();
    }

    public void printAll() {
        for (int nodeId = 1; nodeId < this.numNodes; nodeId++) {
            this.nodeCumulativeSensingDegree[nodeId].printSensingRegion();
            //this.nodeCumulativeSensingDegree[nodeId].printMaxSensingRegion();
            Output.SIMINFO("Node " + nodeId + ": maxCSD = " +
                           this.nodeCumulativeSensingDegree[nodeId].maxCSD(),
                           Preferences.PRINT_RA_PROTOCOL_ACTIVITY);
        }
    }

    public void printNodeWithMinMaxSensingCoverage() {
        int maxId = 1;
        int minId = 1;
        double maxPercentageCoverage = this.nodeCumulativeSensingDegree[1].
                                       getPercentageCoverage();
        double minPercentageCoverage = this.nodeCumulativeSensingDegree[1].
                                       getPercentageCoverage();
        for (int nodeId = 2; nodeId < this.numNodes; nodeId++) {
            if (this.nodeCumulativeSensingDegree[nodeId].getPercentageCoverage() >
                maxPercentageCoverage) {
                maxPercentageCoverage = this.nodeCumulativeSensingDegree[nodeId].
                                        getPercentageCoverage();
                maxId = nodeId;
            }
            if (this.nodeCumulativeSensingDegree[nodeId].getPercentageCoverage() <
                minPercentageCoverage) {
                minPercentageCoverage = this.nodeCumulativeSensingDegree[nodeId].
                                        getPercentageCoverage();
                minId = nodeId;
            }
        }
        Output.SIMINFO("Node *" + maxId +
                       "*: has MAXIMUM Percentage Coverage = " +
                       maxPercentageCoverage,
                       Preferences.PRINT_RA_PROTOCOL_ACTIVITY);
        Output.SIMINFO("Node -" + minId +
                       "-: has MINIMUM Percentage Coverage = " +
                       minPercentageCoverage,
                       Preferences.PRINT_RA_PROTOCOL_ACTIVITY);
    }

} // End of SensingInfoDatabase
