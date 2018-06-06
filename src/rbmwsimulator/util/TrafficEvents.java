package rbmwsimulator.util;

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
public class TrafficEvents {
    /**
     * DOWN a specified gnutella peer
     * time: nodeId -DOWN-;
     */
    public final static int DOWN = 0;

    /**
     * UP a specified gnutella peer
     * time: nodeId -UP-;
     */
    public final static int UP = 1;

    /**
     * Initialize ALL nodes ...
     * time: numNodes -INITIALIZE-;
     *
     * numNodes is redundant here :-)
     */
    public final static int INITIALIZE = 2;

    /**
     * Initiate Full Query with TTL-limited flooding
     * time: nodeId -INITIATE_FULL_QUERY- nWords {word1, word2, ..., wordn} fileType (TTL);
     */
    public final static int INITIATE_FULL_QUERY = 3;

    /**
     *  Initiate Partial Query with n words and TTL-limited flooding
     * time: nodeId -INITIATE_PARTIAL_QUERY- nWords {word1, word2, ..., wordn} fileType (TTL);
     */
    public final static int INITIATE_PARTIAL_QUERY = 4;

    /**
     * Intiate Multiple Queries with a specified Query Rate ...
     * time: nodeId -INITIATE_MULTIPLE_QUERIES- nParamaters {timeInterval, queryRate, distributionType, distributionParameters, ... } (TTL);
     */
    public final static int INITIATE_MULTIPLE_QUERIES = 5;

    /**
     * Reduce Node's Computational Capabilities
     * time: nodeId -REDUCE_NODE_CAPACITY- nParams {cpu Ghz, memory MB, hdisk GB};
     */
    public final static int REDUCE_NODE_CAPACITY = 6;

    /**
     * Increase Node's Computational Capabilities
     * time: nodeId -INCREASE_NODE_CAPACITY- nParams {cpu Ghz, memory MB, hdisk GB};
     */
    public final static int INCREASE_NODE_CAPACITY = 7;

    /**
     * Reduce Node's upstream/downstream bandwidth
     * time: nodeId -REDUCE_NODE_BW- (upstream_bw Mbps, downstream_bw Mbps) nParams {distributionType, distributionParameters, ...};
     */
    public final static int REDUCE_NODE_BW = 8;

    /**
     * Increase Node's upstream/downstream bandwidth
     * time: nodeId -INCREASE_NODE_BW- (upstream_bw Mbps, downstream_bw Mbps) nParams {distributionType, distributionParameters, ...};
     */
    public final static int INCREASE_NODE_BW = 9;

    /**
     * Drop some/all packets at some node for some time interval ...
     * time: nodeId -DROP_PACKETS- timeInterval;
     *          OR
     * time: nodeId -DROP_PACKETS- timeInterval nParams {distributionType, distributionParameters, ...};
     *
     */
    public final static int DROP_PACKETS = 10;
    //          :
    //          :
    //          :

    /**
     * time: nodeNum -STOP_SIMULATION-;
     *
     * nodeNum is redundant here :-)
     */
    public final static int STOP_SIMULATION = 1111;

}
