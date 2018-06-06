package rbmwsimulator.mote;

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
public class Battery {
  private double availableEnergy;
  private double currentCumulativeEnergyDissipationRate; //Joules/unit simulation time (in seconds)

  public Battery(double availableEnergy_, double currentCumulativeEnergyDissipationRate_) {
    this.availableEnergy = availableEnergy_;
    this.currentCumulativeEnergyDissipationRate = currentCumulativeEnergyDissipationRate_;
  }

  public double energy() {
    return this.availableEnergy;
  }

  public void changeCurrentEnergyDissipationRate(double currentCumulativeEnergyDissipationRate_) {
    this.currentCumulativeEnergyDissipationRate = currentCumulativeEnergyDissipationRate_;
  }

  public boolean dissipate(double amount) {
    if(this.availableEnergy >= amount) {
      this.availableEnergy -= amount;
      return true;
    }
    return false;
  }

  public String toString() {
    return ("[Battery: "+new Double(availableEnergy).toString())+"J,"+(new Double(currentCumulativeEnergyDissipationRate).toString()+"J/s]");
  }
}
