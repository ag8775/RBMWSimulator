package cern.jet.random;

import edu.cornell.lassp.houle.RngPack.RandomElement;
import cern.jet.stat.Probability;
/**
 * Wald distribution; also called Inverse Gaussian distribution;
 * <p>
 * <tt>p(x) = sqrt(lambda/(2*pi*x^3)) * exp(-lambda*(x-mu)^2/(2*mu^2*x))</tt>
 * <p>
 * Valid parameter ranges: <tt>x,lambda &gt; 0</tt>.
 * <p>
 * Instance methods operate on a user supplied uniform random number generator; they are unsynchronized.
 * <dt>
 * Static methods operate on a default uniform random number generator; they are synchronized.
 * <p>
 * <b>Implementation:</b>
 * <dt>
 * Method: Ratio of Uniforms with shift.
 * <dt>
 * High performance implementation. This is a port of <A HREF="http://www.cs.
 * sun.ac.za/~lynette/simulation/harsham.html#rRandNumbGener">Random Number
 * Generators</A> by Professor Hossein Arsham.
 * This implementation, in turn, is based on Michael, et al. (1976) as refered
 * in <A HREF="http://www.cqs.washington.edu/papers/zabel/chp4.doc7.html#488046"
 * >Zabel's Dissertation</A>
 *
 * @author Hongbo Liu hongbol@winlab.rutgers.edu
 * @version 0.1
 */
public class Wald extends AbstractContinousDistribution {
        protected double mu;
        protected double lam;
        protected double ch2;
        // cached vars for method nextDouble(a) (for performance only)
        private double freedom_in = -1.0,b,vm,vp,vd;
        private Uniform unif;

        // The uniform random number generated shared by all <b>static</b> methods.
        protected static Wald shared = new Wald(1.0,1.0,makeDefaultGenerator());
/**
 * Constructs a Wald distribution.
 * Example: m = 1.0, l = 1.0.
 * @param m mean value.
 * @param l scale parameter.
 * @throws IllegalArgumentException if <tt>mu, lambda &lt; 0.0</tt>.
 */
public Wald(double m, double l, RandomElement randomGenerator) {
        unif = new Uniform(makeDefaultGenerator());
        setRandomGenerator(randomGenerator);
        setState(m,l);
}
/**
 * Returns the cumulative distribution function.
 */
public double cdf(double x,double y) {
  System.err.println("Warning: CDF of Wald distribution is not implemented");
  return 0;
}
/**
 * Returns a random number from the distribution.
 */
public double nextDouble() {
        return nextDouble(mu, lam);
}
/**
 * Returns a random number from the distribution; bypasses the internal state.
 * @param u mean parameter
 * @param lambda scale parameter
 * It should hold <tt>mu, lambda &gt; 0</tt>.
 */
public double nextDouble(double u, double lambda) {
    ch2 = nextCh2(1);
    double wald = u * (2.*lambda + u*ch2-
                       Math.sqrt(4.*lambda*u*ch2 + Math.pow(u*ch2,2)))/(2.*lambda);
    if( unif.nextDouble() > u/(u+wald)) wald = u*u/wald;
    return wald;
}
public double nextCh2(double freedom) {
/******************************************************************
 *                                                                *
 *        Chi Distribution - Ratio of Uniforms  with shift        *
 *                                                                *
 ******************************************************************
 *                                                                *
 * FUNCTION :   - chru samples a random number from the Chi       *
 *                distribution with parameter  a > 1.             *
 * REFERENCE :  - J.F. Monahan (1987): An algorithm for           *
 *                generating chi random variables, ACM Trans.     *
 *                Math. Software 13, 168-172.                     *
 * SUBPROGRAM : - anEngine  ... pointer to a (0,1)-Uniform        *
 *                engine                                          *
 *                                                                *
 * Implemented by R. Kremer, 1990                                 *
 ******************************************************************/

        double u,v,z,zz,r;

        //if( a < 1 )  return (-1.0); // Check for invalid input value

        if (freedom == 1.0) {
                for(;;) {
                        u = randomGenerator.raw();
                        v = randomGenerator.raw() * 0.857763884960707;
                        z = v / u;
                        if (z < 0) continue;
                        zz = z * z;
                        r = 2.5 - zz;
                        if (z < 0.0) r = r + zz * z / (3.0 * z);
                        if (u < r * 0.3894003915) return(z*z);
                        if (zz > (1.036961043 / u + 1.4)) continue;
                        if (2.0 * Math.log(u) < (- zz * 0.5 )) return(z*z);
                }
        }
        else {
                if (freedom != freedom_in) {
                        b = Math.sqrt(freedom - 1.0);
                        vm = - 0.6065306597 * (1.0 - 0.25 / (b * b + 1.0));
                        vm = (-b > vm) ? -b : vm;
                        vp = 0.6065306597 * (0.7071067812 + b) / (0.5 + b);
                        vd = vp - vm;
                        freedom_in = freedom;
                }
                for(;;) {
                        u = randomGenerator.raw();
                        v = randomGenerator.raw() * vd + vm;
                        z = v / u;
                        if (z < -b) continue;
                        zz = z * z;
                        r = 2.5 - zz;
                        if (z < 0.0) r = r + zz * z / (3.0 * (z + b));
                        if (u < r * 0.3894003915) return((z + b)*(z + b));
                        if (zz > (1.036961043 / u + 1.4)) continue;
                        if (2.0 * Math.log(u) < (Math.log(1.0 + z / b) * b * b - zz * 0.5 - z * b)) return((z + b)*(z + b));
                }
        }
}
/**
 * Returns the probability distribution function.
 */
public double pdf(double x) {
        if (x <= 0.0) throw new IllegalArgumentException();
        return Math.sqrt(lam/(2.0*Math.PI*Math.pow(x,3)))*
          Math.exp(-lam/(2.*x)*Math.pow((x-mu)/mu,2));
}
/**
 * Sets the distribution parameter.
 * @param u mean parameter^M
 * @param lambda scale parameter^M
 */
public void setState(double u, double lambda) {
  mu = u;
  lam = lambda;
}
/**
 * Returns a random number from the distribution.
 * @param u mean parameter^M
 * @param lambda scale parameter^M
 */
public static double staticNextDouble(double u, double lambda) {
        synchronized (shared) {
                return shared.nextDouble(u,lambda);
        }
}
/**
 * Returns a String representation of the receiver.
 */
public String toString() {
        return this.getClass().getName()+"("+mu+","+lam+")";
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
