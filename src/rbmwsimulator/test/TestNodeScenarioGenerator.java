package rbmwsimulator.test;
import rbmwsimulator.mote.SensorTypes;
import rbmwsimulator.generators.NodeScenarioGenerator;
import rbmwsimulator.util.Preferences;
import rbmwsimulator.generators.PseudoRandomNumberGenerator;
import java.util.Vector;
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
public class TestNodeScenarioGenerator {
   public static void main(String args[]) {
       int numNodes = 100;
       int nRandomNumbers = 10000;
       String scenarioFile, randomNumbersFileName;
       String appendPartialFileName;
       int minSensors = 2;
       int maxSensors = 4;
       Vector defaultSensors = new Vector();
       defaultSensors.add(new Integer(SensorTypes.TEMPERATURE_SENSOR));
       defaultSensors.add(new Integer(SensorTypes.PHOTO_SENSOR));
       Vector otherSensors = new Vector();
       otherSensors.add(new Integer(SensorTypes.HUMIDITY_SENSOR));
       otherSensors.add(new Integer(SensorTypes.PRESSURE_SENSOR));
       otherSensors.add(new Integer(SensorTypes.SOUND_SENSOR));
       otherSensors.add(new Integer(SensorTypes.VIBRATION_SENSOR));
       int minSensingRange = 60; //in meters
       int maxSensingRange = 100; //in meters
       int minMemory = 1; //in MB
       int maxMemory = 2;
       int maxCpuSpeed = 10; //in Mhz
       int minCpuSpeed = 2;
       float minRadioBR = (float)2.5; //in Kbps
       float maxRadioBR = (float)5.5;
       int minRadioRange = 60; //in meters
       int maxRadioRange = 100; //in meters
       float minEnergy = 150; //in Joules
       float maxEnergy = 300 ;
       Trace logTrace = new Trace("log.txt");
       String dateString = Preferences.getDateString();
       randomNumbersFileName = "prng_"+nRandomNumbers+"_"+dateString+".rn";
       int seed = 4756;
       //System.out.println(dateString);
       PseudoRandomNumberGenerator prng = new PseudoRandomNumberGenerator(nRandomNumbers, randomNumbersFileName, seed);
       prng.generateRandomNumbers();
       NodeScenarioGenerator nodeScenGen = new NodeScenarioGenerator(numNodes, minSensors, maxSensors, defaultSensors, otherSensors, minSensingRange, maxSensingRange, minMemory, maxMemory,  minCpuSpeed, maxCpuSpeed, minRadioBR, maxRadioBR, minRadioRange, maxRadioRange, minEnergy, maxEnergy, nRandomNumbers, randomNumbersFileName, seed, Preferences.getDateString(), logTrace);
       nodeScenGen.generateScenario();
       logTrace.closeTraceFile();

   }
}
