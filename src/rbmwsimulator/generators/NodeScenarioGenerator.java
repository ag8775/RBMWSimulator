package rbmwsimulator.generators;
import rbmwsimulator.mote.SensorTypes;
import rbmwsimulator.util.Preferences;
import rbmwsimulator.util.SimulationParameters;
import java.util.Vector;
import simulator.util.FormatWriter;
import simulator.util.Trace;
import simulator.util.Output;
import java.io.*;
import java.util.StringTokenizer;
import edu.cornell.lassp.houle.RngPack.*;
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

/** Randomly generate heterogeneous sensor node types with different
 *   (1) number and type of sensors
 *   (2) cpu speeds
 *   (3) energy and sensing model
 *   (4) storage types and sizes
 *   (5) radio
 *   (6) battery
 */
public class NodeScenarioGenerator {
  private int numNodes;
  private long nRandomNumbers;
  private long seed;
  private FormatWriter fileout ;
  private Trace logfile;
  private String scenarioFile, randomNumbersFileName;
  private String appendPartialFileName;
  private int minSensors;
  private int maxSensors;
  private Vector defaultSensors;
  private Vector otherSensors;
  private int minSensingRange; //in meters
  private int maxSensingRange; //in meters
  private int minMemory; //in MB
  private int maxMemory;
  private int maxCpuSpeed; //in Mhz
  private int minCpuSpeed;
  private float minRadioBR; //in Kbps
  private float maxRadioBR;
  private int minRadioRange; //in meters
  private int maxRadioRange; //in meters
  private float minEnergy; //in Joules
  private float maxEnergy;
  private int luxury = 4;
  private RandomElement e;


  //Future Work: Energy and Sensing model
   public NodeScenarioGenerator(int numNodes_, int minSensors_, int maxSensors_, Vector defaultSensors_, Vector otherSensors_, int minSensingRange_, int maxSensingRange_, int minMemory_, int maxMemory_,  int minCpuSpeed_, int maxCpuSpeed_, float minRadioBR_, float maxRadioBR_, int minRadioRange_, int maxRadioRange_, float minEnergy_, float maxEnergy_, long nRandomNumbers_, String randomNumbersFileName_, long seed_, String partialFileName_, Trace trace_) {
     this.numNodes = numNodes_;
     this.minSensors = minSensors_;
     this.maxSensors = maxSensors_;
     this.defaultSensors = new Vector();
     this.defaultSensors.addAll(defaultSensors_);
     this.otherSensors = new Vector();
     this.otherSensors.addAll(otherSensors_);
     this.minSensingRange = minSensingRange_;
     this.maxSensingRange = maxSensingRange_;
     this.minMemory = minMemory_;
     this.maxMemory = maxMemory_;
     this.minCpuSpeed = minCpuSpeed_;
     this.maxCpuSpeed = maxCpuSpeed_;
     this.minRadioBR = minRadioBR_;
     this.maxRadioBR = maxRadioBR_;
     this.minRadioRange = minRadioRange_;
     this.maxRadioRange = maxRadioRange_;
     this.minEnergy = minEnergy_;
     this.maxEnergy = maxEnergy_;
     this.nRandomNumbers = nRandomNumbers_;
     this.randomNumbersFileName = randomNumbersFileName_;
     this.appendPartialFileName = partialFileName_;
     this.logfile = trace_;
     this.seed = seed_;
     this.luxury = 4;
     this.e = new Ranlux(luxury,seed);
     //dumpTrace(new String("Default Sensors = "+sensorsString(defaultSensors)));
   }

   public NodeScenarioGenerator(SimulationParameters simParams, long nRandomNumbers_, String randomNumbersFileName_, String partialFileName_, Trace trace_) {
      this.numNodes = simParams.numNodes;
      this.minSensors = simParams.minSensors;
      this.maxSensors = simParams.maxSensors;
      this.defaultSensors = new Vector();
      this.defaultSensors.addAll(simParams.sensors);
      this.otherSensors = new Vector();
      this.otherSensors.addAll(simParams.otherSensors);
      this.minSensingRange = simParams.minSensingRange;
      this.maxSensingRange = simParams.maxSensingRange;
      this.minMemory = simParams.minStorageCapacity;
      this.maxMemory = simParams.maxStorageCapacity;
      this.minCpuSpeed = simParams.minComputingPower;
      this.maxCpuSpeed = simParams.maxComputingPower;
      this.minRadioBR = simParams.minBitRate;
      this.maxRadioBR = simParams.maxBitRate;
      this.minRadioRange = simParams.minRadioRange;
      this.maxRadioRange = simParams.maxRadioRange;
      this.minEnergy = simParams.minBatteryEnergy;
      this.maxEnergy = simParams.maxBatteryEnergy;
      this.nRandomNumbers = nRandomNumbers_;
      this.randomNumbersFileName = randomNumbersFileName_;
      this.appendPartialFileName = partialFileName_;
      this.logfile = trace_;
      this.seed = simParams.lseed;
      this.luxury = simParams.luxury;
      this.e = new Ranlux(luxury,seed);
      //dumpTrace(new String("Default Sensors = "+sensorsString(defaultSensors)));
    }


  private int getScalingNumberForMaxThresholdRange(float maxThreshold) {
    int scale = 1;
    if(maxThreshold < (float)10)
      scale = 10;
    else if(maxThreshold < (float)100)
      scale = 100;
    else
      scale = 1000;
    return scale;
  }

  private float getRandomValue(String randomNumberRecord, int nodeScenarioIndex) {
    // Random Number Scenario File format ...
    // index: randomNumber;
    // 2: 0.5632242;
    float randomFloatVal = 0;
    String delimiters = new String(": ;");
    long index = 0;
    StringTokenizer st = new StringTokenizer(randomNumberRecord, delimiters);
    float randomNumber;
    float scale;
    while(st.hasMoreTokens()){
      index = Long.valueOf(st.nextToken()).longValue(); //ignore index ...
      randomNumber = (Float.valueOf(st.nextToken()).floatValue());
      //dumpTrace(new String(nodeScenarioIndex+" Random number = "+randomNumber));
      switch(nodeScenarioIndex) {
        case 1: //Randomize numSensors scenario ...
          if(!Preferences.DEFAULT_EQUAL_SENSORS_OPTION) {
            scale = (float)getScalingNumberForMaxThresholdRange(this.maxSensors - this.minSensors);
            randomFloatVal = this.minSensors + Math.round(((this.maxSensors - this.minSensors)*(randomNumber*scale))/scale);
          } else
             randomFloatVal = this.maxSensors;
          break;
        case 2: //Randomize sensingRange scenario ...
          if(!Preferences.DEFAULT_EQUAL_SENSING_RANGE_OPTION){
            scale = (float)getScalingNumberForMaxThresholdRange(this.maxSensingRange - this.minSensingRange);
            randomFloatVal = this.minSensingRange + Math.round(((this.maxSensingRange - this.minSensingRange)*(randomNumber*scale))/scale);
          } else
             randomFloatVal = this.maxSensingRange;
          break;
        case 3: //Randomize radioRange scenario ...
          if(!Preferences.DEFAULT_EQUAL_RADIO_RANGE_OPTION) {
            scale = (float)getScalingNumberForMaxThresholdRange(this.maxRadioRange - this.minRadioRange);
            randomFloatVal = this.minRadioRange + Math.round(((this.maxRadioRange - this.minRadioRange)*(randomNumber*scale))/scale);
          } else
              randomFloatVal = this.maxRadioRange;
          break;
        case 4: //Randomize memory scenario ...
          if(!Preferences.DEFAULT_EQUAL_STORAGE_OPTION) {
            scale = (float)getScalingNumberForMaxThresholdRange(this.maxMemory - this.minMemory);
            randomFloatVal = this.minMemory + Math.round(((this.maxMemory - this.minMemory)*(randomNumber*scale))/scale);
          } else
             randomFloatVal = this.maxMemory;
          break;
        case 5: //Randomize cpuSpeed scenario ...
          if(!Preferences.DEFAULT_EQUAL_COMPUTING_POWER_OPTION) {
            scale = (float)getScalingNumberForMaxThresholdRange(this.maxCpuSpeed - this.minCpuSpeed);
            randomFloatVal = this.minCpuSpeed + Math.round(((this.maxCpuSpeed - this.minCpuSpeed)*(randomNumber*scale))/scale);
          } else
             randomFloatVal = this.maxCpuSpeed;
          break;
        case 6: //Randomize radioBR scenario ...
          if(!Preferences.DEFAULT_EQUAL_RADIO_BIT_RATE_OPTION) {
            scale = (float)getScalingNumberForMaxThresholdRange(this.maxRadioBR - this.minRadioBR);
            randomFloatVal = this.minRadioBR + Math.round(((this.maxRadioBR - this.minRadioBR)*(randomNumber*scale))/scale);
          } else
             randomFloatVal = this.maxRadioBR;
          break;
        case 7: //Randomize energy scenario ...
          if(!Preferences.DEFAULT_EQUALIZE_ENERGY_OPTION) {
            scale = (float) getScalingNumberForMaxThresholdRange(this.maxEnergy - this.minEnergy);
            randomFloatVal = this.minEnergy + Math.round(((this.maxEnergy - this.minEnergy)*(randomNumber * scale)) / scale);
          } else
            randomFloatVal = this.maxEnergy;
          break;
        default:
          break;
      }
    }
    //dumpTrace(new String(nodeScenarioIndex+" Random value = "+randomFloatVal));
    return randomFloatVal;
  }

  private Vector getExtraSensors(int desiredSensors) {
    Vector extraSensors = new Vector();
    int numSensors =  this.otherSensors.size();
    Vector availableSensors = new Vector();
    availableSensors.addAll(this.otherSensors);

    int randomValue = -1, sensor;

    if(numSensors < desiredSensors) {
      desiredSensors = numSensors;
    }

    while(desiredSensors > 0) {
      if(!Preferences.DEFAULT_EQUAL_SENSORS_OPTION)
        randomValue = (int)(Math.round(e.raw()*desiredSensors));
      else
        ++randomValue;
      sensor = ((Integer)(availableSensors.elementAt(randomValue))).intValue();
      availableSensors.removeElementAt(randomValue);
      extraSensors.add(new Integer(sensor));
      --desiredSensors;
    }

    return extraSensors;
  }

  private String sensorsString(Vector sensors) {
     String sensorStr = new String();
     int size = sensors.size();
     //dumpTrace(new String(" Sensor's size "+size));
     int index = 0;

     for(index = 0; index < size; index++) {
       sensorStr += SensorTypes.sensors[((Integer) (sensors.elementAt(index))).intValue()];
       if(index != (size - 1))
         sensorStr +=", ";
     }
     return sensorStr;
  }

   public void generateScenario() {
     int numSensors;
     Vector sensors = new Vector();
     int sensingRange; //in meters
     int memory; //in MB
     int cpuSpeed; //in Mhz
     float radioBR; //in Kbps
     int radioRange; //in meters
     float energy; //in Joules
     float [] randomNumbers;
     randomNumbers = new float[8]; //we are not using the 0th index ...
     String record = null;
     int i=0, j=0, k = 1;

     openInfoFile();
     try {
       FileReader fr = new FileReader(this.randomNumbersFileName);
       BufferedReader br = new BufferedReader(fr);
       //record = new String();
       while(i < numNodes) {
         while (((record = br.readLine()) != null)&&(j < this.nRandomNumbers)) {
           //7 Random Numbers
           //dumpTrace(new String("j = "+j));
           ++j;
           randomNumbers[j%8] = getRandomValue(record, j%8); //losing a randomNumber due to the by product of j%8 == 0 for multiples of 8 and also because we ignore the 0th element ...
           if(j%8 == 0) {
             sensors.removeAllElements();
             sensors.addAll(this.defaultSensors);
             numSensors = Math.round(randomNumbers[1]);
             //Append extra sensors if numSensors > minSensors
             sensors.addAll(getExtraSensors(numSensors - minSensors));
             sensingRange = Math.round(randomNumbers[2]);
             radioRange = Math.round(randomNumbers[3]);
             memory = Math.round(randomNumbers[4]);
             cpuSpeed = Math.round(randomNumbers[5]);
             radioBR = (float)randomNumbers[6];
             energy = (float)randomNumbers[7];
             printInfo(i+": "+sensingRange+", "+radioRange+", "+radioBR+", "+cpuSpeed+", "+memory+", "+energy+", "+numSensors+", {"+sensorsString(sensors)+"};");
             break; //so that we continue with the next node in the outer while loop ...
           }//end if
         }//end inner while
         ++i;
       }//end outer while
     }//end try
     catch (IOException e) {
        // catch possible io errors from readLine()
        Output.ERR(this.toString()+": Uh oh, got an IOException error!", true);
        e.printStackTrace();
     }
     closeInfoFile();
  }

   public void openInfoFile() {
     try {
       this.scenarioFile = new String("node_scen_"+appendPartialFileName+".scen");
       //this.scenarioFile = new String("nodes_"+numNodes+"_mins_"+(new Integer(minSensors)).toString()+"_maxs_"+(new Integer(maxSensors)).toString()+"_minsr_"+(new Integer(minSensingRange)).toString()+"_maxsr_"+(new Integer(maxSensingRange)).toString()+"_minm_"+(new Integer(minMemory)).toString()+"_maxm_"+(new Integer(maxMemory)).toString()+"_minc_"+(new Integer(minCpuSpeed)).toString()+"_maxc_"+(new Integer(maxCpuSpeed)).toString()+"_minrbr_"+(new Float(minRadioBR)).toString()+"_maxrbr_"+(new Float(maxRadioBR)).toString()+"_minrr_"+(new Integer(minRadioRange)).toString()+"_maxrr_"+(new Integer(maxRadioRange)).toString()+"_mine_"+(new Float(minEnergy)).toString()+"_maxe_"+(new Float(maxEnergy)).toString()+"_n_"+nRandomNumbers+"_seed_"+this.seed+"_lux_"+this.luxury+".scen");
       Output.SIMINFO("$$$$$$$$$$ File name = "+this.scenarioFile, true);
       this.fileout = new FormatWriter(new BufferedWriter(new FileWriter(this.scenarioFile)), 5);
     }
     catch (IOException ae) {
       Output.ERR(this.toString()+": IO exception thrown: ", true, ae);
     }
  }

  public String getNodeScenarioFile() {
    return this.scenarioFile;
  }

  public void closeInfoFile() {
    try {
      fileout.close();
    }
    catch (Exception io) {
     Output.ERR(this.toString()+": Error during closing Topology file", true);
    }
  }

  public void printInfo(String str) {
    try {
      fileout.println(str);
      Output.SIMINFO(this.toString()+": "+str, Preferences.PRINT_NODE_SCENARIO_MODEL_DETAILS);
    }
    catch(Exception ae) {
      Output.ERR(this.toString()+": IO exception Thrown: " + ae, true);
    }
  }
}
