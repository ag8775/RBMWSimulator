package rbmwsimulator.model;
import rbmwsimulator.model.Mica2EnergyParameters;
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

//Energy model that accounts for energy consumption per unit time per unit
//component functionality for the following components:
// (1) Radio
//    (a) Idle
//    (b) Reception
//    (c) Transmission
// (2) CPU
// (3) Memory
//     (a) Read
//     (b) Write
// (4) Sensor
//     (a) sampling (ADC)
// (5) Battery:
//     (a) Idle
//     (b) InUse
public class EnergyModel {
  double currentlyAvailableEnergy;

  public EnergyModel(double currentlyAvailableEnergy_) {
    this.currentlyAvailableEnergy = currentlyAvailableEnergy_;
  }

  public boolean isEnergyAvailable() {
    return this.currentlyAvailableEnergy == 0;
  }

  public double availableEnergy() {
    return this.currentlyAvailableEnergy;
  }

  private void zerofyEnergy() {
    if(this.currentlyAvailableEnergy <= 0)
      this.currentlyAvailableEnergy = (double)0.0;
  }

  public void accountForRadioIdleState(double timeInterval_) {
    this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_CPU_IDLE_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForRadioReceptionState(double timeInterval_) {
    this.currentlyAvailableEnergy -=  timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_RX_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForRadioTransmission(long packetSizeInBits_, int radioStrengthLevel_) {
    double timeInterval_ = packetSizeInBits_ * Mica2EnergyParameters.DEFAULT_RADIO_TX_TIMING_PER_BIT;
    switch(radioStrengthLevel_) {
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_00:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_00_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_01:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_01_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_03:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_03_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_06:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_06_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_09:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_09_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_0F:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_0F_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_60:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_60_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_80:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_80_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_C0:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_C0_CURRENT_DRAW;
        break;
      case Mica2EnergyParameters.RADIO_TX_STRENGTH_FF:
        this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_RADIO_TX_STRENGTH_FF_CURRENT_DRAW;
        break;
      default:
        break;
    }
    zerofyEnergy();
  }

  public void accountForCPUActive(int numInstructions_, long avgCyclesPerInstruction_) {
    double timeInterval_ =  numInstructions_*avgCyclesPerInstruction_*Mica2EnergyParameters.DEFAULT_CPU_CLOCK_CYCLE_TIME;
    this.currentlyAvailableEnergy -= timeInterval_*Mica2EnergyParameters.DEFAULT_CPU_ACTIVE_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForCPUIdle(double idleTime_) {
    this.currentlyAvailableEnergy -= idleTime_ * Mica2EnergyParameters.DEFAULT_CPU_IDLE_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForCPUPowerDown(double downTime_) {
    this.currentlyAvailableEnergy -=  downTime_* Mica2EnergyParameters.DEFAULT_CPU_POWER_DOWN_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForCPUPowerSave(double powerSaveTime_) {
    this.currentlyAvailableEnergy -= powerSaveTime_ * Mica2EnergyParameters.DEFAULT_CPU_POWER_SAVE_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForCPUStandBy(double standByTime_) {
    this.currentlyAvailableEnergy -= standByTime_ * Mica2EnergyParameters.DEFAULT_CPU_STAND_BY_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForCPUExtendedStandBy(double standByTime_) {
    this.currentlyAvailableEnergy -= standByTime_ * Mica2EnergyParameters.DEFAULT_CPU_EXTENDED_STAND_BY_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForCPUInternalClockOscillator(double cpuUpTime_) {
    this.currentlyAvailableEnergy -= cpuUpTime_ * Mica2EnergyParameters.DEFAULT_CPU_INTERNAL_OSCILLATOR_CLOCK_CURRENT_DRAW;
    zerofyEnergy();
  }

  public void accountForMemoryRead(double readTime_) {
    this.currentlyAvailableEnergy -= readTime_ * Mica2EnergyParameters.DEFAULT_EEPROM_READ_CURRENT_DRAW;
  }

  public void accountForMemoryWrite(double writeTime_) {

  }

  public void accountForSensorSampling(double samplingTime_) {

  }

}
