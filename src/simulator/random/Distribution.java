package simulator.random;
import java.util.Random;
import simulator.random.*;
import edu.cornell.lassp.houle.RngPack.*;
import cern.jet.random.engine.*;
import java.util.Vector;
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
public final class Distribution {

   public static DistributionInfo getDistributionInfoForJavaRandom(Random r) {
     Vector params = new Vector();
     params.add(r);
     DistributionInfo distInfo = new DistributionInfo(DistributionTypes.JAVA_RANDOM, params);
     return distInfo;
   }

   public static DistributionInfo getDistributionInfoForUniformRandom(Random r) {
     Vector params = new Vector();
     params.add(r);
     DistributionInfo distInfo = new DistributionInfo(DistributionTypes.UNIFORM_RANDOM, params);
     return distInfo;
   }

   public static DistributionInfo getDistributionInfoForUniformRandomWithinInterval(Random r, int low, int high) {
     Vector params = new Vector();
     params.add(r);
     params.add(new Integer(low));
      params.add(new Integer(high));
     DistributionInfo distInfo = new DistributionInfo(DistributionTypes.UNIFORM_RANDOM_WITHIN_INTERVAL, params);
     return distInfo;
   }

   public static DistributionInfo getDistributionInfoForExponentialRandom(Random r, double lambda) {
     Vector params = new Vector();
     params.add(r);
     params.add(new Double(lambda));
     DistributionInfo distInfo = new DistributionInfo(DistributionTypes.EXPONENTIAL_RANDOM, params);
     return distInfo;
   }

   public static DistributionInfo getDistributionInfoForGaussianRandom(Random r, double mean, double std) {
     Vector params = new Vector();
     params.add(r);
     params.add(new Double(mean));
     params.add(new Double(std));
     DistributionInfo distInfo = new DistributionInfo(DistributionTypes.GAUSSIAN_RANDOM, params);
     return distInfo;
   }

   public static DistributionInfo getDistributionInfoForParetoRandom(Random r, double K, double P, double alpha) {
     Vector params = new Vector();
     params.add(r);
     params.add(new Double(K));
     params.add(new Double(P));
     params.add(new Double(alpha));
     DistributionInfo distInfo = new DistributionInfo(DistributionTypes.PARETO_RANDOM, params);
     return distInfo;
   }

   public static DistributionInfo getDistributionInfoForParetoRandomWithShape(Random r, double scale, double shape) {
     Vector params = new Vector();
     params.add(r);
     params.add(new Double(scale));
     params.add(new Double(shape));
     DistributionInfo distInfo = new DistributionInfo(DistributionTypes.PARETO_RANDOM_WITH_SHAPE, params);
     return distInfo;
   }

   public static DistributionInfo getDistributionInfoForRanmarFlat(long seed) {
     Vector params = new Vector();
     RandomElement e = new Ranmar(seed);
     params.add(e);
     DistributionInfo distInfo = new DistributionInfo(DistributionTypes.RANMAR_FLAT, params);
     return distInfo;
   }

   public static DistributionInfo getDistributionInfoForRanmarGaussian(long seed) {
       Vector params = new Vector();
       RandomElement e = new Ranmar(seed);
       params.add(e);
       DistributionInfo distInfo = new DistributionInfo(DistributionTypes.RANECU_GAUSSIAN, params);
       return distInfo;
   }

   public static DistributionInfo getDistributionInfoForRanecuFlat(long seed) {
       Vector params = new Vector();
       RandomElement e = new Ranecu(seed);
       params.add(e);
       DistributionInfo distInfo = new DistributionInfo(DistributionTypes.RANECU_FLAT, params);
       return distInfo;
   }

   public static DistributionInfo getDistributionInfoForRanecuGaussian(long seed) {
    Vector params = new Vector();
    RandomElement e = new Ranecu(seed);
    params.add(e);
    DistributionInfo distInfo = new DistributionInfo(DistributionTypes.RANECU_GAUSSIAN, params);
    return distInfo;
  }

  public static DistributionInfo getDistributionInfoForRanluxFlat(long seed, int luxury) {
    Vector params = new Vector();
    RandomElement e = new Ranlux(luxury, seed);
    params.add(e);
    DistributionInfo distInfo = new DistributionInfo(DistributionTypes.RANLUX_FLAT, params);
    return distInfo;
  }

  public static DistributionInfo getDistributionInfoForRanluxGaussian(long seed, int luxury) {
      Vector params = new Vector();
      RandomElement e = new Ranlux(luxury, seed);
      params.add(e);
      DistributionInfo distInfo = new DistributionInfo(DistributionTypes.RANLUX_GAUSSIAN, params);
      return distInfo;
   }

   public static DistributionInfo getDistributionInfoForZipfRandom(Random r) {
      Vector params = new Vector();
      params.add(r);
      DistributionInfo distInfo = new DistributionInfo(DistributionTypes.ZIPF_RANDOM, params);
      return distInfo;
   }

   public static DistributionInfo getDistributionInfoForMersenneTwister(int seed) {
      Vector params = new Vector();
      RandomElement e = new MersenneTwister(seed);
      params.add(e);
      DistributionInfo distInfo = new DistributionInfo(DistributionTypes.MERSENNE_TWISTER, params);
      return distInfo;
   }


    public static double getUniformRandom(Random r) {
        return r.nextDouble();
    }

    /*returns a rand between low (exclusive) and high (inclusive)*/
    public static int getUniformRandom(Random r, int low, int high) {
        int n=0;
        while (n==0)
            n = r.nextInt(high); /*this gives me a number between 0 and high, inclusive*/
        if((n+low) > high)
          return n;
        return n+low; /*shift*/
    }

     /*returns a rand between low (exclusive) and high (inclusive)*/
    public static double getUniformRandom(Random r, double low, double high) {
        return low+(r.nextDouble()*high);
    }

    //-------------------------------- -----------------------
    public static double getGaussianRandom(Random r, double mean, double std) {
        return ( r.nextGaussian()*std+mean);
    }

    /**
     * This distribution is known as Bounded Pareto. It is characterized by 3
     * parameters: alpha, the exponent of the power law;
     *             k, the smallest possible observation; and
     *             p, the largest possible observation.
     * The probability mass function for the Bounded Pareto B(k; p; alpha)
     * is defined as:
     *                f(x) = ((alpha * (k^alpha))/(1 - ((k/p)^alpha)))*(x^(-alpha-1))
     * where k<= x <= p
     *   See paper "Task Assignment with Unknown Duration"
     * @param r Random
     * @param K double
     * @param P double
     * @param ALPHA double
     * @return double
     */
    public static double getParetoRandom (Random r, double K, double P, double ALPHA) {

        double x = r.nextDouble();
        double    den =Math.pow(1.0-x+x*Math.pow(K/P, ALPHA), 1/ALPHA);
        while (den==0) {
            x = r.nextDouble();
            den =Math.pow(1.0-x+x*Math.pow(K/P, ALPHA), 1/ALPHA);
        }
        return (K/den);
    }

    public static double getParetoRandom (Random r, double scale, double shape) {

        double x = r.nextDouble();
        double    den =Math.pow(1.0-x+x*Math.pow(1.0/scale, shape), 1/shape);
        while (den==0) {
            x = r.nextDouble();
            den =Math.pow(1.0-x+x*Math.pow(1.0/scale, shape), 1/shape);
        }
        return (1/den);

    }

    public static double  getExponentialRandom(Random r, double lambda) {
      double u = getUniformRandom(r);
      return -(1.0/lambda)*Math.log(u);
    };

    public static double getRanmarFlat (RandomElement e) {
      return e.raw();
    }

    public static double getRanmarGaussian (RandomElement e) {
      return e.gaussian();
    }

    public static double getRanecuFlat (RandomElement e) {
      return e.raw();
    }

    public static double getRanecuGaussian (RandomElement e) {
      return e.gaussian();
    }

    public static double getRanluxFlat (RandomElement e) {
      return e.raw();
    }

    public static double getRanluxGaussian (RandomElement e) {
      return e.gaussian();
    }

    public static double getRandomJava(RandomElement e) {
      return e.raw();
    }

    /** Returns a double value with a positive sign,
     * greater than or equal to 0.0 and less than 1.0.
     * The probability distribution is Zipf’s law,
     * i.e. pdf is f(x)~1/x.
     * */
    public static double getZipfRandom(Random r) {
      return Math.exp(r.nextDouble() - 1);
    }

    public static double getRandomNumber(DistributionInfo distInfo) {
      double rn = -1;
      int distributionType = distInfo.getDistributionType();
      Vector distributionParams = distInfo.getDistributionParameters();
      Random r;
      RandomElement e;

      switch(distributionType) {
        case DistributionTypes.JAVA_RANDOM:
          r = (Random)(distributionParams.elementAt(0));
          rn = r.nextDouble();
          break;

        case DistributionTypes.UNIFORM_RANDOM:
          r = (Random)(distributionParams.elementAt(0));
          rn = r.nextDouble();
          break;

        case DistributionTypes.UNIFORM_RANDOM_WITHIN_INTERVAL:
          r = (Random)(distributionParams.elementAt(0));
          int low = ((Integer)(distributionParams.elementAt(1))).intValue();
          int high = ((Integer)(distributionParams.elementAt(2))).intValue();
          rn = getUniformRandom(r, low, high);
          break;

        case DistributionTypes.EXPONENTIAL_RANDOM:
          r = (Random)(distributionParams.elementAt(0));
          double lambda = ((Double)(distributionParams.elementAt(1))).doubleValue();
           rn = getExponentialRandom(r, lambda);
          break;

        case DistributionTypes.GAUSSIAN_RANDOM:
          r = (Random)(distributionParams.elementAt(0));
          double mean = ((Double)(distributionParams.elementAt(1))).doubleValue();
          double std = ((Double)(distributionParams.elementAt(2))).doubleValue();
          rn = getGaussianRandom(r, mean, std);
          break;

        case DistributionTypes.PARETO_RANDOM:
          r = (Random)(distributionParams.elementAt(0));
          double K = ((Double)(distributionParams.elementAt(1))).doubleValue();
          double P = ((Double)(distributionParams.elementAt(2))).doubleValue();
          double ALPHA = ((Double)(distributionParams.elementAt(3))).doubleValue();
          rn = getParetoRandom (r, K, P, ALPHA);
          break;

        case DistributionTypes.PARETO_RANDOM_WITH_SHAPE:
          r = (Random)(distributionParams.elementAt(0));
          double scale = ((Double)(distributionParams.elementAt(1))).doubleValue();
          double shape = ((Double)(distributionParams.elementAt(2))).doubleValue();
          rn = getParetoRandom (r, scale, shape);
          break;

        case DistributionTypes.RANMAR_FLAT:
          e = (RandomElement)distributionParams.elementAt(0);
          rn =  getRanmarFlat(e);
          break;

        case DistributionTypes.RANMAR_GAUSSIAN:
          e = (RandomElement)distributionParams.elementAt(0);
          rn =  getRanmarGaussian(e);
          break;

        case DistributionTypes.RANECU_FLAT:
          e = (RandomElement)distributionParams.elementAt(0);
          rn =  getRanecuFlat(e);
          break;

        case DistributionTypes.RANECU_GAUSSIAN:
          e = (RandomElement)distributionParams.elementAt(0);
          rn =  getRanecuGaussian(e);
          break;

        case DistributionTypes.RANLUX_FLAT:
          e = (RandomElement)distributionParams.elementAt(0);
          rn =  getRanluxFlat(e);
          break;

        case DistributionTypes.RANLUX_GAUSSIAN:
          e = (RandomElement)distributionParams.elementAt(0);
          rn =  getRanluxGaussian(e);
          break;

        case DistributionTypes.RANJAVA:
          e = (RandomElement)distributionParams.elementAt(0);
          rn =  getRandomJava(e);
          break;

        case DistributionTypes.MERSENNE_TWISTER:
          e = (RandomElement)distributionParams.elementAt(0);
          rn =  getRandomJava(e);
          break;

        case DistributionTypes.ZIPF_RANDOM:
          r = (Random)(distributionParams.elementAt(0));
          rn = getZipfRandom(r);
          break;
      }
    return rn;
  }
}
