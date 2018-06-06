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

public class DistributionTypes {
  public static final String distributionTypes[] = {"Uniform Random", "Uniform Random With Interval", "Gaussian Random", "Pareto Random", "Pareto Random With Shape", "Exponential Random", "Zipf Random", "Ranmar Flat", "Ranmar Gaussian", "Ranecu Flat", "Ranecu Gaussian", "Ranlux Flat", "Ranlux Gaussian", "Ranjava", "Mersenne Twister", "Java Random"};

  public static final int UNIFORM_RANDOM = 0;
  public static final int UNIFORM_RANDOM_WITHIN_INTERVAL = 1;
  public static final int GAUSSIAN_RANDOM = 2;
  public static final int PARETO_RANDOM = 3;
  public static final int PARETO_RANDOM_WITH_SHAPE = 4 ;
  public static final int EXPONENTIAL_RANDOM = 5;
  public static final int ZIPF_RANDOM = 6;
  public static final int RANMAR_FLAT = 7;
  public static final int RANMAR_GAUSSIAN = 8;
  public static final int RANECU_FLAT = 9;
  public static final int RANECU_GAUSSIAN = 10;
  public static final int RANLUX_FLAT = 11;
  public static final int RANLUX_GAUSSIAN = 12;
  public static final int RANJAVA = 13;
  public static final int MERSENNE_TWISTER = 14;
  public static final int JAVA_RANDOM = 15;
}
