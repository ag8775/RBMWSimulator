package rbmwsimulator.util;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import rbmwsimulator.element.Node;
import rbmwsimulator.command.*;
import simulator.util.Output;
import simulator.Simulator;

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
public class SimulationEventsTrafficScenarioReader {
    private String trafficScenarioFileName;
    private Node nodes[];
    private long totalSimulationTime;

    public SimulationEventsTrafficScenarioReader(String trafficScenarioFileName_, Node nodes_[]) {
        this.trafficScenarioFileName = trafficScenarioFileName_;
        this.nodes = nodes_;
        this.totalSimulationTime = 0;
    }

    public void loadTrafficPatternOntoSimulator() {
      String record;
      try {
        FileReader fr = new FileReader(this.trafficScenarioFileName);
        BufferedReader br = new BufferedReader(fr);
        while ((record = br.readLine()) != null) {
          Output.SIMINFO(this.toString()+": "+record, Preferences.PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS);
          parseLine(record);
        }
        fr.close();
        br.close();
      } catch (IOException e) {
        // catch possible io errors from readLine()
        Output.SIMINFO(this.toString()+":Uh oh, got an IOException error!", Preferences.PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS);
        e.printStackTrace();
     }
   }

   private void parseLine(String line) {
     String delimiters = new String(": , ; { } ( ) \"Mhz\" \"Ghz\" \"Mbps\" \"GB\" \"MB\" \"s\" ");
     StringTokenizer st = new StringTokenizer(line, delimiters);
     String str = new String();
     String token;
     String nodeIdTokens[] = new String[2];
     boolean multipleNodeEvent = false;
     double time = -1;
     int nodeId = -1;
     int eventType = -1;
     int numTokens = 0;
     while((st.hasMoreTokens())&&(numTokens < 3)) {
       time = Double.valueOf(st.nextToken()).doubleValue();
       token = st.nextToken();
       if(containsRangeOfNodeId(token)) {
          nodeIdTokens = token.split("-");
          multipleNodeEvent = true;
       } else
           nodeId = Integer.valueOf(token).intValue();
       String commandDelimiters = new String("- -");
       eventType = getEventId(st.nextToken(commandDelimiters));
       numTokens+=3;
      }

     int fromNodeId = 0;
     int toNodeId = 0 ;

     if(multipleNodeEvent) {
       fromNodeId =  Integer.valueOf(nodeIdTokens[0]).intValue();
       toNodeId = Integer.valueOf(nodeIdTokens[1]).intValue();
       Output.SIMINFO(this.toString()+":Node id range = "+fromNodeId+"-"+toNodeId, Preferences.PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS);
     }

      switch(eventType) {
        case TrafficEvents.UP:
          if(!multipleNodeEvent)
            parse_Node_UP_EVENT(time, nodeId);
          else {
             for(nodeId = fromNodeId; nodeId <= toNodeId; nodeId++) {
               parse_Node_UP_EVENT(time, nodeId);
             }
          }
          break;
        case TrafficEvents.DOWN:
          if(!multipleNodeEvent)
            parse_Node_DOWN_EVENT(time, nodeId);
          else {
            for(nodeId = fromNodeId; nodeId <= toNodeId; nodeId++) {
              parse_Node_DOWN_EVENT(time, nodeId);
            }
          }
          break;
        case TrafficEvents.INITIALIZE:
          parse_Initialize_EVENT(time);
          break;
        case TrafficEvents.INITIATE_FULL_QUERY:
          //parse_Full_Query_EVENT(time, nodeId, st);
          break;
        case TrafficEvents.INITIATE_PARTIAL_QUERY:
          //parse_Partial_Query_EVENT(time, nodeId, st);
          break;
        case TrafficEvents.INITIATE_MULTIPLE_QUERIES:
          //parse_Multiple_Query_Event(time, nodeId, st);
          break;
        case TrafficEvents.REDUCE_NODE_CAPACITY:
          //parse_Reduce_Capacity_Event(time, nodeId, st);
          break;
        case TrafficEvents.INCREASE_NODE_CAPACITY:
          //parse_Increase_Capacity_Event(time, nodeId, st);
          break;
        case TrafficEvents.REDUCE_NODE_BW:
          //parse_Reduce_BW_Event(time, nodeId, st);
          break;
        case TrafficEvents.INCREASE_NODE_BW:
          //parse_Increase_BW_Event(time, nodeId, st);
          break;
        case TrafficEvents.DROP_PACKETS:
          //parse_Drop_Packets_Event(time, nodeId, st);
          break;
        case TrafficEvents.STOP_SIMULATION:
          parse_Stop_Simulation_Event(time); // nodeId is irrelevant here :-)
          break;
        default:
          break;
      }

     multipleNodeEvent = false;
   }


  private boolean containsRangeOfNodeId(String token) {
    char[] tokenArray =  token.toCharArray();
    int index = 0;
    while(index < tokenArray.length) {
      if(tokenArray[index] == '-')
        return true;
      index++;
    }
    return false;
  }

   private int getEventId(String eventString) {
     int eventId = -1;

     if(eventString.compareTo("UP")==0)
       eventId = TrafficEvents.UP;

     if(eventString.compareTo("DOWN")==0)
       eventId = TrafficEvents.DOWN;

     if(eventString.compareTo("INITIALIZE")==0)
       eventId = TrafficEvents.INITIALIZE;

     if(eventString.compareTo("INITIATE_FULL_QUERY")==0)
       eventId = TrafficEvents.INITIATE_FULL_QUERY;

     if(eventString.compareTo("INITIATE_PARTIAL_QUERY")==0)
       eventId = TrafficEvents.INITIATE_PARTIAL_QUERY;

     if(eventString.compareTo("INITIATE_MULTIPLE_QUERIES")==0)
       eventId = TrafficEvents.INITIATE_MULTIPLE_QUERIES;

     if(eventString.compareTo("REDUCE_NODE_CAPACITY")==0)
       eventId = TrafficEvents.REDUCE_NODE_CAPACITY;

     if(eventString.compareTo("INCREASE_NODE_CAPACITY")==0)
       eventId = TrafficEvents.INCREASE_NODE_CAPACITY;

     if(eventString.compareTo("REDUCE_NODE_BW")==0)
       eventId = TrafficEvents.REDUCE_NODE_BW;

     if(eventString.compareTo("INCREASE_NODE_BW")==0)
       eventId = TrafficEvents.INCREASE_NODE_BW;

     if(eventString.compareTo("DROP_PACKETS")==0)
       eventId = TrafficEvents.DROP_PACKETS;

     if(eventString.compareTo("STOP_SIMULATION")==0)
       eventId = TrafficEvents.STOP_SIMULATION;

     return eventId;
   }

  private void parse_Initialize_EVENT(double time) {
    Simulator.getInstance().schedule(new InitializeCommand(time, nodes));
  }

  private void parse_Node_UP_EVENT(double time, int nodeId) {
    Simulator.getInstance().schedule(new NodeStateCommand(time, nodes[nodeId], TrafficEvents.UP));
  }

  private void parse_Node_UP_EVENT(double time, int fromNodeId, int toNodeId) {
    int nodeId = 0;
    for(nodeId = fromNodeId; nodeId <= toNodeId; nodeId++)
      Simulator.getInstance().schedule(new NodeStateCommand(time, nodes[nodeId], TrafficEvents.UP));
  }


  private void parse_Node_DOWN_EVENT(double time, int nodeId) {
    Simulator.getInstance().schedule(new NodeStateCommand(time, nodes[nodeId], TrafficEvents.DOWN));
  }

  private void parse_Node_DOWN_EVENT(double time, int fromNodeId, int toNodeId) {
    int nodeId = 0;
    for(nodeId = fromNodeId; nodeId <= toNodeId; nodeId++)
      Simulator.getInstance().schedule(new NodeStateCommand(time, nodes[nodeId], TrafficEvents.DOWN));
  }


  public long getTotalSimulationTime() {
    return this.totalSimulationTime;
  }

  private void parse_Stop_Simulation_Event(double time) {
    this.totalSimulationTime = Math.round(time);
    Simulator.getInstance().schedule(new StopCommand(time, nodes));
  }




}
