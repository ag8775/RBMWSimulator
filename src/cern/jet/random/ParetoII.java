
package cern.jet.random;

import edu.cornell.lassp.houle.RngPack.RandomElement;
/**
 * Pareto II Distribution is modified Pareto Distribution by left shift along the x-axis. For Pareto Distribution, see the
 * <A HREF="http://www.treasure-troves.com/math/ParetoDistribution.html"> math definition</A>
 * <A HREF="http://www.statsoft.com/textbook/glosp.html#Pareto Distribution"> animated definition</A>.
 * <p>
 * <tt>p(x) = shape*scale^shape*(x+shift)^{-(1+shape)}</tt> for <tt>x &gt;= scale</tt>, <tt>shape &gt; 0</tt>.
 * <p>
 * Note that mean is infinite if <tt>shape <= 1</tt> and variance is infinite if
 * <tt>shape <= 2</tt>.
 * <p>
 * Instance methods operate on a user supplied uniform random number generator; they are unsynchronized.
 * <dt>
 * Static methods operate on a default uniform random number generator; they are synchronized.
 * <p>
 *
 * @author Hongbo Liu hongbol@winlab.rutgers.edu
 * @version 0.1, 26-Nov-99
 */
public class ParetoII extends AbstractContinousDistribution {

  protected double scale;
  protected double shape;
  protected double shift;
  protected double pwr;

  // The uniform random number generated shared by all <b>static</b> methods.
  protected static ParetoII shared = new ParetoII(1.0, 1.5, 1.0, makeDefaultGenerator());

  /**
   * Constructs a ParetoII distribution.
   */
  public ParetoII(double k, double alpha, double mu, RandomElement randomGenerator) {
    setRandomGenerator(randomGenerator);
    scale = k;
    shape = alpha;
    shift = mu;
    pwr = -1.0/alpha;
  }

  /**
   * Returns the cumulative distribution function.
   */
  public double cdf(double x) {
    if ((x+shift) <= scale)
      return 0.0;
    return 1.0 - Math.pow(scale/(x+shift), shape);
  }

  /**
   * Returns a random number from the distribution.
   */
  public double nextDouble() {
    return scale*Math.pow(randomGenerator.raw(), pwr) - shift;
  }

  /**
   * Returns a random number from the distribution; bypasses the internal
   state.*/
  public double nextDouble(double k, double alpha, double mu) {
    return k*Math.pow(randomGenerator.raw(), -1.0/alpha) - mu;
  }

  /**
   * Returns the probability distribution function.
   */
  public double pdf(double x) {
    if ((x + shift)< scale)
      return 0.0;
    return shape*Math.pow(scale, shape)*Math.pow(x+ shift, -(1.0 + shape));
  }

  /**
   * Sets the parameters.
   */
  public void setState(double k, double alpha, double mu) {
    this.scale = k;
    this.shape = alpha;
    this.shift = mu;
  }

  /**
   * Returns a random number from the distribution with the given
   * scale = k, and shape = alpha.
   */
  public static double staticNextDouble(double k, double alpha, double mu) {
    synchronized (shared) {
      return shared.nextDouble(k, alpha, mu);
    }
  }

  /**
   * Returns a String representation of the receiver.
   */
  public String toString() {
    return this.getClass().getName() + "(" + scale + ", " + shape
      + ", " + shift + ")";
  }

  /**
   * Sets the uniform random number generated shared by all <b>static</b> methods.
   * @param randomGenerator the new uniform random number generator to be shared.
   */
  private static void xstaticSetRandomGenerator(RandomElement randomGenerator) {
    synchronized (shared) {
      shared.setRandomGenerator(randomGenerator);
    }
  }
}

