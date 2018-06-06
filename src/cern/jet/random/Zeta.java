/*
Copyright � 1999 CERN - European Organization for Nuclear Research.
Permission to use, copy, modify, distribute and sell this software and its documentation for any purpose
is hereby granted without fee, provided that the above copyright notice appear in all copies and
that both that copyright notice and this permission notice appear in supporting documentation.
CERN makes no representations about the suitability of this software for any purpose.
It is provided "as is" without expressed or implied warranty.
*/
package cern.jet.random;

import edu.cornell.lassp.houle.RngPack.RandomElement;
/**
 * Zeta distribution.
 * <p>
 * Valid parameter ranges: <tt>ro &gt; 0</tt> and <tt>pk &gt;= 0</tt>.
 * <dt>
 * If either <tt>ro &gt; 100</tt>  or  <tt>k &gt; 10000</tt> numerical problems in
 * computing the theoretical moments arise, therefore <tt>ro &lt;= 100</tt> and
 * <tt>k &lt;= 10000</tt> are recommended.
 * <p>
 * Instance methods operate on a user supplied uniform random number generator; they are unsynchronized.
 * <dt>
 * Static methods operate on a default uniform random number generator; they are synchronized.
 * <p>
 * <b>Implementation:</b>
 * <dt>Method: Acceptance/Rejection.
 * High performance implementation.
 * <dt>This is a port and adaption of <tt>Zeta.c</tt> from the <A HREF="http://www.cis.tu-graz.ac.at/stat/stadl/random.html">C-RAND / WIN-RAND</A> library.
 * C-RAND's implementation, in turn, is based upon
 * <p>
 * J. Dagpunar (1988): Principles of Random Variate  Generation, Clarendon Press, Oxford.
 *
 * @author wolfgang.hoschek@cern.ch
 * @version 1.0, 09/24/99
 */
public class Zeta extends AbstractDiscreteDistribution {
        protected double ro;
        protected double pk;

        // cached values (for performance)
        protected double c,d,ro_prev = -1.0,pk_prev = -1.0;
        protected double maxlongint = Long.MAX_VALUE - 1.5;

        // The uniform random number generated shared by all <b>static</b> methods.
        protected static Zeta shared = new Zeta(1.0,1.0,makeDefaultGenerator());
/**
 * Constructs a Zeta distribution.
 */
public Zeta(double ro, double pk, RandomElement randomGenerator) {
        setRandomGenerator(randomGenerator);
        setState(ro,pk);
}
/**
 * Returns a zeta distributed random number.
 */
protected long generateZeta(double ro, double pk, RandomElement randomGenerator) {
/******************************************************************
 *                                                                *
 *            Zeta Distribution - Acceptance Rejection            *
 *                                                                *
 ******************************************************************
 *                                                                *
 * To sample from the Zeta distribution with parameters ro and pk *
 * it suffices to sample variates x from the distribution with    *
 * density function  f(x)=B*{[x+0.5]+pk}^(-(1+ro)) ( x > .5 )     *
 * and then deliver k=[x+0.5].                                    *
 * 1/B=Sum[(j+pk)^-(ro+1)]  (j=1,2,...) converges for ro >= .5 .  *
 * It is not necessary to compute B, because variates x are       *
 * generated by acceptance rejection using density function       *
 * g(x)=ro*(c+0.5)^ro*(c+x)^-(ro+1).                              *
 *                                                                *                                                                *
 * Integer overflow is possible, when ro is small (ro <= .5) and  *
 * pk large. In this case a new sample is generated. If ro and pk *
 * satisfy the inequality   ro > .14 + pk*1.85e-8 + .02*ln(pk)    *
 * the percentage of overflow is less than 1%, so that the        *
 * result is reliable.                                            *
 * NOTE: The comment above is likely to be nomore valid since     *
 * the C-version operated on 32-bit integers, while this Java     *
 * version operates on 64-bit integers. However, the following is *
 * still valid:                                                   *                                                                *
 *                                                                *                                                                *
 * If either ro > 100  or  k > 10000 numerical problems in        *
 * computing the theoretical moments arise, therefore ro<=100 and *
 * k<=10000 are recommended.                                      *
 *                                                                *
 ******************************************************************
 *                                                                *
 * FUNCTION:    - zeta  samples a random number from the          *
 *                Zeta distribution with parameters  ro > 0  and  *
 *                pk >= 0.                                        *
 * REFERENCE:   - J. Dagpunar (1988): Principles of Random        *
 *                Variate  Generation, Clarendon Press, Oxford.   *
 *                                                                *
 ******************************************************************/
        double u,v,e,x;
        long k;

        if (ro != ro_prev || pk != pk_prev) {                   // Set-up
                ro_prev = ro;
                pk_prev = pk;
                if (ro<pk) {
                        c = pk-0.5;
                        d = 0;
                }
                else {
                        c = ro-0.5;
                        d = (1.0+ro)*Math.log((1.0+pk)/(1.0+ro));
                }
        }
        do {
                do {
                        u=randomGenerator.raw();
                        v=randomGenerator.raw();
                        x = (c+0.5)*Math.exp(-Math.log(u)/ro) - c;
                } while (x<=0.5 || x>=maxlongint);

                k = (int) (x+0.5);
                e = -Math.log(v);
        } while ( e < (1.0+ro)*Math.log((k+pk)/(x+c)) - d );

        return k;
}
/**
 * Returns a random number from the distribution.
 */
public int nextInt() {
        return (int) generateZeta(ro, pk, randomGenerator);
}
/**
 * Sets the parameters.
 */
public void setState(double ro, double pk) {
        this.ro = ro;
        this.pk = pk;
}
/**
 * Returns a random number from the distribution.
 */
public static int staticNextInt(double ro, double pk) {
        synchronized (shared) {
                shared.setState(ro,pk);
                return shared.nextInt();
        }
}
/**
 * Returns a String representation of the receiver.
 */
public String toString() {
        return this.getClass().getName()+"("+ro+","+pk+")";
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
