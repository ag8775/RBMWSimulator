package simulator.random;

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
/** Simplifying wrapper for AbstractDistribution.  A RandomDistribution
  * simply holds a reference to an AbstractDistribution, to which it
  * delegates the nextInt() and nextDouble() methods.  This allows us
  * to hide AbstractDistribution from the SSFNet users.<p>
  *
  * Unfortunately, it also hides the details of the underlying distribution
  * from the user --- power users may wish to access the underlying
  * distribution in order to cast it to a known distribution from
  * the CERN package.<p>
  *
  * For example, if we have a RandomDistribution whose implementation is
  * an instance of cern.jet.random.Exponential, we might want to
  * access ((Exponential)getImplementation()).pdf(x), the probability
  * distribution function.<p>
  */
public class RandomDistribution
  extends cern.jet.random.AbstractDistribution {

  protected RandomDistribution(cern.jet.random.AbstractDistribution dist) {
    underlyingDistribution = dist;
  }

  private cern.jet.random.AbstractDistribution underlyingDistribution;

  public cern.jet.random.AbstractDistribution getImplementation() {
    return underlyingDistribution;
  };

  public final double nextDouble() {
    return underlyingDistribution.nextDouble();
  }

  public final int nextInt() {
    return underlyingDistribution.nextInt();
  }
}
