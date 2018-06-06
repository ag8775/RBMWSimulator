package rbmwsimulator.test;
import rbmwsimulator.generators.PseudoRandomNumberGenerator;

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
public class TestPseudoRandomNumberGenerator {
  PseudoRandomNumberGenerator prng;
  public TestPseudoRandomNumberGenerator(long numRandomNumbers, String randomNumberFileName, int seed) {
    prng = new PseudoRandomNumberGenerator(numRandomNumbers, randomNumberFileName, seed);
  }

  public void generateRandomNumbers() {
     prng.generateRandomNumbers();
  }

  public static void main(String[] args) {
    int seed = 4765;
    long nRandomNumbers = 100000;
    String fileName = "prng.rn";
    TestPseudoRandomNumberGenerator tPRNG = new TestPseudoRandomNumberGenerator(100000, fileName, seed);
    tPRNG.generateRandomNumbers();
  }

}
