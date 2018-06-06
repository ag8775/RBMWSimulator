package rbmwsimulator.model;

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
 * UCB Mica2 Mote Power Benchmark Summary Numbers
 * URL: http://www.eecs.harvard.edu/~shnayder/ptossim/mica2bench/summary.html
 *
 * [Radio transmission of a packet with 4 byte payload at maximum tx power]
 *
 * Table of Results
 *  Mode 	Current
 *   +CPU
 *    -Active 	8.0 mA
 *    -Idle 	3.2 mA
 *    -ADC Noise Reduction 	1.0 mA
 *    -Power-down 	103 µA
 *    -Power-save 	110 µA
 *    -Standby 	216 µA
 *    -Extended Standby 	223 µA
 *    -Internal Oscillator 	0.93 mA
 *  +Leds 	2.2 mA/led
 *  +Mica2 sensorboard 	0.7 mA
 *  +EEPROM
 *    -Read 	6.2 mA
 *    -Read time 	565 µs
 *    -Write 	18.4 mA
 *    -Write time 	12.9 ms
 *  +Radio
 *    -Rx 	7.03 mA
 *    -Tx (power = 00) 	3.72 mA
 *    -Tx (power = 01) 	5.21 mA
 *    -Tx (power = 03) 	5.37 mA
 *    -Tx (power = 06) 	6.47 mA
 *    -Tx (power = 09) 	7.05 mA
 *    -Tx (power = 0F) 	8.47 mA
 *    -Tx (power = 60) 	11.57 mA
 *    -Tx (power = 80) 	13.77 mA
 *    -Tx (power = C0) 	17.37 mA
 *    -Tx (power = FF) 	21.48 mA
 *  +Radio Timing Data
 *    -TX time/bit 	62.4 µs/bit
 *    -Average CSMA time 	40.95 ms
 *    -STDV CSMA time 	13.50 ms
 *
 * Notes:
 *   + The power supply voltage used was 3 V, other power supply settings will yield similar current draw but might be off a few percent.
 *   + Different CPU modes were tested using a modified version of snooze code. This does not reflect the possible power savings using HPLPowerManagement, which could not be configured to test all CPU modes.
 *   + To test the CPU power levels Digital Multimeters were used because they more accurately measure the currents.
 *   + We found that on average the CPU uses 8 mA per instruction, therefore the total power is only dependent on the cycles per instruction. More extensive per instruction testing will have to be undertaken to build a more detailed power model.
 *   + Not all radio levels were tested
 *   + Often it was necessary to synchronize code execution with data collection in this case the TOS_SET_INT1_PIN() command was used to fire an interrupt pin which was sampled on a separate oscilloscope channel.
 */

public class Mica2EnergyParameters {
    //CPU Currents
    public static final double DEFAULT_CPU_ACTIVE_CURRENT_DRAW = 8.0e-3; //8mA per instruction; total power is dependent on cycles/instruction
    public static final double DEFAULT_CPU_IDLE_CURRENT_DRAW = 3.2e-3; //3.2mA
    public static final double DEFAULT_CPU_ADC_NOISE_REDUCTION_CURRENT_DRAW = 1.0e-3;//1mA
    public static final double DEFAULT_CPU_POWER_DOWN_CURRENT_DRAW = 103.0e-6;//103uA
    public static final double DEFAULT_CPU_POWER_SAVE_CURRENT_DRAW = 110.0e-6;//110uA
    public static final double DEFAULT_CPU_STAND_BY_CURRENT_DRAW = 216.0e-6;//216uA
    public static final double DEFAULT_CPU_EXTENDED_STAND_BY_CURRENT_DRAW = 223.0e-6;//223uA
    public static final double DEFAULT_CPU_INTERNAL_OSCILLATOR_CLOCK_CURRENT_DRAW = 0.93e-3;//0.93mA
    public static final double DEFAULT_CPU_CLOCK_RATE = 7.3827e+06;//7.3827Mhz
    public static final double DEFAULT_CPU_CLOCK_CYCLE_TIME = 1/DEFAULT_CPU_CLOCK_RATE;

    //Leds Currents (per Led)
    public static final double DEFAULT_LEDS_CURRENT_DRAW = 2.2e-3;//2.2mA

    //Mica2 SensorBoard
    public static final double DEFAULT_MICA2_SENSORBOARD_CURRENT_DRAW = 0.7e-03;//0.7 mA

    //Memory Read/Write
    public static final double DEFAULT_EEPROM_READ_CURRENT_DRAW = 6.2e-03;//6.2mA
    public static final double DEFAULT_EEPROM_READ_TIME = 565e-06;//565 µs
    public static final double DEFAULT_EEPROM_WRITE_CURRENT_DRAW = 18.4e-03;//18.4 mA
    public static final double DEFAULT_EEPROM_WRITE_TIME = 12.9e-03;//12.9 ms

    //Radio Reception
    public static final double DEFAULT_RADIO_RX_CURRENT_DRAW = 7.03e-03;//7.03mA

    //Radio Modes
    public static final int RADIO_TX_STRENGTH_00 = 0x00;
    public static final int RADIO_TX_STRENGTH_01 = 0x01;
    public static final int RADIO_TX_STRENGTH_03 = 0x03;
    public static final int RADIO_TX_STRENGTH_06 = 0x06;
    public static final int RADIO_TX_STRENGTH_09 = 0x09;
    public static final int RADIO_TX_STRENGTH_0F = 0x0F;
    public static final int RADIO_TX_STRENGTH_60 = 0x60;
    public static final int RADIO_TX_STRENGTH_80 = 0x80;
    public static final int RADIO_TX_STRENGTH_C0 = 0xC0;
    public static final int RADIO_TX_STRENGTH_FF = 0xFF;

    //Radio Transmission
    public static final double DEFAULT_RADIO_TX_STRENGTH_00_CURRENT_DRAW = 3.72e-03;//3.72 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_01_CURRENT_DRAW = 5.21e-03;//5.21 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_03_CURRENT_DRAW = 5.37e-03;//5.37 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_06_CURRENT_DRAW = 6.47e-03;//6.47 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_09_CURRENT_DRAW = 7.05e-03;//7.05 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_0F_CURRENT_DRAW = 8.47e-03;//8.47 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_60_CURRENT_DRAW = 11.57e-03;//11.57 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_80_CURRENT_DRAW = 13.77e-03;//13.77 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_C0_CURRENT_DRAW = 17.37e-03;//17.37 mA
    public static final double DEFAULT_RADIO_TX_STRENGTH_FF_CURRENT_DRAW = 21.48e-03;//21.48 mA

    //Radio Timing
    public static final double DEFAULT_RADIO_TX_TIMING_PER_BIT = 62.4e-06;//62.4 µs/bit
    public static final double DEFAULT_RADIO_AVERAGE_CSMA_TIME = 40.95e-03;//40.95 ms
    public static final double DEFAULT_RADIO_STANDARD_DEVIATION_CSMA_TIME = 13.50e-03;//13.50 ms
}
