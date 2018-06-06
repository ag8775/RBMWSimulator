
package cern.jet.random;

import edu.cornell.lassp.houle.RngPack.RandomElement;
/**
 * Pareto Distribution. See the
 * <A HREF="http://www.treasure-troves.com/math/ParetoDistribution.html"> math definition</A>
 * <A HREF="http://www.statsoft.com/textbook/glosp.html#Pareto Distribution"> animated definition</A>.
 * <p>
 * <tt>p(x) = shape*scale^shape*x^{-(1+shape)}</tt> for <tt>x &gt;= scale</tt>, <tt>shape &gt; 0</tt>.
 * <p>
 * The mean is infinite if <tt>shape <= 1</tt> and variance is infinite if
 * <tt>shape <= 2</tt>.
 * <p>
 * Instance methods operate on a user supplied uniform random number generator; they are unsynchronized.
 * <dt>
 * Static methods operate on a default uniform random number generator; they are synchronized.
 * <p>
 *
 * @author Andy Ogielski ato@renesys.com
 * @version 0.1, 26-Nov-99
 */
public class Pareto extends AbstractContinousDistribution {

  protected double scale;
  protected double shape;
  protected double pwr;

  // The uniform random number generated shared by all <b>static</b> methods.
  protected static Pareto shared = new Pareto(1.0, 1.5, makeDefaultGenerator());

  /**
   * Constructs a Pareto distribution.
   */
  public Pareto(double k, double alpha, RandomElement randomGenerator) {
    setRandomGenerator(randomGenerator);
    scale = k;
    shape = alpha;
    pwr = -1.0/alpha;
  }

  /**
   * Returns the cumulative distribution function.
   */
  public double cdf(double x) {
    if (x <= scale)
      return 0.0;
    return 1.0 - Math.pow(scale/x, shape);
  }

  /**
   * Returns a random number from the distribution.
   */
  public double nextDouble() {
    return scale*Math.pow(randomGenerator.raw(), pwr);
  }

  /**
   * Returns a random number from the distribution; bypasses the internal state.
   */
  public double nextDouble(double k, double alpha) {
    return k*Math.pow(randomGenerator.raw(), -1.0/alpha);
  }

  /**
   * Returns the probability distribution function.
   */
  public double pdf(double x) {
    if (x < scale)
      return 0.0;
    return shape*Math.pow(scale, shape)*Math.pow(x, -(1.0 + shape));
  }

  /**
   * Sets the parameters.
   */
  public void setState(double k, double alpha) {
    this.scale = k;
    this.shape = alpha;
  }

  /**
   * Returns a random number from the distribution with the given
   * scale = k, and shape = alpha.
   */
  public static double staticNextDouble(double k, double alpha) {
    synchronized (shared) {
      return shared.nextDouble(k, alpha);
    }
  }

  /**
   * Returns a String representation of the receiver.
   */
  public String toString() {
    return this.getClass().getName() + "(" + scale + ", " + shape + ")";
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

