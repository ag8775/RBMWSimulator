package rbmwsimulator.command;
import simulator.command.Command;
import rbmwsimulator.element.Node;
import rbmwsimulator.generators.SensingEvent;

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
 * SensingEventReadingUpdateCommand can be used to schedule a change in the
 * sensing readings associated with an event in the vicinity of a Node at any
 * time.
*/
public class SensingEventReadingUpdateCommand extends Command {
   private Node m_node;
   private SensingEvent sensing_event;
   private double event_sensor_reading;
   private long event_pos_x, event_pos_y;

   public SensingEventReadingUpdateCommand(double time, Node node, SensingEvent sensingEvent, long eventPosX, long eventPosY, double eventSensorReading) {
     super("SensingReadingUpdate",time);
     m_node=node;
     sensing_event = sensingEvent;
     event_sensor_reading = eventSensorReading;
     event_pos_x = eventPosX;
     event_pos_y = eventPosY;
   }

   public void execute() {
     m_node.setSensorReadingForEvent(sensing_event, event_pos_x, event_pos_y, event_sensor_reading);
   }
}
