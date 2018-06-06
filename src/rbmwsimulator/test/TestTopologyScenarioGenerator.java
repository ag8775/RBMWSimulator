package rbmwsimulator.test;
import rbmwsimulator.util.Preferences;
import rbmwsimulator.generators.TopologyScenarioGenerator;
import rbmwsimulator.generators.PseudoRandomNumberGenerator;
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
public class TestTopologyScenarioGenerator {

    public static void main(String args[]) {
      int numNodes = 100;
      int maxX = 640, maxY = 640, nRandomNumbers = 10000;
      String dateString = Preferences.getDateString();
      String randomNumbersFileName = "prng_"+nRandomNumbers+"_"+dateString+".rn";
      int seed = 4756;
      System.out.println(dateString);

      PseudoRandomNumberGenerator prng = new PseudoRandomNumberGenerator(nRandomNumbers, randomNumbersFileName, seed);
      prng.generateRandomNumbers();
      TopologyScenarioGenerator topScenGen = new TopologyScenarioGenerator(numNodes, maxX, maxY, nRandomNumbers, randomNumbersFileName, dateString);
      topScenGen.generateScenario();

    }
}
