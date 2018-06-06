package simulator.random;
import java.util.*;

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
public class DistributionInfo {
  private int distributionType;
  private Vector distributionParameters; // this is a Vector of strings

  public DistributionInfo() {
    this.distributionType = -1;
  }

  public DistributionInfo(int type, Vector params) {
    this.distributionType = type;
    this.distributionParameters = params;
  }

  public void setDistributionType(int type) {
    this.distributionType = type;
  }

  public void setDistributionParameters(Vector params) {
    this.distributionParameters = params;
  }

  public int getDistributionType() {
    return distributionType;
  }

  public Vector getDistributionParameters() {
    return this.distributionParameters;
  }
}
