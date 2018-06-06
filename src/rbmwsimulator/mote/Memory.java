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
public class Memory {
  private int availableStorageCapacity;
  public Memory(int storageCapacity_) {
    this.availableStorageCapacity = storageCapacity_;
  }

  public int memory() {
    return this.availableStorageCapacity;
  }

  public boolean allocateMemory(int memoryRequest) {
    if(this.availableStorageCapacity > memoryRequest) {
      this.availableStorageCapacity -= memoryRequest;
      return true;
    }
    return false;
  }

  public void deallocateMemory(int memoryRequest) {
    this.availableStorageCapacity += memoryRequest;
  }

  public String toString() {
    return "[Memory: "+this.availableStorageCapacity+"MB]";
  }
}


