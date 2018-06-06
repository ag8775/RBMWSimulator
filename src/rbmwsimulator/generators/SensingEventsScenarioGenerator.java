package rbmwsimulator.generators;
import rbmwsimulator.model.SensingModel;
import rbmwsimulator.model.MobilityModel;
import java.util.Vector;
import simulator.util.FormatWriter;
import simulator.util.Output;
import java.io.*;
import rbmwsimulator.element.Node;
import rbmwsimulator.command.SensingEventReadingUpdateCommand;
import rbmwsimulator.element.Coordinates;
import simulator.Simulator;
import rbmwsimulator.util.Preferences;

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

/** Randomly generate movement scenarios for sensing events with different attributes:
 *   (1) Sensing Models: Point Sensing Model, Wave-based Distributed Model, ...
 *   (2) Sensor Types: See rbmwsimulator.mote.SensorTypes, {"Temperature", "Photo", "Pressure", "Humidity", "Sound", "Vibration"};
 *   (3) Mobility Model: Random Way Point Model, Brownian Motion Model, Infrastructure-based Mobility Model ...
 *
 */
public class SensingEventsScenarioGenerator {
  private String scenarioFile;
  private String appendPartialFileName;
  private double totalSimulationTime;
  private Vector sensingEvents; //A vector that holds the list of sensing events ...
  private int numEvents;
  private FormatWriter fileout;
  private Node nodes[];
  private int numNodes;

  public SensingEventsScenarioGenerator(int numEvents_, double totalSimulationTime_, String partialFileName_, Vector currentSensingEvents_, int numNodes_, Node[] nodes_) {
    this.numEvents = numEvents_;
    this.totalSimulationTime = totalSimulationTime_;
    this.appendPartialFileName = partialFileName_;
    this.sensingEvents = new Vector(numEvents_);
    this.numEvents = numEvents_;
    this.sensingEvents.addAll(currentSensingEvents_);
    this.nodes = nodes_;
    this.numNodes = numNodes_;
  }

  public void generateScenario() {
    SensingEvent sensingEvent;
    MobilityModel mobilityModel;
    MobilityStatus eventMobilityStatus;

    int eventIndex;
    /** For every sensing event, do the following:
     *  (1) Initialize its mobility model ...
     *  (2) Open the random number file in the SensingEvent's mobility status objects ...
     *  (3) Generate movement for every time increment from 0 to totalSimulationTime ...
     *  (4) Close the random number file in the SensingEvent's mobility status objects ...
     *  (5) Close the eventScenarioFile ...
     */
    openInfoFile();
    for(eventIndex = 0; eventIndex < this.sensingEvents.size(); eventIndex++) {
        sensingEvent = (SensingEvent) (this.sensingEvents.elementAt(eventIndex));

        eventMobilityStatus = (MobilityStatus) (sensingEvent.getCurrentEventStatus());
        eventMobilityStatus.openRandomNumberFile();

        mobilityModel = (MobilityModel) (sensingEvent.getMobilityModel());
        mobilityModel.initialize(eventMobilityStatus);

        //Update the reading ranges of all the nodes that are exposed to this event ...
        updateSensorReadingsForEvent(sensingEvent, (double)0.0, true);

        Output.SIMINFO(this.toString()+": Initialization: "+eventMobilityStatus.toString(), Preferences.PRINT_EVENT_MODEL_DETAILS);
    }
    for(long currentTime = 0; currentTime < this.totalSimulationTime; currentTime++) {
      for(eventIndex = 0; eventIndex < this.sensingEvents.size(); eventIndex++) {
        boolean eventMoved = false;
        sensingEvent = (SensingEvent) (this.sensingEvents.elementAt(eventIndex));
        eventMobilityStatus = (MobilityStatus) (sensingEvent.getCurrentEventStatus());
        mobilityModel = (MobilityModel) (sensingEvent.getMobilityModel());

        eventMoved = mobilityModel.directMobility(eventMobilityStatus, currentTime);

        //Debugging information goes in trace file ...
        Output.SIMINFO(this.toString()+", "+currentTime+": "+eventMobilityStatus.toString(), Preferences.PRINT_EVENT_MODEL_DETAILS);

        //Event scenario goes in eventScenarioFile ...
        printInfo(currentTime+": "+eventIndex+" {"+eventMobilityStatus.getCurrentPosition().getX()+", "+eventMobilityStatus.getCurrentPosition().getY()+"};");

        //Update the reading ranges of all the nodes that are exposed to this event ...
        updateSensorReadingsForEvent(sensingEvent, (double)currentTime, eventMoved);
      }//end for (sensingEvents) ...
    }//end for (simulationTime)...

    //Close the randomNumberFile for all the events ...
    for(eventIndex = 0; eventIndex < this.sensingEvents.size(); eventIndex++) {
      sensingEvent = (SensingEvent) (this.sensingEvents.elementAt(eventIndex));
      eventMobilityStatus = (MobilityStatus) (sensingEvent.getCurrentEventStatus());
      eventMobilityStatus.closeRandomNumberFile();
    }
    closeInfoFile();
  }

  /** Enque the movement/change in position of the event and then fire change
   *  in readings using the Sensing Model
   */
  private void updateSensorReadingsForEvent(SensingEvent sensingEvent, double time, boolean hasEventMoved) {
    SensingModel sensingModel;
    MobilityStatus eventMobilityStatus = (MobilityStatus) (sensingEvent.getCurrentEventStatus());
    int modelType = sensingEvent.getSensingModel().getModelType();
    Vector exposedNodes = null;
    int nodeId;
    double highReadingRange = 0.0;
    double lowReadingRange = 0.0;
    double eventSensorReading = 0.0;
    double randomNumber;

    //Get Sensing Model Object
    sensingModel = sensingEvent.getSensingModel();
    //Update event position and hence update the reading ranges of the nodes exposed to that event
    sensingModel.updateEventPosition(eventMobilityStatus.getCurrentPosition());
    //Get the vector of exposed nodes
    exposedNodes = sensingModel.getExposedNodesVector();

    //Update readings for these nodes *only* for moving events ...
    if(hasEventMoved) {
      for(int exposedNodesIdx = 0; exposedNodesIdx < exposedNodes.size(); exposedNodesIdx++) {
        nodeId = ((Integer)(exposedNodes.elementAt(exposedNodesIdx))).intValue();
        //Randomize the reading between the high and low sensing reading ranges ...
        highReadingRange = ((double[])(sensingModel.getSensingReadingRangeFor(nodeId)))[0];
        lowReadingRange = ((double[])(sensingModel.getSensingReadingRangeFor(nodeId)))[1];
        //Output.SIMINFO("Event "+sensingEvent.getEventId()+" time = "+time+" low = "+lowReadingRange+" high = "+highReadingRange);
        randomNumber = eventMobilityStatus.getRandomNumber();
        eventSensorReading = (double) ((double)lowReadingRange  +  (double)(((double)highReadingRange - (double)lowReadingRange)*randomNumber));
        loadSensingReadingUpdateEventOntoSimulator(sensingEvent, eventMobilityStatus.getCurrentPosition().getX(), eventMobilityStatus.getCurrentPosition().getY(), nodeId, eventSensorReading, time);
      }
    }
 }

  private void loadSensingReadingUpdateEventOntoSimulator(SensingEvent event, long eventPosX, long eventPosY, int nodeId, double eventReading, double time) {
     SensingEventReadingUpdateCommand eventReadingUpdateCommand = new SensingEventReadingUpdateCommand(time, this.nodes[nodeId], event, eventPosX, eventPosY, eventReading);
     Simulator.getInstance().schedule(eventReadingUpdateCommand);
  }

  private void openInfoFile() {
    try {
      this.scenarioFile = new String("events_"+this.numEvents+"_total_time_"+(new Double(this.totalSimulationTime)).toString()+"_"+appendPartialFileName+".scen");
      this.fileout = new FormatWriter(new BufferedWriter(new FileWriter(scenarioFile)), 5);
    }
    catch (IOException ae) {
      Output.ERR(this.toString()+": IO exception thrown: ", true, ae);
    }
  }

  public String getEventScenarioFile() {
    return this.scenarioFile;
  }

  private void closeInfoFile() {
    try {
      fileout.close();
    }
    catch (Exception io) {
      Output.ERR(this.toString()+": Error during closing event scenario file", true);
    }
  }

  private void printInfo(String str) {
    try {
      fileout.println(str);
    }
    catch(Exception ae) {
      Output.ERR(this.toString()+": IO exception Thrown: " + ae, true);
    }
  }
}
