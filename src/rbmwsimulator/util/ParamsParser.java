package rbmwsimulator.util;
import rbmwsimulator.generators.TopologyScenarioGenerator;
import rbmwsimulator.generators.NodeScenarioGenerator;
import simulator.random.Distribution;
import simulator.random.DistributionInfo;
import rbmwsimulator.generators.PseudoRandomNumberGenerator;
import java.util.StringTokenizer;
import java.io.*;
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

public class ParamsParser {
  private String paramsFile;
  private FileReader fr;
  private BufferedReader br;
  private String distribution;
  private String generator;
  private String[] sensorTypes;
  private SimulationParameters simParams;
  private boolean topologicalScenarioRecord;
  private boolean nodeScenarioRecord;
  private boolean prngScenarioRecord;
  private int countTopologicalScenarioParameters;
  private int countNodeScenarioParameters;
  private int countPRNGScenarioParameters;
  private int countSensorTypes;
  private TopologyScenarioGenerator topologyScenarioGenerator;
  private NodeScenarioGenerator nodeScenarioGenerator;
  private String simulationStartTime;
  private int nRandomNumbers;

  public ParamsParser(String paramsFile, String simulationStartTime, int nRandomNumbers) {
    this.paramsFile = paramsFile;
    this.topologicalScenarioRecord = false;
    this.nodeScenarioRecord = false;
    this.prngScenarioRecord = false;
    this.countTopologicalScenarioParameters = 0;
    this.countNodeScenarioParameters = 0;
    this.countPRNGScenarioParameters = 0;
    this.countSensorTypes = 0;
    this.simulationStartTime = simulationStartTime;
    this.nRandomNumbers = nRandomNumbers;
   }

  public void openParamsFile() {
    try {
       this.fr = new FileReader(this.paramsFile);
       this.br = new BufferedReader(fr);
    }
    catch (IOException e) {
       // catch possible io errors from readLine()
       System.out.println("Uh oh, got an IOException error!");
       e.printStackTrace();
    }
  }

  public void closeParamsFile() {
    try {
       this.fr.close();
       this.br.close();
    }
    catch (IOException e) {
       // catch possible io errors from readLine()
       System.out.println("Uh oh, got an IOException error!");
       e.printStackTrace();
    }
  }

  private void generateRandomNumbers(String randomNumbersFileName) {
    DistributionInfo distInfo = new DistributionInfo();
   /*if(this.generator.compareTo("Flat") == 0) {
     if(this.distribution.compareTo("Ranmar") == 0)
      distInfo = Distribution.getDistributionInfoForRanmarFlat(this.seed);
    if(this.distribution.compareTo("Ranecu") == 0)
       distInfo = Distribution.getDistributionInfoForRanecuFlat(this.seed);
     if(this.distribution.compareTo("Ranlux") == 0)
       distInfo = Distribution.getDistributionInfoForRanluxFlat(this.seed, this.luxury);
   }

   if(this.generator.compareTo("Gaussian") == 0) {
     if(this.distribution.compareTo("Ranmar") == 0)
       distInfo = Distribution.getDistributionInfoForRanmarGaussian(this.seed);
     if(this.distribution.compareTo("Ranecu") == 0)
        distInfo = Distribution.getDistributionInfoForRanecuGaussian(this.seed);
      if(this.distribution.compareTo("Ranlux") == 0)
        distInfo = Distribution.getDistributionInfoForRanluxGaussian(this.seed, this.luxury);
   }
   if(distInfo.getDistributionType() != -1)
      PseudoRandomNumberGenerator.generateRandomNumbers(distInfo, randomNumbersFileName, this.nRandomNumbers);
   */
  }

  public TopologyScenarioGenerator getTopologyScenarioGenerator() {
    return this.topologyScenarioGenerator;
  }

  public NodeScenarioGenerator getNodeScenarioGenerator() {
    return this.nodeScenarioGenerator;
  }

  public void parseParamsFile() {
    System.out.println(this.paramsFile);
    String record = null;
    try {
          this.fr = new FileReader(this.paramsFile);
          this.br = new BufferedReader(fr);
          record = new String();
          while (((record = br.readLine()) != null))
          {
            if (record.length() > 0) {
              if (record.charAt(0) != '#') {
                if (record.charAt(0) == '!') {
                  if (record.regionMatches(1, "Topological", 0,
                                           "Topological".length()))
                    this.topologicalScenarioRecord = true;
                  if (record.regionMatches(1, "Node", 0, "Node".length()))
                    this.nodeScenarioRecord = true;
                }
                else if (record.regionMatches(2, "PRNG", 0, "PRNG".length()))
                  this.prngScenarioRecord = true;
                if((record.charAt(0) != '!')&&(record.charAt(1) != '!')) {
                  if(this.prngScenarioRecord)
                    parsePseudoRandomGeneratorParameters(record);
                  else if (this.topologicalScenarioRecord)
                    parseTopologicalScenarioParameters(record);
                  else if (this.nodeScenarioRecord)
                    parseNodeScenarioParameters(record);
                }
              }
            }
          }
          fr.close();
          br.close();
        }
        catch (IOException e)
        {
          // catch possible io errors from readLine()
          System.out.println("Uh oh, got an IOException error!");
          e.printStackTrace();
        }
  }

  public void parseTopologicalScenarioParameters(String record) {
    int numParameters = 3;
    if ((record != null)&&(!this.prngScenarioRecord)&&(this.topologicalScenarioRecord)) {
        //This represents the set of topological parameters
        String delimiters = new String("> =");
        StringTokenizer st = new StringTokenizer(record, delimiters);
        if (st.countTokens() == 2) {
          String token = st.nextToken();
          if (token.compareTo("numNodes") == 0)
            this.simParams.numNodes = Integer.valueOf(st.nextToken()).intValue();
          if (token.compareTo("maxX") == 0)
            this.simParams.maxX = Integer.valueOf(st.nextToken()).intValue();
          if (token.compareTo("maxY") == 0)
            this.simParams.maxY = Integer.valueOf(st.nextToken()).intValue();
          this.countTopologicalScenarioParameters++;
       }
       if(this.countTopologicalScenarioParameters >= numParameters) {
         this.topologicalScenarioRecord = false;
         printTopologicalScenarioParameters();
         String randomNumbersFileName = new String("prng_topo_"+this.simulationStartTime+".rn");
         generateRandomNumbers(randomNumbersFileName);
         //this.topologyScenarioGenerator = new TopologyScenarioGenerator(this.numNodes, this.maxX, this.maxY, this.nRandomNumbers, randomNumbersFileName, new String(this.simulationStartTime));
       }
    }
  }


  public void parsePseudoRandomGeneratorParameters(String record) {
    int numParameters = 4;
     if ((record != null)&&(this.prngScenarioRecord)) {
       //This represents the set of PRNG parameters
       String delimiters = new String("> =");
       StringTokenizer st = new StringTokenizer(record, delimiters);
       if(st.countTokens() == 2) {
         String token = st.nextToken();
         if (token.compareTo("seed") == 0)
           this.simParams.lseed = Long.valueOf(st.nextToken()).longValue();
         if (token.compareTo("luxury") == 0)
           this.simParams.luxury = Integer.valueOf(st.nextToken()).intValue();
         if (token.compareTo("distribution") == 0)
           this.distribution = st.nextToken();
         if (token.compareTo("generator") == 0)
           this.generator = st.nextToken();
         this.countPRNGScenarioParameters++;
       }
    }
    if(this.countPRNGScenarioParameters >= numParameters) {
      this.prngScenarioRecord = false;
      this.countPRNGScenarioParameters = 0;
      printPseudoRandomNumberGeneratorParameters();
    }
  }

  public void parseNodeScenarioParameters(String record) {
    int numParameters = 5;
    String delimiters;
    StringTokenizer st;
    if ((record != null)&&(!this.prngScenarioRecord)&&(this.nodeScenarioRecord)) {
       //This represents the set of node parameters
       delimiters = new String("> =");
       st = new StringTokenizer(record, delimiters);
       if (st.countTokens() == 2) {
         String token = st.nextToken();
         if (token.compareTo("energy") == 0)
           this.simParams.maxBatteryEnergy = Float.valueOf(st.nextToken()).floatValue();
         if (token.compareTo("radio_range") == 0)
           this.simParams.maxRadioRange = Integer.valueOf(st.nextToken()).intValue();
         if (token.compareTo("radio_bit_rate") == 0)
           this.simParams.maxBitRate = Float.valueOf(st.nextToken()).floatValue();
         if (token.compareTo("memory") == 0)
           this.simParams.maxStorageCapacity = Integer.valueOf(st.nextToken()).intValue();
         if (token.compareTo("sensors") == 0) {
           this.simParams.maxSensors = Integer.valueOf(st.nextToken()).intValue();
           this.sensorTypes = new String[this.simParams.maxSensors];
         }
       } else
          if(st.countTokens() == 1) {
           //Lets read the sensor types ...
            if(this.countSensorTypes < this.simParams.maxSensors) {
              delimiters = new String(">");
              st = new StringTokenizer(record, delimiters);
              this.sensorTypes[this.countSensorTypes] = st.nextToken();
              this.countSensorTypes++;
            }
         }
         this.countNodeScenarioParameters++;
      }
      if(this.countNodeScenarioParameters >= (numParameters+this.simParams.maxSensors)) {
        this.nodeScenarioRecord = false;
        this.countNodeScenarioParameters = 0;
        this.countSensorTypes = 0;
        printNodeScenarioParameters();
        String randomNumbersFileName = new String("prng_nodes_"+this.simulationStartTime+".rn");
        generateRandomNumbers(randomNumbersFileName);
        //this.nodeScenarioGenerator = new NodeScenarioGenerator(this.numNodes, this.energy, this.radioRange, this.radioBitRate, this.memorySize, this.numSensors, this.sensorTypes, this.nRandomNumbers, randomNumbersFileName, new String(this.simulationStartTime));
    }
  }

  private void printPseudoRandomNumberGeneratorParameters() {
    System.out.println("PRNG Parameters");
    System.out.println("seed = "+this.simParams.lseed+", luxury = "+this.simParams.luxury+", distribution = "+this.distribution+", generator = "+this.generator);
  }

  private void printNodeScenarioParameters() {
    System.out.println("Node Parameters");
    System.out.print("energy = "+this.simParams.maxBatteryEnergy+", radio_range = "+this.simParams.maxRadioRange+", radio_bit_rate = "+this.simParams.maxBitRate+", memory = "+this.simParams.maxStorageCapacity+", numSensors = "+this.simParams.maxSensors+", ");
    for(int i = 0; i < this.simParams.maxSensors; i++)
      System.out.print(this.sensorTypes[i]+" ");
    System.out.println();
  }

  private void printTopologicalScenarioParameters() {
    System.out.println("Topological Parameters");
    System.out.println("numNodes = "+this.simParams.numNodes+", maxX = "+this.simParams.maxX+", maxY = "+this.simParams.maxY);
  }
}
