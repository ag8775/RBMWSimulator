package rbmwsimulator.model;
import simulator.util.Output;
import rbmwsimulator.generators.MobilityStatus;

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
public abstract class MobilityModel {
   private String m_name;         // Name of the mobility (be artistic)
   private int m_type; //See rbmwsimulator.util.Preferences

   public MobilityModel(String m_name_, int m_type_) {
     this.m_name = m_name_;
     this.m_type = m_type_;
   }

   public String getName() {
     return  this.m_name;
   }

   public int getType() {
     return this.m_type;
   }

   /**
    * Debugging output, print's the mobility model's name.
    */
   public void dump() {
     //Output.SIMINFO(m_name+": "+m_time);
   }

   /**
    * Initialize function. Has to be overridden by subclasses.
    * @param currentMobilityStatus MobilityStatus
    */
   public abstract void initialize(MobilityStatus currentMobilityStatus);

   /**
    * The directMobility function. Has to be overridden by subclasses.
    */
   public abstract boolean directMobility(MobilityStatus currentMobilityStatus, long currentTime);

}
