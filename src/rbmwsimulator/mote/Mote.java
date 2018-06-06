package rbmwsimulator.mote;

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
public class Mote {
   public Battery battery;
   public Memory memory;
   public Processor processor;
   public Sensor sensors[];
   public int numSensors;
   public Transceiver radio;

   public Mote(int numSensors_) {
     //Using Default Constructor ...
     this.numSensors = numSensors_;
     this.sensors = new Sensor[this.numSensors];
   }

   public void configureBattery(double availableEnergy_, double currentCumulativeEnergyDissipationRate_) {
     this.battery = new Battery(availableEnergy_, currentCumulativeEnergyDissipationRate_);
   }

   public void configureMemory(int storageCapacity_) {
     this.memory = new Memory(storageCapacity_);
   }

   public void configureProcessor(int cpuSpeed_) {
     this.processor = new Processor(cpuSpeed_);
   }

   public void configureTransceiver(int mode_, double switchingDelay_, float radioBR_, int radioRange_) {
     this.radio = new Transceiver(mode_, switchingDelay_, radioBR_, radioRange_);
   }

   public void configureSensor(int sensorIndex, int sensorType_, int sensingRange_, long seed_, int luxury_) {
     if(this.numSensors > sensorIndex) { //indexed from 0 to (this.numSensors - 1)
       sensors[sensorIndex] = new Sensor(sensorType_, seed_, luxury_);
       sensors[sensorIndex].setSensingRange(sensingRange_);
     }
   }

   public String toString() {
     String str = new String(battery.toString()+", "+memory.toString()+", "+processor.toString()+", "+radio.toString()+", ["+numSensors+": ");
     for(int i = 0; i < this.numSensors; i++) {
       str += " "+this.sensors[i].toString();
     }
     str+="]";
     return str;

   }

}
