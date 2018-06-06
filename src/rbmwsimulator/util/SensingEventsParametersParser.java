package rbmwsimulator.util;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.*;
import rbmwsimulator.mote.SensorTypes;
import rbmwsimulator.model.MovementModelTypes;
import rbmwsimulator.model.SensingModelTypes;
import rbmwsimulator.generators.SensingEvent;
import rbmwsimulator.model.MobilityModel;
import rbmwsimulator.model.RandomWayPointMobilityModel;
import rbmwsimulator.element.Coordinates;
import rbmwsimulator.model.SensingModel;
import rbmwsimulator.model.PointSensingExposureModels;
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
public class SensingEventsParametersParser {
  private String paramsFile;
  private FileReader fr;
  private BufferedReader br;
  private int maxX, maxY;
  private int numNodes;
  private Coordinates [] nodePositions;
  private boolean prngScenarioRecord;
  private boolean eventScenarioParameters;
  private boolean individualEventRecord;
  private boolean coordinatesScenarioRecord;
  private boolean mobilityScenarioRecord;
  private boolean stationaryScenarioRecord;
  private boolean sensingScenarioRecord;
  private boolean movementModelRecord;
  private boolean initialEventDetailsParsed;
  private int countPRNGScenarioParameters;
  private int currentEventId;
  private String currentEventName;
  private int currentSensorType;
  private long currentEventCoordX;
  private long currentEventCoordY;
  private long seed;
  private int luxury;
  private String distribution;
  private String generator;
  private int currentEventMaxSpeed;
  private int currentEventMinSpeed;
  private float currentEventMinPauseTime;
  private float currentEventMaxPauseTime;
  private float currentEventMinMobilityTime;
  private float currentEventMaxMobilityTime;
  private int currentSensingEventModelType;
  private long currentSensingModelGridDimX, currentSensingModelGridDimY;
  private int currentSensingModelNumThresholds;
  private double [] currentEventSensingThresholds;
  private double [] currentEventDistanceThresholds;
  private int numEvents;
  private int maxThresholds;
  private int countInitialEventDetails;
  private int countEventScenarioParameters;
  private int countEventCoordinates;
  private int countSensingScenarioParameters;
  private int countMobilityScenarioParameters;
  private int countStationaryScenarioParameters;
  private int currentEventMovementModelType;
  Vector sensingEvents;

  public SensingEventsParametersParser(String paramsFile, int numNodes_, int maxX_, int maxY_, Coordinates [] nodePositions_) {
    this.paramsFile = paramsFile;
    this.numNodes = numNodes_;
    this.maxX = maxX_;
    this.maxY = maxY_;
    this.nodePositions = nodePositions_;
    this.prngScenarioRecord = false;
    this.eventScenarioParameters = false;
    this.individualEventRecord = false;
    this.coordinatesScenarioRecord = false;
    this.mobilityScenarioRecord = false;
    this.stationaryScenarioRecord = false;
    this.sensingScenarioRecord = false;
    this.movementModelRecord = false;
    this.initialEventDetailsParsed = false;
    this.countInitialEventDetails = 0;
    this.countEventScenarioParameters = 0;
    this.countEventCoordinates = 0;
    this.countSensingScenarioParameters = 0;
    this.countMobilityScenarioParameters = 0;
    this.countStationaryScenarioParameters = 0;
    this.currentEventId = 0;
    this.numEvents = 0;
    initializeEventDetails();
    this.countPRNGScenarioParameters = 0;
  }

  public SensingEventsParametersParser(String paramsFile) {
    this.paramsFile = paramsFile;
    this.prngScenarioRecord = false;
    this.eventScenarioParameters = false;
    this.individualEventRecord = false;
    this.coordinatesScenarioRecord = false;
    this.mobilityScenarioRecord = false;
    this.stationaryScenarioRecord = false;
    this.sensingScenarioRecord = false;
    this.movementModelRecord = false;
    this.initialEventDetailsParsed = false;
    this.countInitialEventDetails = 0;
    this.countEventScenarioParameters = 0;
    this.countEventCoordinates = 0;
    this.countSensingScenarioParameters = 0;
    this.countMobilityScenarioParameters = 0;
    this.countStationaryScenarioParameters = 0;
    this.currentEventId = 0;
    this.numEvents = 0;
    initializeEventDetails();
    this.countPRNGScenarioParameters = 0;
  }


  private void initializeEventDetails() {
  this.currentEventId = 0;
  this.currentEventName = "";
  this.currentSensorType = -1;
  this.currentEventCoordX = -1;
  this.currentEventCoordY = -1;
  this.seed = -1;
  this.luxury = -1;
  this.distribution = "";
  this.generator = "";
  this.currentEventMaxSpeed = 0;
  this.currentEventMinSpeed = 0;
  this.currentEventMinPauseTime = 0;
  this.currentEventMaxPauseTime = 0;
  this.currentEventMinMobilityTime = 0;
  this.currentEventMaxMobilityTime = 0;
  this.currentSensingEventModelType = 0;
  this.currentSensingModelGridDimX = 0;
  this.currentSensingModelGridDimY = 0;
  this.currentEventMovementModelType = 0;
  this.currentSensingModelNumThresholds = 0;
 }

  public void parseParamsFile() {
    //Output.SIMINFO(this.paramsFile);
    String record = null;
    try {
          this.fr = new FileReader(this.paramsFile);
          this.br = new BufferedReader(fr);
          record = new String();
          while (((record = br.readLine()) != null))
          {
            if (record.length() > 0) {
              if (record.charAt(0) != '#') {
                //Output.SIMINFO(record);
                if (record.charAt(0) == '!') {
                  if (record.regionMatches(1, "Scenario", 0, "Scenario".length()))
                    this.eventScenarioParameters = true;
                  if (record.regionMatches(1, "Event", 0, "Event".length()))
                    this.individualEventRecord = true;
                }
                else if (record.regionMatches(2, "Coordinates", 0, "Coordinates".length()))
                  this.coordinatesScenarioRecord = true;
                else if (record.regionMatches(2, "PRNG", 0, "PRNG".length()))
                  this.prngScenarioRecord = true;
                else if (record.regionMatches(2, "Mobility", 0, "Mobility".length()))
                  this.mobilityScenarioRecord = true;
                else if (record.regionMatches(2, "Stationary", 0, "Stationary".length()))
                  this.stationaryScenarioRecord = true;
                else if (record.regionMatches(2, "Sensing", 0, "Sensing".length()))
                  this.sensingScenarioRecord = true;
                else if (record.regionMatches(2, "Movement", 0, "Movement".length()))
                  this.movementModelRecord = true;
                if((record.charAt(0) != '!')&&(record.charAt(1) != '!')) {
                  //Output.SIMINFO("-------- "+record);
                  //Output.SIMINFO("E = "+this.eventScenarioParameters+" IER = "+this.individualEventRecord+" IEDP = "+this.initialEventDetailsParsed+" CSR = "+this.coordinatesScenarioRecord+" PSR = "+this.prngScenarioRecord+" MSR = "+this.mobilityScenarioRecord+" SSR = "+this.stationaryScenarioRecord+" SeSR = "+this.sensingScenarioRecord+" MMR = "+this.movementModelRecord);
                  if(this.eventScenarioParameters)
                    parseEventScenarioParameters(record);
                  else if(this.individualEventRecord) {
                     if(!this.initialEventDetailsParsed)
                       parseInitialEventDetails(record);
                     else if(this.coordinatesScenarioRecord)
                        parseEventCoordinates(record);
                      else if (this.prngScenarioRecord)
                        parsePseudoRandomGeneratorParameters(record);
                      else if (this.mobilityScenarioRecord)
                        parseMobilityScenarioParameters(record);
                      else if (this.stationaryScenarioRecord)
                        parseStationaryScenarioParameters(record);
                      else if(this.sensingScenarioRecord)
                        parseSensingScenarioRecord(record);
                      else if(this.movementModelRecord) {
                        parseMovementModelParameters(record);
                        this.initialEventDetailsParsed = false;
                        //This is the last record for any event, so update the event information ...
                        addToSensingEventVector();
                        initializeEventDetails();
                      }
                  } //end of else if(this.individualEventRecord)
                }//end of if((record.charAt(0) != '!')&&(record.charAt(1) != '!'))
              } //end of if (record.charAt(0) != '#')
            }//end of if (record.length() > 0)
          }//end of while ...
          fr.close();
          br.close();
        }
        catch (IOException e)
        {
          // catch possible io errors from readLine()
          Output.ERR(this.toString()+": Uh oh, got an IOException error!", Preferences.PRINT_EVENT_MODEL_DETAILS);
          e.printStackTrace();
        }
  }

  public Vector getSensingEventVector() {
    return this.sensingEvents;
  }

  public int getEventCount() {
    return this.numEvents;
  }

  private void addToSensingEventVector() {
    SensingModel eventSensingModel = null;
    MobilityModel eventMobilityModel = null;
    SensingEvent sensingEvent = null;

    double eventSensingRange = this.currentEventDistanceThresholds[this.currentSensingModelNumThresholds-1];
    Coordinates  eventCoordinates = new Coordinates(this.currentEventCoordX, this.currentEventCoordY);
    if(this.currentSensingEventModelType == SensingModelTypes.POINT_SENSING_EXPOSURE_MODEL)
      eventSensingModel = new PointSensingExposureModels(SensingModelTypes.POINT_SENSING_EXPOSURE_MODEL, this.numNodes, this.maxX, this.maxY, this.currentSensingModelGridDimX, this.currentSensingModelGridDimY, this.nodePositions, eventSensingRange, this.currentEventSensingThresholds, this.currentEventDistanceThresholds, eventCoordinates);
    //else if (this.currentSensingEventModelType == SensingModelTypes.WAVE_SENSING_EXPOSURE_MODEL)
    if(this.currentEventMovementModelType == MovementModelTypes.RANDOM_WAYPOINT_MOBILITY_MODEL)
      eventMobilityModel = new RandomWayPointMobilityModel();
    if((!eventSensingModel.equals(null))&&(!eventMobilityModel.equals(null))) {
      sensingEvent = new SensingEvent(this.currentEventName, this.currentEventId, this.currentSensorType, eventCoordinates, this.seed, this.luxury, this.maxX, this.maxY, this.currentEventMaxSpeed, this.currentEventMinSpeed, this.currentEventMinPauseTime, this.currentEventMaxPauseTime, this.currentEventMinMobilityTime, this.currentEventMaxMobilityTime, eventSensingModel, eventMobilityModel);
      this.sensingEvents.add(sensingEvent);
    }
  }

  private void parseEventScenarioParameters(String record) {
    //Output.SIMINFO("I am here in parseEventScenarioParameters()");
    int numParameters = 2;
    if(record != null) {
      String delimiters = new String("> =");
      StringTokenizer st = new StringTokenizer(record, delimiters);
      if(st.countTokens() == 2) {
        String token = st.nextToken();
        if (token.compareTo("numEvents") == 0) {
          this.numEvents = Integer.valueOf(st.nextToken()).intValue();
          this.sensingEvents = new Vector(this.numEvents);
        }
        if (token.compareTo("maxThresholds") == 0)
          this.maxThresholds = Integer.valueOf(st.nextToken()).intValue();
        this.countEventScenarioParameters++;
      }
    }
    if(this.countEventScenarioParameters >= numParameters) {
      //Output.SIMINFO("Number of events = " + this.numEvents);
      //Output.SIMINFO("maxThresholds = " + this.maxThresholds);
      this.eventScenarioParameters = false;
      this.countEventScenarioParameters = 0;
    }
  }

  private void parseInitialEventDetails(String record) {
    //Output.SIMINFO("I am here in parseInitialEventDetails()");
    int numParameters = 3;
    if(this.countInitialEventDetails < numParameters) {
        if (record != null) {
            String delimiters = new String("> =");
            StringTokenizer st = new StringTokenizer(record, delimiters);
            if (st.countTokens() == 2) {
                String token = st.nextToken();
                if (token.compareTo("id") == 0)
                    this.currentEventId = Integer.valueOf(st.nextToken()).
                                          intValue();
                if (token.compareTo("name") == 0)
                    this.currentEventName = st.nextToken();
                if (token.compareTo("sensor") == 0)
                   this.currentSensorType = SensorTypes.getSensorType(st.nextToken());
                this.countInitialEventDetails++;
            }
        }
    }
    if(this.countInitialEventDetails >= numParameters) {
      printInitialEventDetails();
      this.initialEventDetailsParsed = true;
      this.countInitialEventDetails = 0;
    }
  }

  private void parsePseudoRandomGeneratorParameters(String record) {
  //Output.SIMINFO("I am here in parsePseudoRandomGeneratorParameters()");
  int numParameters = 4;
   if ((record != null)&&(this.prngScenarioRecord)) {
     //This represents the set of PRNG parameters
     String delimiters = new String("> =");
     StringTokenizer st = new StringTokenizer(record, delimiters);
     //Output.SIMINFO("## "+record);
     if(st.countTokens() == 2) {
       String token = st.nextToken();
       //Output.SIMINFO("### "+token);
       if (token.compareTo("seed") == 0)
         this.seed = Long.valueOf(st.nextToken()).longValue();
       if (token.compareTo("luxury") == 0)
         this.luxury = Integer.valueOf(st.nextToken()).intValue();
       if (token.compareTo("distribution") == 0)
         this.distribution = st.nextToken();
       if (token.compareTo("generator") == 0)
         this.generator = st.nextToken();
       this.countPRNGScenarioParameters++;
      //Output.SIMINFO("### "+this.countPRNGScenarioParameters);
     }
  }
  if(this.countPRNGScenarioParameters >= numParameters) {
    this.prngScenarioRecord = false;
    this.countPRNGScenarioParameters = 0;
    printPseudoRandomNumberGeneratorParameters();
  }
}

  private void parseEventCoordinates(String record) {
    //Output.SIMINFO("I am here in parseEventCoordinates()");
    int numParameters = 2;
    if(record != null) {
      String delimiters = new String("> =");
      StringTokenizer st = new StringTokenizer(record, delimiters);
      if(st.countTokens() == 2) {
        String token = st.nextToken();
        if (token.compareTo("x") == 0)
          this.currentEventCoordX = Long.valueOf(st.nextToken()).longValue();
        if (token.compareTo("y") == 0)
          this.currentEventCoordY = Long.valueOf(st.nextToken()).longValue();
        this.countEventCoordinates++;
      }
    }
    if(this.countEventCoordinates >= numParameters) {
      //Output.SIMINFO("Event Position X = " + this.currentEventCoordX);
      //Output.SIMINFO("Event Position Y  = " + this.currentEventCoordY);
      this.coordinatesScenarioRecord = false;
      this.countEventCoordinates = 0;
    }
  }

  private void parseSensingScenarioRecord(String record) {
    //Output.SIMINFO("I am here in parseSensingScenarioRecord()");
    int numParameters = 4;
    String token;
    if(record != null) {
      String delimiters = new String("> = , { } :");
      StringTokenizer st = new StringTokenizer(record, delimiters);
      if(st.countTokens() > 2) {
        //These tokens correspond to the sensing and distance thresholds ...
        token = st.nextToken();
        if(token.compareTo("SensingDistanceThresholds") == 0) {
          int index = 0;
          this.currentSensingModelNumThresholds = Integer.valueOf(st.nextToken()).intValue();
          this.currentEventDistanceThresholds = new double[this.currentSensingModelNumThresholds];
          this.currentEventSensingThresholds = new double[this.currentSensingModelNumThresholds];
          for(index = 0; index < this.currentSensingModelNumThresholds; index++) {
            this.currentEventSensingThresholds[index] = Double.valueOf(st.nextToken()).doubleValue();
            this.currentEventDistanceThresholds[index] = Double.valueOf(st.nextToken()).doubleValue();;
          }
        }
      } else {
         //These tokens correspond to the sensing model type, and sensing model's grid dimensions {x, y}
         token = st.nextToken();
         if(token.compareTo("model") == 0)
           this.currentSensingEventModelType = SensingModelTypes.getSensingModelType(st.nextToken());
         if(token.compareTo("gridX") == 0)
           this.currentSensingModelGridDimX = Long.valueOf(st.nextToken()).longValue();
         if(token.compareTo("gridY") == 0)
           this.currentSensingModelGridDimY = Long.valueOf(st.nextToken()).longValue();
      }
      this.countSensingScenarioParameters++;
    }
    if(this.countSensingScenarioParameters >= numParameters) {
      printSensingScenarioParameters();
      this.sensingScenarioRecord = false;
      this.countSensingScenarioParameters = 0;
    }
  }

  private void parseMovementModelParameters(String record) {
    //Output.SIMINFO("I am here in parseMovementModelParameters()");
    if(record != null) {
      String delimiters = new String("> =");
      StringTokenizer st = new StringTokenizer(record, delimiters);
      if (st.countTokens() == 2) {
        String token = st.nextToken();
        if(token.compareTo("model") == 0) {
            this.currentEventMovementModelType = MovementModelTypes.getMovementModelType(st.nextToken());
            //Output.SIMINFO("Movement Model = "+MovementModelTypes.models[this.currentEventMovementModelType]);
        }
      }
      this.movementModelRecord = false;
    }
  }

  private void parseMobilityScenarioParameters(String record) {
    //Output.SIMINFO("I am here in parseMobilityScenarioParameters()");
    int numParameters = 4;
    if (record != null) {
      //This represents the set of topological parameters
      String delimiters = new String("> =");
      StringTokenizer st = new StringTokenizer(record, delimiters);
      if (st.countTokens() == 2) {
          String token = st.nextToken();
          //Output.SIMINFO("^^^^^"+token);
          if (token.compareTo("minSpeed") == 0)
            this.currentEventMinSpeed = Integer.valueOf(st.nextToken()).intValue();
          if (token.compareTo("maxSpeed") == 0)
            this.currentEventMaxSpeed = Integer.valueOf(st.nextToken()).intValue();
          if (token.compareTo("minMobilityTime") == 0)
            this.currentEventMinMobilityTime = Float.valueOf(st.nextToken()).floatValue();
          if (token.compareTo("maxMobilityTime") == 0)
            this.currentEventMaxMobilityTime = Float.valueOf(st.nextToken()).floatValue();
          this.countMobilityScenarioParameters++;
          //Output.SIMINFO("^^^^^"+this.countMobilityScenarioParameters);
       }
       if(this.countMobilityScenarioParameters >= numParameters) {
         this.mobilityScenarioRecord = false;
         printMobilityScenarioParameters();
         this.countMobilityScenarioParameters = 0;
       }
    }
  }

  private void parseStationaryScenarioParameters(String record) {
    //Output.SIMINFO("I am here in parseStationaryScenarioParameters()");
    int numParameters = 2;
    String delimiters;
    StringTokenizer st;
    if (record != null) {
      //This represents the set of node parameters
      delimiters = new String("> =");
      st = new StringTokenizer(record, delimiters);
      if (st.countTokens() == 2) {
        String token = st.nextToken();
        if (token.compareTo("minPauseTime") == 0)
           this.currentEventMinPauseTime = Float.valueOf(st.nextToken()).floatValue();
         if (token.compareTo("maxPauseTime") == 0)
           this.currentEventMaxPauseTime = Float.valueOf(st.nextToken()).floatValue();
         this.countStationaryScenarioParameters++;
       }
      }
      if(this.countStationaryScenarioParameters >= numParameters) {
        this.stationaryScenarioRecord = false;
        this.countStationaryScenarioParameters = 0;
        printStationaryScenarioParameters();
    }
  }

  private void printInitialEventDetails() {
    Output.SIMINFO(this.toString()+": Initial Event Details", Preferences.PRINT_EVENT_MODEL_DETAILS);
    Output.SIMINFO("Event id = "+this.currentEventId+" event name = "+this.currentEventName+" sensor = "+SensorTypes.sensors[this.currentSensorType], Preferences.PRINT_EVENT_MODEL_DETAILS);
  }

  private void printPseudoRandomNumberGeneratorParameters() {
    Output.SIMINFO(this.toString()+": PRNG Parameters", Preferences.PRINT_EVENT_MODEL_DETAILS);
    Output.SIMINFO(" seed = "+this.seed+", luxury = "+this.luxury+", distribution = "+this.distribution+", generator = "+this.generator, Preferences.PRINT_EVENT_MODEL_DETAILS);
  }

  private void printSensingScenarioParameters() {
    Output.SIMINFO(this.toString()+": Sensing Scenario Parameters", Preferences.PRINT_EVENT_MODEL_DETAILS);
    Output.SIMINFO("Sensing Model = "+SensingModelTypes.models[this.currentSensingEventModelType], Preferences.PRINT_EVENT_MODEL_DETAILS);
    Output.SIMINFO("Sensing Model Grid Dimensions = {"+this.currentSensingModelGridDimX+", "+this.currentSensingModelGridDimY+"}", Preferences.PRINT_EVENT_MODEL_DETAILS);
    Output.SIMINFO("Sensing Distance Thresholds = "+this.currentSensingModelNumThresholds+" {", Preferences.PRINT_EVENT_MODEL_DETAILS);
    for(int index = 0; index < this.currentSensingModelNumThresholds; index++) {
      Output.SIMINFO(this.currentEventSensingThresholds[index]+":"+this.currentEventDistanceThresholds[index]+", ", Preferences.PRINT_EVENT_MODEL_DETAILS);
    }
    Output.SIMINFO("}", Preferences.PRINT_EVENT_MODEL_DETAILS);
  }

  private void printStationaryScenarioParameters() {
    Output.SIMINFO(this.toString()+": Stationary Scenario Parameters", Preferences.PRINT_EVENT_MODEL_DETAILS);
    Output.SIMINFO("minPauseTime = "+this.currentEventMinPauseTime+", maxPauseTime = "+this.currentEventMaxPauseTime, Preferences.PRINT_EVENT_MODEL_DETAILS);
  }

  private void printMobilityScenarioParameters() {
    Output.SIMINFO(this.toString()+": Mobility Scenario Parameters", Preferences.PRINT_EVENT_MODEL_DETAILS);
    Output.SIMINFO("minSpeed = "+this.currentEventMinSpeed+" maxSpeed = "+this.currentEventMaxSpeed+" minMobilityTime = "+this.currentEventMinMobilityTime+" maxMobilityTime = "+this.currentEventMaxMobilityTime, Preferences.PRINT_EVENT_MODEL_DETAILS);
  }

  public static void main(String args[]) {
    SensingEventsParametersParser eventParametersParser = new SensingEventsParametersParser("event_params.txt");
    eventParametersParser.parseParamsFile();
  }
}
