package rbmwsimulator.mote;
import rbmwsimulator.protocol.Status;
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
public class Transceiver {
  private int radioMode; //Sleep, IDLE, RECEIVING, TRANSMITTING
  private double radioSwitchingDelay;
  private float radioBR; //in Kbps
  private int radioRange; //in meters

  public Transceiver(int mode_, double switchingDelay_, float radioBR_, int radioRange_) {
    this.radioMode = mode_;
    this.radioSwitchingDelay = switchingDelay_;
    this.radioBR = radioBR_;
    this.radioRange = radioRange_;
   }

  public int getRadioRange() {
    return this.radioRange;
  }

  public float getRadioBitRate() {
    return this.radioBR;
  }

  public double getRadioSwitchingDelay() {
    return this.radioSwitchingDelay;
  }

  public void switchToRxMode() {
    this.radioMode = Status.RADIO_RECEIVING;
  }

  public void switchToTxMode() {
    this.radioMode = Status.RADIO_TRANSMITTING;
  }

  public void switchToIdleMode() {
    this.radioMode = Status.RADIO_IDLE;
  }

  public void switchToSleepMode() {
    this.radioMode = Status.RADIO_SLEEP;
  }

  public boolean isTransmitting() {
    return this.radioMode == Status.RADIO_TRANSMITTING;
  }

  public boolean isReceiving() {
    return this.radioMode == Status.RADIO_RECEIVING;
  }

  public boolean isIdle() {
    return this.radioMode == Status.RADIO_IDLE;
  }

  public boolean isSleeping() {
    return this.radioMode == Status.RADIO_SLEEP;
  }

  public String toString() {
    return  "[Radio: "+Status.radio_status[this.radioMode]+", "+this.radioBR+"Kbps "+this.radioRange+"m ]";
  }

}
