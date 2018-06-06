package rbmwsimulator.generators;
import edu.cornell.lassp.houle.RngPack.*;
import cern.jet.random.engine.*;
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
public class PseudoRandomNumberGenerator {
    RandomElement randomNumberGenerator;
    long nRandomNumbers;
    String randomNumberFileName;
    Trace randomTrace;
    long seed;

    public PseudoRandomNumberGenerator(long numRandomNumbers, String randomNumberFileName, long seed) {
       this.nRandomNumbers = numRandomNumbers;
       this.randomNumberFileName = randomNumberFileName;
       randomTrace = new Trace(randomNumberFileName);
       this.seed = seed;
       randomNumberGenerator = new MersenneTwister((int)this.seed); //Currently using this random engine ... will make it generic in future where it accepts other generators ...
    }

    public void generateRandomNumbers() {
      for(long i=1; i<=nRandomNumbers; i++)
        randomTrace.dumpTrace(i+": "+(new Double(randomNumberGenerator.raw())).toString()+";");
      randomTrace.closeTraceFile();
    }
}
