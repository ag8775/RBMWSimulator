package rbmwsimulator.test;
import simulator.Simulator;
import simulator.random.*;
import simulator.util.Trace;
import simulator.util.Output;
import rbmwsimulator.generators.*;
import rbmwsimulator.util.*;
import rbmwsimulator.mote.SensorTypes;
import rbmwsimulator.element.Node;
import rbmwsimulator.protocol.Status;
import java.util.Vector;
import rbmwsimulator.model.WirelessNeighborhoodModel;
import rbmwsimulator.gui.RBMWSimulationVisualizer;


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
public class TestRBMWSimulator {
  private SimulationParameters simParameters;
  private boolean setMaxSensors;

  public TestRBMWSimulator(SimulationParameters simParameters_) {
    this.simParameters = simParameters_;
    setMaxSensors = false;
  }

  public void outputCommandLineOptions() {
      System.out.println("java RBMWSimulator <--help> <-no-gui> <-no-print> <-pf params_file_name.txt> <-d 0..15> <-seed seedValue> <-low value> <-high value> <-k value> <-p value> <-alpha value> <-scale value> <-shape value> <-lambda value> <-luxury 0..4> <-nn numNodes> <-x meters> <-y maxY> <-max-rr meters> <-min-rr meters> <-max-sr meters> <-min-sr meters> <-max-be batteryEnergy> <-min-be batteryEnergy> <-max-br Kbps> <-min-br Kbps>  <-max-m MB> <-min-m MB> <-max-cpu Mhz> <-min-cpu Mhz> <-max-ns numSensors> <-min-ns numSensors> <-s 0..5>");
      System.out.println("\t-gui: Run the front-end visualizer (if not specified, default value is false)");
      System.out.println("\t-print: Dump the simulation output on the screen (if not specified, default value is false)");
      System.out.println("\t-pf: Command line options are specified in the parameters file");
      System.out.println("\t-d: Distribution type (if not specified, default value is "+Preferences.DEFAULT_DISTRIBUTION_TYPE+")");
      System.out.println("\t\t 0: Uniform");
      System.out.println("\t\t 1: Uniform Within Interval: {low, high}");
      System.out.println("\t\t 2: Gaussian: {mean, std dev}");
      System.out.println("\t\t 3: Pareto: {K, P, alpha}");
      System.out.println("\t\t 4: Pareto With Shape: {scale, shape}");
      System.out.println("\t\t 5: Exponential: lambda");
      System.out.println("\t\t 6: Zipf");
      System.out.println("\t\t 7: Ranmar with Flat: seed");
      System.out.println("\t\t 8: Ranmar with Gaussian: seed");
      System.out.println("\t\t 9: Ranecu with Flat: seed");
      System.out.println("\t\t 10: Ranecu with Gaussian: seed");
      System.out.println("\t\t 11: Ranlux with Flat: {seed, luxury}");
      System.out.println("\t\t 12: Ranlux with Gaussian: {seed, luxury}");
      System.out.println("\t\t 13: RANJAVA");
      System.out.println("\t\t 14: Mersenne Twister");
      System.out.println("\t\t 15: Java Random");
      System.out.println("\t-seed: Seed value (if not specified, default value is obtained from a random seed table or clock");
      System.out.println("\t-low: Low range for the random number (if not specified, default value is "+Preferences.DEFAULT_LOW_RANGE);
      System.out.println("\t-high: High range for the random number (if not specified, default value is "+Preferences.DEFAULT_HIGH_RANGE);
      System.out.println("\t-k: K value for the Pareto random number generator (K > 0 and K << P, if not specified, default value is "+Preferences.DEFAULT_PARETO_K_VALUE);
      System.out.println("\t-p: P value for the Pareto random number generator (K > 0 and K << P, if not specified, default value is "+Preferences.DEFAULT_PARETO_P_VALUE);
      System.out.println("\t-alpha: Alpha value for the Pareto random number generator (0 < alpha < 2, if not specified, default value is "+Preferences.DEFAULT_PARETO_ALPHA_VALUE);
      System.out.println("\t-scale: Scale value for the Pareto random number generator (scale > 0, if not specified, default value is "+Preferences.DEFAULT_PARETO_SCALE_VALUE);
      System.out.println("\t-shape: Shape value for the Pareto random number generator (shape > 0, if not specified, default value is "+Preferences.DEFAULT_PARETO_SHAPE_VALUE);
      System.out.println("\t-lambda: Lambda value for the Exponential random number generator (lambda > 0, default value is "+Preferences.DEFAULT_EXPONENTIAL_LAMBDA_VALUE);
      System.out.println("\t-luxury: Luxury value (if not specified, default value is "+Preferences.DEFAULT_LUXURY_LEVEL+")");
      System.out.println("\t-nn: Number of nodes (if not specified, default value is "+Preferences.DEFAULT_NUM_NODES+" nodes)");
      System.out.println("\t-x: Maximum X dimension of the area of deployment, in meters (if not specified, default value is "+Preferences.DEFAULT_MAX_X+" meters)");
      System.out.println("\t-y: Maximum Y dimension of the area of deployment, in meters (if not specified, default value is "+Preferences.DEFAULT_MAX_Y+" meters)");
      System.out.println("\t-max-rr, -min-rr: Max (min) mote radio range, in meters (if not specified, default value is "+Preferences.DEFAULT_MAX_RADIO_RANGE+" meters)");
      System.out.println("\t-max-sr, -min-sr: Max (min) mote sensing range, in meters (if not specified, default value is "+Preferences.DEFAULT_MAX_SENSING_RANGE+" meters)");
      System.out.println("\t-max-be, -min-be: Max (min) mote battery energy, in Joules (if not specified, default value is "+Preferences.DEFAULT_MAX_BATTERY_ENERGY+" Joules)");
      System.out.println("\t-max-br, -min-br: Max (min) mote radio bit rate, in Kbps (if not specified, default value is "+Preferences.DEFAULT_MAX_RADIO_BIT_RATE+" Kbps)");
      System.out.println("\t-max-m, -min-m: Max (min) mote storage capacity, in MB (if not specified, default value is "+Preferences.DEFAULT_MAX_STORAGE_CAPACITY+" MB)");
      System.out.println("\t-max-cpu, -min-cpu: Max (min) mote processor speed, in Mhz (if not specified, default value is "+Preferences.DEFAULT_MAX_COMPUTING_POWER+" Mhz)");
      System.out.println("\t-max-ns, -min-ns: Max (min) number of sensors on-board, (if not specified, default value is "+Preferences.DEFAULT_MAX_SENSORS+")");
      System.out.println("\t-s: Sensor types separated by comma (if not specified, default sensors include Temperature and Photo)");
      System.exit(0);
    }


  public void parseSensors(String s) {
    boolean sensorExists = false;
    for(int i = 0; i < SensorTypes.sensors.length; i++) {
      if (SensorTypes.sensors[i].compareTo(s) == 0) {
        simParameters.sensors.addElement(new Integer(i));
        sensorExists = true;
      }
    }
    if(!sensorExists) {
      Output.DEBUG(this.toString()+": Sensor " + s + " does not exist in the database", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
      --simParameters.maxSensors;
    }
  }

  public void parseCommandLineParameters(String[] args) {
    String a;
    int i;
    /* Primitive non-chomskyian parser for command line arguments. */
    parse_loop:
    for (i = 0; i < args.length; i++) {
      a = new String(args[i]);
      //Output.SIMINFO("TestRBMWSimulator: args["+i+"] = "+a);
      a.toLowerCase();

      if(a.compareTo("--help") == 0) {
        outputCommandLineOptions();
        break;
      }

      if (a.compareTo("-gui") == 0) {
        simParameters.showGUI = true;
        continue;
      }
      if (a.compareTo("-no-print") == 0) {
        simParameters.doPrint = true;
        continue;
      }
      if (a.compareTo("-pf") == 0) {
        i++;
        a=new String(args[i]);
        simParameters.paramsFileName = a;
        continue;
      }
      if(a.compareTo("-d") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.distributionType=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Distribution type is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if(simParameters.distributionType<0 || simParameters.distributionType > DistributionTypes.distributionTypes.length)
          Output.ERR(this.toString()+": Distribution type must be between 0 and "+DistributionTypes.distributionTypes.length, Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
      }

      if(a.compareTo("-seed") == 0) {
        i++;
        a=new String(args[i]);
        //Output.SIMINFO("TestRBMWSimulator: Found argument for string -seed = "+a);
        try {
          simParameters.iseed=Integer.parseInt(a);
          simParameters.lseed = simParameters.iseed;
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Seed value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-low") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.lowRange=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Low range value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-high") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.highRange=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": High range value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if (simParameters.highRange < simParameters.lowRange)
          Output.ERR(this.toString()+": High range value must be greater than "+simParameters.lowRange, Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
        }

      if(a.compareTo("-luxury") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.luxury=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Luxury value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if (simParameters.luxury<0 || simParameters.luxury>edu.cornell.lassp.houle.RngPack.Ranlux.maxlev)
          Output.ERR(this.toString()+": luxury level must be between 0 and "+edu.cornell.lassp.houle.RngPack.Ranlux.maxlev, Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
      }

      //double k, p, alpha, scale, shape; //for Pareto distribution
      if(a.compareTo("-k") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.k=Double.parseDouble(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Pareto distribution's k value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if (simParameters.k<=0 || simParameters.k>simParameters.p)
          Output.ERR(this.toString()+": Pareto distribution's k value must be much less than "+simParameters.p, Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
      }

      if(a.compareTo("-p") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.p=Double.parseDouble(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Pareto distribution's p value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if (simParameters.p<=0 || simParameters.p<simParameters.k)
          Output.ERR(this.toString()+": Pareto distribution's p value must be much greater than "+simParameters.k, Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
      }

      if(a.compareTo("-scale") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.scale=Double.parseDouble(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Pareto distribution's scale value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if (simParameters.scale<=0)
          Output.ERR(this.toString()+": Pareto distribution's scale value must be greater than 0", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
      }

      if(a.compareTo("-shape") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.shape=Double.parseDouble(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Pareto distribution's shape value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if (simParameters.shape<=0)
          Output.ERR(this.toString()+": Pareto distribution's shape value must be greater than 0", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
      }

      if(a.compareTo("-alpha") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.alpha=Double.parseDouble(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+": Pareto distribution's alpha value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if (simParameters.alpha<=0|| simParameters.alpha>=2)
          Output.ERR(this.toString()+": Pareto distribution's alpha value must be greater than 0 and less than 2", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
      }

      if(a.compareTo("-lambda") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.lambda=Double.parseDouble(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Pareto distribution's lambda value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        if (simParameters.lambda<=0)
          Output.ERR(this.toString()+":  Pareto distribution's lambda value must be greater than 0", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
          continue;
      }

      if(a.compareTo("-nn") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.numNodes=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Number of nodes is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-x") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxX=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum X coordinate is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-y") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxY=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum Y coordinate is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-max-rr") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxRadioRange=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum radio range is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-min-rr") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.minRadioRange=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Minimum radio range is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }


      if(a.compareTo("-max-sr") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxSensingRange=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum sensing range is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-min-sr") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.minSensingRange=Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Minimum sensing range is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }


      if(a.compareTo("-max-be") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxBatteryEnergy = Float.parseFloat(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum battery energy value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-min-be") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.minBatteryEnergy = Float.parseFloat(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Minimum battery energy value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }


      if(a.compareTo("-max-br") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxBitRate = Float.parseFloat(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum radio bit rate value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-min-br") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.minBitRate = Float.parseFloat(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Minimum radio bit rate value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }


      if(a.compareTo("-max-m") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxStorageCapacity = Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum storage capacity value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-min-m") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.minStorageCapacity = Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Minimum storage capacity value is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }


      if(a.compareTo("-max-cpu") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxComputingPower = Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum processing power is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-min-cpu") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.minComputingPower = Integer.parseInt(a);
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Minimum processing power is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }


      if(a.compareTo("-max-ns") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.maxSensors = Integer.parseInt(a);
          setMaxSensors = true;
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Maximum number of sensors is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }

      if(a.compareTo("-min-ns") == 0) {
        i++;
        a=new String(args[i]);
        try {
          simParameters.minSensors = Integer.parseInt(a);
          setMaxSensors = true;
        }
        catch (NumberFormatException ex) {
          Output.ERR(this.toString()+":  Minimum number of sensors is not a valid number.", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
        }
        continue;
      }


      if(a.compareTo("-s") == 0) {
        if(setMaxSensors) {
          simParameters.sensors.removeAllElements();
          for (int j = 0; j < simParameters.maxSensors; j++) {
            i++;
            a = new String(args[i]);
            parseSensors(a);
          }
        }
        continue;
      }
    }
  }

  public static void main(String args[]) {
    String dateString = Preferences.getDateString();

    String trafficFileName = new String("trafficScenarioFile.txt");
    String eventParametersFileName = new String("event_params_4.txt");

    //Simulation parameters ...
    SimulationParameters simParameters = new SimulationParameters(dateString);
    Trace logTrace = new Trace("log_"+dateString+".txt");
    Output.logTrace = logTrace;

    TestRBMWSimulator rbmwSim = new TestRBMWSimulator(simParameters);
    Simulator sim;

    //GUI option to simulation ...
    RBMWSimulationVisualizer rbmwSimulationVisualizerGUI;

    //Scenario generators ...
    TopologyScenarioGenerator topoScenGen;
    NodeScenarioGenerator nodeScenGen;
    PseudoRandomNumberGenerator prng;
    SimulationEventsTrafficScenarioGenerator trafficScenGen;
    SensingEventsScenarioGenerator sensingEventScenGen;

    //Scenario readers ...
    TopologyReader topoScenReader;
    NodeScenarioReader nodeScenReader;
    SimulationEventsTrafficScenarioReader trafficScenReader;
    SensingEventsParametersParser sensingEventsParametersParser;

    //Simulator elements ...
    Node nodes[];
    int nodeId;
    WirelessNeighborhoodModel wirelessModel;

    //Topological properties manager ...
    NeighborManager neighborManager;

    rbmwSim.parseCommandLineParameters(args);

    simParameters.nRandomNumbers = simParameters.numNodes*2*7*Preferences.DEFAULT_MULTIPLICATIVE_FACTOR_NRANDOM_NUMBERS; //2 for {maxX. maxY} and 7 for {radioRange, sensingRange, radioBitRate, cpuSpeed, memory, maxSensors, and battery}

    Output.SIMINFO("TestRBMWSimulator: Generating pseudo random numbers ...", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
    String randomNumbersFileName = "prng_"+simParameters.nRandomNumbers+"_"+dateString+".rn";
    prng = new PseudoRandomNumberGenerator(simParameters.nRandomNumbers, randomNumbersFileName, simParameters.iseed);
    logTrace.dumpTrace("Generating "+simParameters.nRandomNumbers+" random numbers");

    prng.generateRandomNumbers();

    //Generate topological scenario ...
    Output.SIMINFO("TestRBMWSimulator: Generating topology scenario ...", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
    topoScenGen = new TopologyScenarioGenerator(simParameters.numNodes, simParameters.maxX, simParameters.maxY, simParameters.nRandomNumbers, randomNumbersFileName, dateString);

    topoScenGen.generateScenario();

    //Update the remaining sensors ...
    simParameters.updateOtherSensors();

    //Generate node scenario ...
    Output.SIMINFO("TestRBMWSimulator: Generating node scenario ...", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
    nodeScenGen = new NodeScenarioGenerator(simParameters, simParameters.nRandomNumbers, randomNumbersFileName, dateString, logTrace);
    nodeScenGen.generateScenario();

    //Initialize the scenario readers ...
    Output.SIMINFO("TestRBMWSimulator: Creating topology scenario reader ...", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
    topoScenReader = new TopologyReader(topoScenGen.getTopologyScenarioFile(), simParameters.numNodes);
    Output.SIMINFO("TestRBMWSimulator: Creating node scenario reader ...", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
    nodeScenReader = new NodeScenarioReader(nodeScenGen.getNodeScenarioFile(), simParameters.numNodes, simParameters.lseed, simParameters.luxury);

    //Read the scenario files ...
    topoScenReader.readTopologyScenarioFile();
    nodeScenReader.readNodeScenarioFile();

    //Initialize the nodes ...
    Output.SIMINFO("TestRBMWSimulator: Creating "+simParameters.numNodes+" Node Objects", Preferences.PRINT_RBMW_SIMULATOR_ACTIVITY);
    nodes = new Node[simParameters.numNodes];
    for(nodeId = 0; nodeId < simParameters.numNodes; nodeId++) {
      nodes[nodeId] = new Node(nodeId, topoScenReader.getCoordinatesFor(nodeId), nodeScenReader.getMoteObjectFor(nodeId), Status.DOWN);
      //nodes[nodeId].dump();
    }

    trafficScenReader = new SimulationEventsTrafficScenarioReader(trafficFileName, nodes);
    trafficScenReader.loadTrafficPatternOntoSimulator();

    //Initialize the NeighborManager ...
    neighborManager = new NeighborManager(simParameters.numNodes, nodes, topoScenReader, nodeScenReader);
    //Find 1-hop neighbors (active and inactive both)
    neighborManager.findNeighbors();
    //neighborManager.dump();

    //Parse the event parameters file ...
    sensingEventsParametersParser = new SensingEventsParametersParser(eventParametersFileName, simParameters.numNodes, simParameters.maxX, simParameters.maxY, topoScenReader.getNodePositions());
    sensingEventsParametersParser.parseParamsFile();

    //Generate Random Numbers files for the events ...
    PseudoRandomNumberGenerator event_prng[] = new PseudoRandomNumberGenerator[sensingEventsParametersParser.getEventCount()];
    String event_prng_file_name[] = new String[sensingEventsParametersParser.getEventCount()];
    int eventIndex;
    long numEventRandomNumbers = 14000;
    Vector sensingEvents = sensingEventsParametersParser.getSensingEventVector();
    for(eventIndex = 0; eventIndex < sensingEventsParametersParser.getEventCount(); eventIndex++) {
      event_prng_file_name[eventIndex] = new String("event_prng_"+(eventIndex+1)+"_r_"+numEventRandomNumbers+"_"+dateString+".rn");
      event_prng[eventIndex] = new PseudoRandomNumberGenerator(numEventRandomNumbers, event_prng_file_name[eventIndex], ((SensingEvent)(sensingEvents.elementAt(eventIndex))).getSeed());
      logTrace.dumpTrace("Generating "+numEventRandomNumbers+" random numbers for event "+(eventIndex+1));
      event_prng[eventIndex].generateRandomNumbers();
      ((SensingEvent)(sensingEvents.elementAt(eventIndex))).getCurrentEventStatus().setRandomNumbersFileName(event_prng_file_name[eventIndex]);
    }

    //Initialize the EventScenarioGenerator
    sensingEventScenGen = new SensingEventsScenarioGenerator(sensingEventsParametersParser.getEventCount(), trafficScenReader.getTotalSimulationTime(), dateString, sensingEventsParametersParser.getSensingEventVector(), simParameters.numNodes, nodes);
    sensingEventScenGen.generateScenario();

    //Initialize the Wireless Model ...
    wirelessModel = new WirelessNeighborhoodModel(neighborManager, nodes, simParameters.numNodes);

    //Show GUI
    if(simParameters.showGUI) {
       rbmwSimulationVisualizerGUI = new RBMWSimulationVisualizer();
       rbmwSimulationVisualizerGUI.createAndShowGui();
    }

    //Log Simulation Parameters
    simParameters.logSimulationParameters();

    // Get a simulator
    sim = Simulator.getInstance();

    // Start simulating
    sim.run();

    //Close the log file ...
    logTrace.closeTraceFile();
  }
}
