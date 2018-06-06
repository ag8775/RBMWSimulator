package rbmwsimulator.gui;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.*;
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
public class SimulationConfigurationFrame extends JFrame {
  public SimulationConfigurationFrame() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(1000, 500));
    this.setTitle("Simulation Configuration");

    // PRNG Panel
    jPanelPRNG.setBorder(titledBorder1);
    jPanelPRNG.setMinimumSize(new Dimension(515, 40));
    jPanelPRNG.setPreferredSize(new Dimension(1000, 62));
    jLabelSeed.setText("Seed");
    jTextFieldSeed.setPreferredSize(new Dimension(40, 23));
    jTextFieldSeed.setText("");
    jTextFieldSeed.setColumns(5);
    jLabelLuxury.setToolTipText("");
    jLabelLuxury.setText("Luxury");
    jLabelDistribution.setText("Distribution");
    jLabelGenerator.setToolTipText("");
    jLabelGenerator.setText("Random Generator");

    contentPane.setPreferredSize(new Dimension(1000, 400));
    jPanelRandomScenarioGenerators.setPreferredSize(new Dimension(1000, 200));
    jProgressBarGenNodeScen.setPreferredSize(new Dimension(60, 18));
    jProgressBarGenerateTopology.setPreferredSize(new Dimension(60, 18));
    jButtonGenNodeScen.addActionListener(new
        SimulationConfigurationFrame_jButtonGenNodeScen_actionAdapter(this));
    jButtonChooseSensors.setText("Select Sensors");
    jButtonChooseSensors.addActionListener(new
        SimulationConfigurationFrame_jButtonChooseSensors_actionAdapter(this));
    jPanelPRNG.add(jLabelSeed);
    jPanelPRNG.add(jTextFieldSeed);
    jPanelPRNG.add(jLabelLuxury);
    jPanelPRNG.add(jComboBoxLuxury);
    jPanelPRNG.add(jLabelDistribution);
    jPanelPRNG.add(jComboBoxDistribution);
    jPanelPRNG.add(jLabelGenerator);
    jPanelPRNG.add(jComboBoxGenerator);

    // Topology Generator Panel
    jPanelTopologyGenerator.setBorder(titledBorder2);
    jPanelTopologyGenerator.setPreferredSize(new Dimension(1000, 62));
    jLabelNumNodes.setText("Number of nodes");
    jTextFieldNumNodes.setPreferredSize(new Dimension(66, 23));
    jTextFieldNumNodes.setText("");
    jTextFieldNumNodes.setColumns(4);
    jLabelMaxX.setText("Max X");
    jTextFieldMaxX.setPreferredSize(new Dimension(66, 23));
    jTextFieldMaxX.setText("");
    jTextFieldMaxX.setColumns(4);
    jLabelMaxY.setText("Max Y");
    jTextFieldMaxY.setPreferredSize(new Dimension(66, 23));
    jTextFieldMaxY.setText("");
    jTextFieldMaxY.setColumns(4);
    jTextFieldMaxY.addActionListener(new
        SimulationConfigurationFrame_jTextFieldMaxY_actionAdapter(this));
    jButtonGenTopo.setText("Generate");
    jButtonSaveAsTopo.setText("Save As");
    jPanelTopologyGenerator.add(jLabelNumNodes);
    jPanelTopologyGenerator.add(jTextFieldNumNodes);
    jPanelTopologyGenerator.add(jLabelMaxX);
    jPanelTopologyGenerator.add(jTextFieldMaxX);
    jPanelTopologyGenerator.add(jLabelxMeters);
    jPanelTopologyGenerator.add(jLabelMaxY);
    jPanelTopologyGenerator.add(jTextFieldMaxY);
    jPanelTopologyGenerator.add(jLabelyMeters);
    jPanelTopologyGenerator.add(jButtonGenTopo);
    jPanelTopologyGenerator.add(jProgressBarGenerateTopology);
    jPanelTopologyGenerator.add(jButtonSaveAsTopo);
    jLabelxMeters.setFont(font1);
    jLabelxMeters.setText("m");
    jLabelyMeters.setToolTipText("");
    jLabelyMeters.setFont(font1);
    jLabelyMeters.setText("m");

    //Node Scenario Panel
    jPanelNodeScenarioGenerator.setBorder(titledBorder3);
    jPanelNodeScenarioGenerator.setPreferredSize(new Dimension(1000, 62));
    jLabelInitEnergy.setText("Energy");
    jTextFieldInitEnergy.setPreferredSize(new Dimension(66, 23));
    jTextFieldInitEnergy.setText("");
    jTextFieldInitEnergy.setColumns(4);
    jLabelRadioRange.setText("Radio");
    jTextFieldRadioRange.setPreferredSize(new Dimension(66, 23));
    jTextFieldRadioRange.setText("");
    jTextFieldRadioRange.setColumns(4);
    jLabelRadioBitRate.setText("Bit Rate");
    jTextFieldRadioBitRate.setPreferredSize(new Dimension(66, 23));
    jTextFieldRadioBitRate.setText("");
    jTextFieldRadioBitRate.setColumns(4);
    jLabelMaxMemory.setText("Memory");
    jTextFieldMaxMemory.setPreferredSize(new Dimension(66, 23));
    jTextFieldMaxMemory.setText("");
    jTextFieldMaxMemory.setColumns(4);
    jButtonGenNodeScen.setText("Generate");
    jButtonSaveNodeScen.setText("Save As");
    jPanelNodeScenarioGenerator.add(jLabelInitEnergy);
    jPanelNodeScenarioGenerator.add(jTextFieldInitEnergy);
    jPanelNodeScenarioGenerator.add(jLabelJoules);
    jPanelNodeScenarioGenerator.add(jLabelRadioRange);
    jPanelNodeScenarioGenerator.add(jTextFieldRadioRange);
    jPanelNodeScenarioGenerator.add(jLabelRadioRangeMeters);
    jPanelNodeScenarioGenerator.add(jLabelRadioBitRate);
    jPanelNodeScenarioGenerator.add(jTextFieldRadioBitRate);
    jPanelNodeScenarioGenerator.add(jLabelBitRateKbps);
    jPanelNodeScenarioGenerator.add(jLabelMaxMemory);
    jPanelNodeScenarioGenerator.add(jTextFieldMaxMemory);
    jPanelNodeScenarioGenerator.add(jLabelMemoryKB);
    jPanelNodeScenarioGenerator.add(jButtonChooseSensors);
    jPanelNodeScenarioGenerator.add(jButtonGenNodeScen);
    jPanelNodeScenarioGenerator.add(jProgressBarGenNodeScen);
    jPanelNodeScenarioGenerator.add(jButtonSaveNodeScen);
    jLabelJoules.setFont(font1);
    jLabelJoules.setText("J");
    jLabelRadioRangeMeters.setFont(font1);
    jLabelRadioRangeMeters.setText("m");
    jLabelBitRateKbps.setFont(font1);
    jLabelBitRateKbps.setText("Kbps");
    jLabelMemoryKB.setFont(font1);
    jLabelMemoryKB.setText("KB");


    //Random Scenario Generators Panel
    jPanelRandomScenarioGenerators.setLayout(borderLayout2);
    jPanelRandomScenarioGenerators.add(jPanelPRNG, java.awt.BorderLayout.NORTH);
    jPanelRandomScenarioGenerators.add(jPanelTopologyGenerator, java.awt.BorderLayout.CENTER);
    jPanelRandomScenarioGenerators.add(jPanelNodeScenarioGenerator, java.awt.BorderLayout.SOUTH);

    // ContentPane
    contentPane.add(jPanelRandomScenarioGenerators, java.awt.BorderLayout.NORTH);

    // Menu Bar and Items
    this.setJMenuBar(jMenuBar1);
    jMenuFile.setText("File");
    jMenuItemFileOpen.setText("Open");
    jMenuHelp.setText("Help");
    jMenuItemHelpTopoScen.setText("Topology Scenario Generator");
    jMenuItemHelpPRNG.setText("Pseudo Random Generator");
    jMenuItemHelpEnergyModel.setText("Energy Model");
    jMenuItemHelpSensingModel.setText("Sensing Model");
    jMenuItemHelpRadioModel.setText("Radio Model");
    jMenuItemHelpEventScen.setText("Event Scenario Generator");
    jMenuItemHelpNodeScen.setText("Node Scenario Generator");
    jMenuItemHelpSimulation.setText("Simulation");
    jMenuItemHelpMoteParameters.setText("Mote Parameters");
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuHelp);
    jMenuFile.add(jMenuItemFileOpen);
    jMenuHelp.add(jMenuItemHelpSimulation);
    jMenuHelp.add(jMenuItemHelpPRNG);
    jMenuHelp.add(jMenuItemHelpTopoScen);
    jMenuHelp.add(jMenuItemHelpNodeScen);
    jMenuHelp.add(jMenuItemHelpEventScen);
    jMenuHelp.add(jMenuItemHelpEnergyModel);
    jMenuHelp.add(jMenuItemHelpSensingModel);
    jMenuHelp.add(jMenuItemHelpRadioModel);
    jMenuHelp.add(jMenuItemHelpMoteParameters);
  } //end of jbInit()


  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  Font font1 = new Font("Serif", Font.ITALIC, 12);
  // PRNG Panel
  TitledBorder titledBorder1 = new TitledBorder("PRNG Parameters");
  JPanel jPanelPRNG = new JPanel();
  JPanel jPanelRandomScenarioGenerators = new JPanel();
  JLabel jLabelSeed = new JLabel();
  JTextField jTextFieldSeed = new JTextField();
  JComboBox jComboBoxLuxury = new JComboBox(edu.cornell.lassp.houle.RngPack.RandomApp.luxuryVals);
  JLabel jLabelLuxury = new JLabel();
  JLabel jLabelDistribution = new JLabel();
  JComboBox jComboBoxDistribution = new JComboBox(simulator.random.DistributionTypes.distributionTypes);
  JLabel jLabelGenerator = new JLabel();
  JComboBox jComboBoxGenerator = new JComboBox(edu.cornell.lassp.houle.RngPack.RandomApp.randomGeneratorTypes);

  // Topology Scenario Panel
  TitledBorder titledBorder2 = new TitledBorder("Topological Parameters");
  JPanel jPanelTopologyGenerator = new JPanel();
  JLabel jLabelNumNodes = new JLabel();
  JTextField jTextFieldNumNodes = new JTextField();
  JLabel jLabelMaxX = new JLabel();
  JTextField jTextFieldMaxX = new JTextField();
  JLabel jLabelMaxY = new JLabel();
  JTextField jTextFieldMaxY = new JTextField();
  JButton jButtonGenTopo = new JButton();
  JProgressBar jProgressBarGenerateTopology = new JProgressBar();
  JButton jButtonSaveAsTopo = new JButton();

  // Node Scenario Panel
  TitledBorder titledBorder3 = new TitledBorder("Node Scenario Parameters");
  JPanel jPanelNodeScenarioGenerator = new JPanel();
  JLabel jLabelInitEnergy = new JLabel();
  JTextField jTextFieldInitEnergy = new JTextField();
  JLabel jLabelRadioRange = new JLabel();
  JTextField jTextFieldRadioRange = new JTextField();
  JLabel jLabelRadioBitRate = new JLabel();
  JTextField jTextFieldRadioBitRate = new JTextField();
  JLabel jLabelMaxMemory = new JLabel();
  JTextField jTextFieldMaxMemory = new JTextField();
  JButton jButtonGenNodeScen = new JButton();
  JProgressBar jProgressBarGenNodeScen = new JProgressBar();
  JButton jButtonSaveNodeScen = new JButton();

  // Menu Bars and Menu Items
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuItemFileOpen = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuItemHelpPRNG = new JMenuItem();
  JMenuItem jMenuItemHelpTopoScen = new JMenuItem();
  JMenuItem jMenuItemHelpEnergyModel = new JMenuItem();
  JMenuItem jMenuItemHelpSensingModel = new JMenuItem();
  JMenuItem jMenuItemHelpRadioModel = new JMenuItem();
  JMenuItem jMenuItemHelpEventScen = new JMenuItem();
  JMenuItem jMenuItemHelpNodeScen = new JMenuItem();
  JMenuItem jMenuItemHelpMoteParameters = new JMenuItem();
  JMenuItem jMenuItemHelpSimulation = new JMenuItem();
  JLabel jLabelxMeters = new JLabel();
  JLabel jLabelyMeters = new JLabel();
  JLabel jLabelJoules = new JLabel();
  JLabel jLabelRadioRangeMeters = new JLabel();
  JLabel jLabelBitRateKbps = new JLabel();
  JLabel jLabelMemoryKB = new JLabel();
  JButton jButtonChooseSensors = new JButton();
  ListDialogRunner sensorListDialogRunner;

  public void jTextFieldMaxY_actionPerformed(ActionEvent e) {

  }

  public void jButtonGenNodeScen_actionPerformed(ActionEvent e) {

  }

  public void jButtonChooseSensors_actionPerformed(ActionEvent e) {
     sensorListDialogRunner = new ListDialogRunner(this, "Select Sensors", false, rbmwsimulator.mote.SensorTypes.sensors, "Sensors ...");
  }
} //end of class SimulationConfigurationFrame

class SimulationConfigurationFrame_jButtonChooseSensors_actionAdapter
    implements ActionListener {
  private SimulationConfigurationFrame adaptee;
  SimulationConfigurationFrame_jButtonChooseSensors_actionAdapter(
      SimulationConfigurationFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonChooseSensors_actionPerformed(e);
  }
}

class SimulationConfigurationFrame_jButtonGenNodeScen_actionAdapter
    implements ActionListener {
  private SimulationConfigurationFrame adaptee;
  SimulationConfigurationFrame_jButtonGenNodeScen_actionAdapter(
      SimulationConfigurationFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonGenNodeScen_actionPerformed(e);
  }
}

class SimulationConfigurationFrame_jTextFieldMaxY_actionAdapter
    implements ActionListener {
  private SimulationConfigurationFrame adaptee;
  SimulationConfigurationFrame_jTextFieldMaxY_actionAdapter(
      SimulationConfigurationFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jTextFieldMaxY_actionPerformed(e);
  }
}
