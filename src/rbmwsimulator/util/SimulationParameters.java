package rbmwsimulator.util;
import java.util.*;
import rbmwsimulator.util.Preferences;
import rbmwsimulator.mote.SensorTypes;
import simulator.util.Trace;
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
public class SimulationParameters {
    public boolean showGUI; //=> we are using command-line options ...
    public boolean doPrint; //dump results on the output screen ...

    public boolean useParamsFile; //read the command line options from a file ...
    public String paramsFileName;

    //Command line options for the random number generator ...
    public int distributionType;
    public long nRandomNumbers;
    public long lseed;
    public int iseed;
    public int luxury; // for RANLUX prng ... max level = 4
    public int lowRange; //for Uniform random distribution ...
    public int highRange;
    public double k, p, alpha, scale, shape; //for Pareto distribution
    public double lambda; // for exponential distribution ...

    //Command line options for the topology generator ...
    public int numNodes;
    public int maxX, maxY; // expressed in meters ...

    //Command line options for the node scenario generator ...
    public float maxBatteryEnergy; //in Joules
    public int maxRadioRange; // in meters
    public int maxSensingRange; //in meters
    public float maxBitRate; //in Kbps
    public int maxStorageCapacity; //in MB
    public int maxComputingPower; //in mips (Million Instructions Per Second)
    public Vector sensors;
    public Vector otherSensors;
    public int maxSensors;

    public float minBatteryEnergy;
    public int minRadioRange; // in meters
    public int minSensingRange; //in meters
    public float minBitRate; //in Kbps
    public int minStorageCapacity; //in MB
    public int minComputingPower; //in mips (Million Instructions Per Second)
    public int minSensors;

    private String simulationScenarioParametersFilename = new String("sim_params_");
    private  Trace simulationScenarioParametersTrace;

    public SimulationParameters(String dateString) {
      //Default Options ...
      showGUI = false;
      doPrint = false;
      useParamsFile = false;

      distributionType = Preferences.DEFAULT_DISTRIBUTION_TYPE;
      lseed = Preferences.DEFAULT_LONG_SEED;
      iseed = Preferences.DEFAULT_INT_SEED;
      luxury = Preferences.DEFAULT_LUXURY_LEVEL;
      lowRange = Preferences.DEFAULT_LOW_RANGE;
      highRange = Preferences.DEFAULT_HIGH_RANGE;
      k = Preferences.DEFAULT_PARETO_K_VALUE;
      p = Preferences.DEFAULT_PARETO_P_VALUE;
      alpha = Preferences.DEFAULT_PARETO_ALPHA_VALUE;
      scale = Preferences.DEFAULT_PARETO_SCALE_VALUE;
      shape = Preferences.DEFAULT_PARETO_SHAPE_VALUE;
      lambda = Preferences.DEFAULT_EXPONENTIAL_LAMBDA_VALUE;
      numNodes = Preferences.DEFAULT_NUM_NODES;
      maxX = Preferences.DEFAULT_MAX_X;
      maxY = Preferences.DEFAULT_MAX_Y;

      maxBatteryEnergy = Preferences.DEFAULT_MAX_BATTERY_ENERGY;
      maxRadioRange = Preferences.DEFAULT_MAX_RADIO_RANGE;
      maxSensingRange = Preferences.DEFAULT_MAX_SENSING_RANGE;
      maxBitRate = Preferences.DEFAULT_MAX_RADIO_BIT_RATE;
      maxStorageCapacity = Preferences.DEFAULT_MAX_STORAGE_CAPACITY;
      maxComputingPower = Preferences.DEFAULT_MAX_COMPUTING_POWER;
      maxSensors = Preferences.DEFAULT_MAX_SENSORS;

      minBatteryEnergy = Preferences.DEFAULT_MAX_BATTERY_ENERGY - Preferences.DEFAULT_DELTA_BATTERY_ENERGY;
      minRadioRange = Preferences.DEFAULT_MAX_RADIO_RANGE - Preferences.DEFAULT_DELTA_RADIO_RANGE;
      minSensingRange = Preferences.DEFAULT_MAX_SENSING_RANGE - Preferences.DEFAULT_DELTA_SENSING_RANGE;
      minBitRate = Preferences.DEFAULT_MAX_RADIO_BIT_RATE - Preferences.DEFAULT_DELTA_RADIO_BIT_RATE;
      minStorageCapacity = Preferences.DEFAULT_MAX_STORAGE_CAPACITY - Preferences.DEFAULT_DELTA_STORAGE_CAPACITY;
      minComputingPower = Preferences.DEFAULT_MAX_COMPUTING_POWER - Preferences.DEFAULT_DELTA_COMPUTING_POWER;
      minSensors = Preferences.DEFAULT_MAX_SENSORS - Preferences.DEFAULT_DELTA_SENSORS;

      //Default Sensors: Temperature and Photo
      sensors = new Vector();
      sensors.addElement(new Integer(SensorTypes.TEMPERATURE_SENSOR));
      sensors.addElement(new Integer(SensorTypes.PHOTO_SENSOR));
      otherSensors = new Vector();
      updateOtherSensors();
      simulationScenarioParametersTrace = new Trace(simulationScenarioParametersFilename+dateString+".txt");
    }

    public void logSimulationParameters() {
      //PRNG
      simulationScenarioParametersTrace.dumpTrace("nRandomNumbers: "+nRandomNumbers);
      simulationScenarioParametersTrace.dumpTrace("distributionType: "+distributionType);
      simulationScenarioParametersTrace.dumpTrace("lseed: "+lseed);
      simulationScenarioParametersTrace.dumpTrace("iseed: "+iseed);
      simulationScenarioParametersTrace.dumpTrace("luxury: "+luxury);
      simulationScenarioParametersTrace.dumpTrace("lowRange: "+lowRange);
      simulationScenarioParametersTrace.dumpTrace("highRange: "+highRange);
      simulationScenarioParametersTrace.dumpTrace("k: "+k);
      simulationScenarioParametersTrace.dumpTrace("p: "+p);
      simulationScenarioParametersTrace.dumpTrace("alpha: "+alpha);
      simulationScenarioParametersTrace.dumpTrace("scale: "+scale);
      simulationScenarioParametersTrace.dumpTrace("shape: "+shape);
      simulationScenarioParametersTrace.dumpTrace("lambda: "+lambda);

      //Topology Scenario Parameters
      simulationScenarioParametersTrace.dumpTrace("Number of nodes: "+numNodes);
      simulationScenarioParametersTrace.dumpTrace("maxX: "+maxX);
      simulationScenarioParametersTrace.dumpTrace("maxY: "+maxY);

      //Node Scenario Parameters
     simulationScenarioParametersTrace.dumpTrace("maxBatteryEnergy: "+maxBatteryEnergy);
     simulationScenarioParametersTrace.dumpTrace("minBatteryEnergy: "+minBatteryEnergy);
     simulationScenarioParametersTrace.dumpTrace("maxRadioRange: "+ maxRadioRange);
     simulationScenarioParametersTrace.dumpTrace("minRadioRange: "+minRadioRange);
     simulationScenarioParametersTrace.dumpTrace("maxSensingRange: "+maxSensingRange);
     simulationScenarioParametersTrace.dumpTrace("minSensingRange: "+minSensingRange);
     simulationScenarioParametersTrace.dumpTrace("maxBitRate: "+maxBitRate);
     simulationScenarioParametersTrace.dumpTrace("minBitRate: "+minBitRate);
     simulationScenarioParametersTrace.dumpTrace("maxStorageCapacity: "+maxStorageCapacity);
     simulationScenarioParametersTrace.dumpTrace("minStorageCapacity: "+minStorageCapacity);
     simulationScenarioParametersTrace.dumpTrace("maxComputingPower: "+maxComputingPower);
     simulationScenarioParametersTrace.dumpTrace("minComputingPower: "+minComputingPower);
     simulationScenarioParametersTrace.dumpTrace("maxSensors: "+maxSensors);
     simulationScenarioParametersTrace.dumpTrace("minSensors: "+minSensors);

     for(int index = 0; index < sensors.size(); index++) {
         simulationScenarioParametersTrace.dumpTrace("Sensor: "+SensorTypes.sensors[((Integer)(sensors.elementAt(index))).intValue()]);
     }
     simulationScenarioParametersTrace.closeTraceFile();
    }

    //add only those sensors that are not in sensor vector ...
    public void updateOtherSensors() {
      otherSensors.removeAllElements();
      for(int sensorIndex = 0; sensorIndex < SensorTypes.sensors.length; sensorIndex++) {
        Integer sensor = new Integer(sensorIndex);
        if(!sensors.contains(sensor)) {
          otherSensors.add(sensor);
        }
      }
    }
}
