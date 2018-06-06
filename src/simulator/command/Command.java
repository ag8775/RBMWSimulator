package simulator.command;
import simulator.util.Output;
import rbmwsimulator.util.Preferences;
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
/**
   Command is a very important class in the Simulator because it represents the commands
   that the simulator is supposed to execute at a specific time.<br>
   You have to subclass command to make use of it. You can then use it to
   schedule a call to your class' foobar() function after x seconds.
*/
public abstract class Command {

    String m_name;         // Name of the command (be artistic)
    double m_time;         // The time at which the command should be executed

    /**
       Create a new command class with a specific name to execute at some
       specific time.
       @param name name of the command
       @param time the time at which to execute the command
    */
    public Command(String name,double time) {
      m_name=name;
      m_time=time;
    }


    /**
       Return the command's execution time.
    */
    public double getTime() {
      return m_time;
    }

    /**
       Return the command's name.
    */
    public String getName() {
      return m_name;
    }

    /**
       Debugging output, print's the command's name.
    */
    public void dump() {
      Output.SIMINFO(this.toString()+", "+m_name+": "+m_time, Preferences.PRINT_COMMAND_DETAILS);
    }

    /**
       The execution function. Has to be overridden by subclasses.
     */
    public abstract void execute();

}
