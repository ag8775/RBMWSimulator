package rbmwsimulator.gui;

import java.io.*;
import java.sql.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import rbmwsimulator.gui.*;
import simulator.util.Trace;

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
public class ApplicationIDE extends JFrame {
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileNewApplication = new JMenuItem();
  JMenuItem jMenuFileOpenApplication = new JMenuItem();
  JMenuItem jMenuFileSaveApplication = new JMenuItem();
  JMenuItem jMenuFileSaveAsApplication = new JMenuItem();
  JMenuItem jMenuFileCloseApplication = new JMenuItem();
  JMenuItem jMenuFileExit = new JMenuItem();

  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpTopics = new JMenuItem();
  JMenuItem jMenuHelpISADEEnv = new JMenuItem();
  JMenuItem jMenuHelpQuickTips = new JMenuItem();
  JMenuItem jMenuHelpTutorials = new JMenuItem();
  JMenuItem jMenuHelpSamples = new JMenuItem();
  JMenuItem jMenuHelpHomePage = new JMenuItem();
  JMenuItem jMenuHelpAbout = new JMenuItem();

  JMenu jMenuEdit = new JMenu(); // Edit Menu
  JMenuItem jMenuEditUndo = new JMenuItem();
  JMenuItem jMenuEditRedo = new JMenuItem();
  JMenuItem jMenuEditCut = new JMenuItem();
  JMenuItem jMenuEditCopy = new JMenuItem();
  JMenuItem jMenuEditPaste = new JMenuItem();

  JMenu jMenuView = new JMenu(); // View Menu
  JMenuItem jMenuViewEditor = new JMenuItem();
  JMenuItem jMenuViewDefault = new JMenuItem();
  JMenuItem jMenuViewLogs = new JMenuItem();
  JMenuItem jMenuViewNetwork = new JMenuItem();
  JMenuItem jMenuViewRefresh = new JMenuItem();

  JMenu jMenuRun = new JMenu(); // Run Menu
  JMenuItem jMenuRunCompile = new JMenuItem();
  JMenuItem jMenuRunTest = new JMenuItem();
  JMenuItem jMenuRunDebug = new JMenuItem();
  JMenuItem jMenuRunOptimize = new JMenuItem();
  JMenuItem jMenuRunSimulation = new JMenuItem();

  JMenu jMenuConfigure = new JMenu(); // Configure Menu
  JMenuItem jMenuConfigureSimulation = new JMenuItem();

  JToolBar jToolBar = new JToolBar();

  JButton jButtonOpenFile = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  ImageIcon image1;
  ImageIcon image2;
  ImageIcon image3;

  ButtonGroup jButtonGroupSimulationControls = new ButtonGroup();
  JToggleButton jButtonSimPlay = new JToggleButton();
  JToggleButton jButtonSimPause = new JToggleButton();
  JToggleButton jButtonSimStop = new JToggleButton();
  ImageIcon simPlayIcon;
  ImageIcon simPauseIcon;
  ImageIcon simStopIcon;

  //Simulation time ...
  JLabel jSimulationTimeLabel = new JLabel();
  JTextField jSimulationTimeTextField = new JTextField();
  JComboBox jSimulationTimeUnitsComboBox = new JComboBox();
  JLabel jSimulationStatusIconLabel = new JLabel();
  JLabel jSimulationStatusLabel = new JLabel();

  JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel jPanelParameters = new JPanel();
  JPanel jPanelEditor = new JPanel();
  JPanel jPanelNetwork = new JPanel();
  JPanel jPanelLogs = new JPanel();
  JPanel jPanelBottom = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel jLabelCommand = new JLabel();
  //XYLayout xYLayout1 = new XYLayout();
  CardLayout cardLayout1 = new CardLayout();
  JTextField jTextFieldCommand = new JTextField();
  JButton jButtonDispatch = new JButton();
  JPanel jPanelApplicationLogs = new JPanel();
  JCheckBox jCheckBoxApplicationLog = new JCheckBox();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPaneApplicationLogs = new JScrollPane();
  JScrollPane jScrollPaneNetworkLogs = new JScrollPane();
  BorderLayout borderLayout4 = new BorderLayout();
  JCheckBox jCheckBoxNetworkLogs = new JCheckBox();
  JPanel jPanelNetworkLogs = new JPanel();
  JScrollPane jScrollPaneSensingEventLogs = new JScrollPane();
  BorderLayout borderLayout5 = new BorderLayout();
  JCheckBox jCheckBoxSensingEventLogs = new JCheckBox();
  JPanel jPanelSensingEventLogs = new JPanel();
  JScrollPane jScrollPanePredictionLogs = new JScrollPane();
  BorderLayout borderLayout6 = new BorderLayout();
  JCheckBox jCheckBoxPredictionLogs = new JCheckBox();
  JPanel jPanelPredictionLogs = new JPanel();
  JScrollPane jScrollPaneCommandLogs = new JScrollPane();
  JPanel jPanelCommandLogs = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JCheckBox jCheckBoxCommandLog = new JCheckBox();
  JTextArea jTextAreaApp = new JTextArea();
  JTextArea jTextAreaNetworking = new JTextArea();
  JTextArea jTextAreaSensing = new JTextArea();
  JTextArea jTextAreaCommand = new JTextArea();
  JTextArea jTextAreaPrediction = new JTextArea();
  final JFileChooser fc = new JFileChooser();
  SensorNetworkDeployment wsnDeploymentVisualizer;
  NetworkEditor networkEditor = new NetworkEditor();
  //SimulationViewer simulationFrame;
  // File Logs
  String commandFile, appFile, networkingFile, sensingFile, predictionFile;
  Trace commandLog, appLog, networkingLog, sensingLog, predictionLog;
  private int maxX = 670, maxY = 670;
  // Command Ids ...
  public static final byte LED_ON = 1;
  public static final byte LED_OFF = 2;
  public static final byte RADIO_LOUDER = 3;
  public static final byte RADIO_QUIETER = 4;
  public static final byte START_SENSING = 5;
  public static final byte READ_LOG = 6;
  public static final byte DISCOVER_NETWORK = 7;

  // Time ...
  Time time; //Construct the frame

  JTextPane jTextEditorPane = networkEditor.getNetworkEditorPane();
  JScrollPane editorScrollPane = new JScrollPane(jTextEditorPane);

  TitledBorder titledBorder1 = new TitledBorder("");
  GridLayout gridLayout1 = new GridLayout();
  public ApplicationIDE() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception {
    image1 = new ImageIcon("images/openFile.png");
    image2 = new ImageIcon("images/closeFile.png");
    image3 = new ImageIcon("images/help.png");
    simPlayIcon = new ImageIcon("images/play16.gif");
    simPauseIcon = new ImageIcon("images/pause16.gif");
    simStopIcon = new ImageIcon("images/stop16.gif");

    contentPane = (JPanel)this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(800, 749));
    this.setTitle("Integrated Sensing Application(s) Development Environment (ISADE)");
    statusBar.setText(" ");
    jMenuFile.setText("File");
    jMenuFileNewApplication.setText("New...");
    jMenuFileOpenApplication.setText("Open...");
    jMenuFileOpenApplication.addActionListener(new
        ApplicationIDE_jMenuFileOpen_ActionAdapter(this));
    jMenuFileSaveApplication.setText("Save...");
    jMenuFileSaveAsApplication.setText("Save As...");
    jMenuFileCloseApplication.setText("Close...");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new
                                    ApplicationIDE_jMenuFileExit_ActionAdapter(this));

    jMenuHelp.setText("Help");
    jMenuHelpTopics.setText("Help Topics");
    jMenuHelpISADEEnv.setText("ISADE Environment");
    jMenuHelpQuickTips.setText("Quick Tips");
    jMenuHelpTutorials.setText("ISADE Tutorials");
    jMenuHelpSamples.setText("ISADE Samples");
    jMenuHelpHomePage.setText("ISADE Homepage");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new
        ApplicationIDE_jMenuHelpAbout_ActionAdapter(this));

    jMenuEdit.setText("Edit");
    jMenuEditUndo.setText("Undo");
    jMenuEditRedo.setText("Redo");
    jMenuEditCut.setText("Cut");
    jMenuEditCopy.setText("Copy");
    jMenuEditPaste.setText("Paste");

    jMenuView.setText("View");
    jMenuViewDefault.setText("Parameters");
    jMenuViewEditor.setText("Editor");
    jMenuViewLogs.setText("Logs");
    jMenuViewNetwork.setText("Network");
    jMenuViewRefresh.setText("Refresh");

    jMenuRun.setText("Run");
    jMenuRunCompile.setText("Compile");
    jMenuRunTest.setText("Test");
    jMenuRunDebug.setText("Debug");
    jMenuRunOptimize.setText("Optimize");
    jMenuRunSimulation.setText("Simulation");
    jMenuRunSimulation.addActionListener(new
        ApplicationIDE_jMenuRunSimulation_ActionAdapter(this));

    jMenuConfigure.setText("Configure");
    jMenuConfigureSimulation.setText("Simulation");

    jButtonOpenFile.setIcon(image1);
    jButtonOpenFile.setToolTipText("Open File");
    jButtonOpenFile.addActionListener(new
        ApplicationIDE_jMenuFileOpen_ActionAdapter(this));
    jButton2.setIcon(image2);
    jButton2.setToolTipText("Close File");
    jButton3.setIcon(image3);
    jButton3.setToolTipText("Help");

    jButtonSimPlay.setIcon(simPlayIcon);
    jButtonSimPlay.setToolTipText("Play Simulation");
    jButtonSimPlay.addActionListener(new
        ApplicationIDE_jButtonSimPlay_actionAdapter(this));

    jButtonSimPause.setIcon(simPauseIcon);
    jButtonSimPause.setToolTipText("Pause Simulation");
    jButtonSimPause.addActionListener(new
     ApplicationIDE_jButtonSimPause_actionAdapter(this));

   jButtonSimStop.setIcon(simStopIcon);
   jButtonSimStop.setToolTipText("Stop Simulation");
   jButtonSimStop.addActionListener(new
       ApplicationIDE_jButtonSimStop_actionAdapter(this));

   jSimulationTimeLabel.setText("Simulation Time");
   jSimulationTimeLabel.setPreferredSize(new Dimension(91, 20));

   jSimulationTimeTextField.setColumns(10);
   jSimulationTimeTextField.setText("0.000");
   jSimulationTimeTextField.setToolTipText("simulation time");
   jSimulationTimeTextField.setEditable(false);
   jSimulationTimeTextField.setPreferredSize(new Dimension(169, 20));

   jSimulationTimeUnitsComboBox.setPreferredSize(new Dimension(50, 20));
   jSimulationTimeUnitsComboBox.setSelectedIndex(-1);
   jSimulationTimeUnitsComboBox.setToolTipText("simulation time units");
   jSimulationTimeUnitsComboBox.addItem("ns");
   jSimulationTimeUnitsComboBox.addItem("ms");
   jSimulationTimeUnitsComboBox.addItem("s");
   jSimulationTimeUnitsComboBox.addItem("min");

   jSimulationStatusLabel.setText("Status");
   jSimulationStatusLabel.setPreferredSize(new Dimension(37, 20));
   jSimulationStatusIconLabel = new JLabel();
   jSimulationStatusIconLabel.setText("");
   jSimulationStatusIconLabel.setToolTipText("simulation status {red, green, yellow}");
   jSimulationStatusIconLabel.setIcon(new ImageIcon("images/TrafficOff.gif"));

    //jTabbedPane1.setBackground(SystemColor.desktop);
    jTabbedPane1.setBackground(SystemColor.GRAY);
    //jTabbedPane1.setForeground(Color.lightGray);
    jTabbedPane1.setForeground(Color.BLACK);
    jPanelParameters.setForeground(UIManager.getColor("Desktop.background"));
    // jPanelParameters.setLayout(xYLayout1);
    jPanelParameters.setLayout(cardLayout1);
    jPanelBottom.setBackground(UIManager.getColor(
        "InternalFrame.resizeIconHighlight"));
    jPanelBottom.setForeground(SystemColor.activeCaption);
    jPanelBottom.setPreferredSize(new Dimension(10, 40));
    jPanelBottom.setLayout(flowLayout1);
    jLabelCommand.setPreferredSize(new Dimension(59, 30));
    jLabelCommand.setHorizontalAlignment(SwingConstants.LEFT);
    jLabelCommand.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabelCommand.setText("Command");
    jLabelCommand.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
    jTextFieldCommand.setPreferredSize(new Dimension(550, 30));
    jTextFieldCommand.setText("");
    jButtonDispatch.setPreferredSize(new Dimension(95, 30));
    jButtonDispatch.setText("Dispatch");
    jButtonDispatch.addActionListener(new
        ApplicationIDE_jButtonDispatch_actionAdapter(this));
    jPanelApplicationLogs.setBackground(Color.lightGray);
    jPanelApplicationLogs.setMinimumSize(new Dimension(10, 10));
    jPanelApplicationLogs.setPreferredSize(new Dimension(781, 110));
    jPanelApplicationLogs.setLayout(borderLayout2);
    jCheckBoxApplicationLog.setActionCommand("jCheckBox1");
    jCheckBoxApplicationLog.setText("Application Development and Deployment");
    jCheckBoxApplicationLog.addActionListener(new
        ApplicationIDE_jCheckBoxApplicationLog_actionAdapter(this));
    jScrollPaneApplicationLogs.setHorizontalScrollBarPolicy(JScrollPane.
        HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPaneApplicationLogs.setVerticalScrollBarPolicy(JScrollPane.
        VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPaneApplicationLogs.getViewport().setBackground(UIManager.getColor(
        "ScrollPane.background"));
    jScrollPaneApplicationLogs.setBorder(BorderFactory.createEtchedBorder());
    jScrollPaneNetworkLogs.setHorizontalScrollBarPolicy(JScrollPane.
        HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPaneNetworkLogs.setVerticalScrollBarPolicy(JScrollPane.
        VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPaneNetworkLogs.getViewport().setBackground(UIManager.getColor(
        "ScrollPane.background"));
    jScrollPaneNetworkLogs.setBorder(BorderFactory.createEtchedBorder());
    jCheckBoxNetworkLogs.setText("Networking");
    jCheckBoxNetworkLogs.addActionListener(new
        ApplicationIDE_jCheckBoxNetworkLogs_actionAdapter(this));
    jCheckBoxNetworkLogs.setActionCommand("jCheckBox1");
    jPanelNetworkLogs.setLayout(borderLayout4);
    jPanelNetworkLogs.setPreferredSize(new Dimension(781, 110));
    jPanelNetworkLogs.setMinimumSize(new Dimension(10, 10));
    jPanelNetworkLogs.setOpaque(true);
    jPanelNetworkLogs.setBackground(Color.lightGray);
    jScrollPaneSensingEventLogs.setHorizontalScrollBarPolicy(JScrollPane.
        HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPaneSensingEventLogs.setVerticalScrollBarPolicy(JScrollPane.
        VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPaneSensingEventLogs.getViewport().setBackground(UIManager.getColor(
        "ScrollPane.background"));
    jScrollPaneSensingEventLogs.setBorder(BorderFactory.createEtchedBorder());
    jCheckBoxSensingEventLogs.setText("Sensing");
    jCheckBoxSensingEventLogs.addActionListener(new
        ApplicationIDE_jCheckBoxSensingEventLogs_actionAdapter(this));
    jCheckBoxSensingEventLogs.setActionCommand("jCheckBox1");
    jPanelSensingEventLogs.setLayout(borderLayout5);
    jPanelSensingEventLogs.setPreferredSize(new Dimension(781, 110));
    jPanelSensingEventLogs.setMinimumSize(new Dimension(10, 10));
    jPanelSensingEventLogs.setBackground(Color.lightGray);
    jScrollPanePredictionLogs.setHorizontalScrollBarPolicy(JScrollPane.
        HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPanePredictionLogs.getViewport().setBackground(UIManager.getColor(
        "ScrollPane.background"));
    jScrollPanePredictionLogs.setBorder(BorderFactory.createEtchedBorder());
    jCheckBoxPredictionLogs.setText("Prediction");
    jCheckBoxPredictionLogs.addActionListener(new
        ApplicationIDE_jCheckBoxPredictionLogs_actionAdapter(this));
    jCheckBoxPredictionLogs.setActionCommand("jCheckBox1");
    jPanelPredictionLogs.setLayout(borderLayout6);
    jPanelPredictionLogs.setPreferredSize(new Dimension(781, 110));
    jPanelPredictionLogs.setMinimumSize(new Dimension(10, 10));
    jPanelPredictionLogs.setBackground(Color.lightGray);
    jScrollPaneCommandLogs.setHorizontalScrollBarPolicy(JScrollPane.
        HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPaneCommandLogs.setVerticalScrollBarPolicy(JScrollPane.
        VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPaneCommandLogs.getViewport().setBackground(UIManager.getColor(
        "ScrollPane.background"));
    jScrollPaneCommandLogs.setBorder(BorderFactory.createEtchedBorder());
    jPanelCommandLogs.setLayout(borderLayout3);
    jPanelCommandLogs.setPreferredSize(new Dimension(781, 110));
    jPanelCommandLogs.setMinimumSize(new Dimension(10, 10));
    jPanelCommandLogs.setBackground(Color.lightGray);
    jCheckBoxCommandLog.setText("Command");
    jCheckBoxCommandLog.addActionListener(new
        ApplicationIDE_jCheckBoxCommandLog_actionAdapter(this));
    jCheckBoxCommandLog.setActionCommand("jCheckBox1");
    jTextAreaApp.setDebugGraphicsOptions(0);
    jTextAreaApp.setCaretPosition(0);
    jTextAreaApp.setEditable(false);
    jTextAreaApp.setText("");
    jTextAreaApp.setLineWrap(true);
    jTextAreaApp.setWrapStyleWord(true);
    jTextAreaNetworking.setEditable(false);
    jTextAreaNetworking.setText("");
    jTextAreaNetworking.setLineWrap(true);
    jTextAreaNetworking.setWrapStyleWord(true);
    jTextAreaSensing.setEditable(false);
    jTextAreaSensing.setText("");
    jTextAreaSensing.setLineWrap(true);
    jTextAreaSensing.setWrapStyleWord(true);
    jTextAreaCommand.setEditable(false);
    jTextAreaCommand.setText("");
    jTextAreaCommand.setLineWrap(true);
    jTextAreaCommand.setWrapStyleWord(true);
    jTextAreaPrediction.setDoubleBuffered(false);
    jTextAreaPrediction.setEditable(false);
    jTextAreaPrediction.setLineWrap(true);
    jTextAreaPrediction.setWrapStyleWord(true);
    jPanelLogs.setFont(new java.awt.Font("Dialog", Font.BOLD, 13));
    jPanelLogs.setToolTipText("");
    jPanelLogs.setInputVerifier(null);

    jTextEditorPane.setBorder(BorderFactory.createEtchedBorder());
    editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.
                                                VERTICAL_SCROLLBAR_ALWAYS);
    editorScrollPane.setPreferredSize(jPanelEditor.getPreferredSize());
    editorScrollPane.setMinimumSize(jPanelEditor.getMinimumSize());
    jPanelEditor.setLayout(gridLayout1);
    jPanelEditor.add(editorScrollPane);

    wsnDeploymentVisualizer = new SensorNetworkDeployment(jPanelNetwork, this.maxX, this.maxY);

    jToolBar.add(jButtonOpenFile);
    jToolBar.add(jButton2);
    jToolBar.add(jButton3);

    jToolBar.addSeparator();
    jToolBar.add(jButtonSimPlay);
    jButtonGroupSimulationControls.add(jButtonSimPlay);
    jToolBar.add(jButtonSimPause);
    jButtonGroupSimulationControls.add(jButtonSimPause);
    jToolBar.add(jButtonSimStop);
    jButtonGroupSimulationControls.add(jButtonSimStop);

    jToolBar.addSeparator();
    jToolBar.add(jSimulationTimeLabel);
    jToolBar.add(jSimulationTimeTextField);
    jToolBar.add(jSimulationTimeUnitsComboBox);
    jToolBar.add(jSimulationStatusIconLabel);
    jToolBar.add(jSimulationStatusLabel);

    jMenuFile.add(jMenuFileNewApplication);
    jMenuFile.add(jMenuFileOpenApplication);
    jMenuFile.add(jMenuFileCloseApplication);
    jMenuFile.add(jMenuFileSaveApplication);
    jMenuFile.add(jMenuFileSaveAsApplication);
    jMenuFile.add(jMenuFileExit);

    jMenuEdit.add(jMenuEditUndo);
    jMenuEdit.add(jMenuEditRedo);
    jMenuEdit.add(jMenuEditCut);
    jMenuEdit.add(jMenuEditCopy);
    jMenuEdit.add(jMenuEditPaste);

    jMenuHelp.add(jMenuHelpTopics);
    jMenuHelp.add(jMenuHelpISADEEnv);
    jMenuHelp.add(jMenuHelpQuickTips);
    jMenuHelp.add(jMenuHelpTutorials);
    jMenuHelp.add(jMenuHelpSamples);
    jMenuHelp.add(jMenuHelpHomePage);
    jMenuHelp.add(jMenuHelpAbout);

    jMenuView.add(jMenuViewDefault);
    jMenuView.add(jMenuViewEditor);
    jMenuView.add(jMenuViewNetwork);
    jMenuView.add(jMenuViewLogs);
    jMenuView.add(jMenuViewRefresh);

    jMenuRun.add(jMenuRunCompile);
    jMenuRun.add(jMenuRunTest);
    jMenuRun.add(jMenuRunDebug);
    jMenuRun.add(jMenuRunOptimize);
    jMenuRun.add(jMenuRunSimulation);

    jMenuConfigure.add(jMenuConfigureSimulation);
    jMenuConfigureSimulation.addActionListener(new
        ApplicationIDE_jMenuConfigureSimulation_ActionAdapter(this));

    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuEdit);
    jMenuBar1.add(jMenuView);
    jMenuBar1.add(jMenuRun);
    jMenuBar1.add(jMenuConfigure);
    jMenuBar1.add(jMenuHelp);

    this.setJMenuBar(jMenuBar1);
    contentPane.add(jToolBar, BorderLayout.NORTH);
    contentPane.add(statusBar, BorderLayout.WEST);
    contentPane.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanelParameters, "Parameters");
    jTabbedPane1.add(jPanelEditor, "Editor");

    jTabbedPane1.add(jPanelNetwork, "Network");
    jTabbedPane1.add(jPanelLogs, "Logs");
    jPanelBottom.add(jLabelCommand, null);
    jPanelBottom.add(jTextFieldCommand, null);
    jPanelBottom.add(jButtonDispatch, null);
    jPanelCommandLogs.add(jCheckBoxCommandLog, BorderLayout.NORTH);
    jPanelCommandLogs.add(jScrollPaneCommandLogs, BorderLayout.CENTER);
    jPanelLogs.add(jPanelApplicationLogs, null);
    jPanelLogs.add(jPanelNetworkLogs, null);
    jPanelNetworkLogs.add(jCheckBoxNetworkLogs, BorderLayout.NORTH);
    jPanelNetworkLogs.add(jScrollPaneNetworkLogs, BorderLayout.CENTER);
    jPanelLogs.add(jPanelSensingEventLogs, null);
    jPanelSensingEventLogs.add(jCheckBoxSensingEventLogs, BorderLayout.NORTH);
    jPanelSensingEventLogs.add(jScrollPaneSensingEventLogs, BorderLayout.CENTER);
    jPanelLogs.add(jPanelCommandLogs, null);
    jPanelLogs.add(jPanelPredictionLogs, null);
    jPanelPredictionLogs.add(jCheckBoxPredictionLogs, BorderLayout.NORTH);
    jPanelPredictionLogs.add(jScrollPanePredictionLogs, BorderLayout.CENTER);
    contentPane.add(jPanelBottom, BorderLayout.SOUTH);
    jPanelApplicationLogs.add(jCheckBoxApplicationLog, BorderLayout.NORTH);
    jPanelApplicationLogs.add(jScrollPaneApplicationLogs, BorderLayout.CENTER);
    jScrollPaneApplicationLogs.getViewport().add(jTextAreaApp, null);
    jScrollPaneNetworkLogs.getViewport().add(jTextAreaNetworking, null);
    jScrollPaneSensingEventLogs.getViewport().add(jTextAreaSensing, null);
    jScrollPaneCommandLogs.getViewport().add(jTextAreaCommand, null);
    jScrollPanePredictionLogs.getViewport().add(jTextAreaPrediction, null);
    jScrollPaneNetworkLogs.getViewport();
    jScrollPaneSensingEventLogs.getViewport();
    jScrollPanePredictionLogs.getViewport();
    jScrollPaneCommandLogs.getViewport();
    initialize();
  }

  public void jMenuFileOpen_actionPerformed(ActionEvent e) {
    int returnVal = fc.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      boolean openFile = true;
      String openedFile = file.getName();
      String pathFile = file.getAbsolutePath();
      System.out.println(" Opening File ..." + openedFile + " with Path ..." +
                         pathFile);
      //this is where a real application would open the file.
      //    log.append("Opening: " + file.getName() + "." + newline);
    }
    else {
      //  log.append("Open command cancelled by user." + newline);
    }
  }

  //File | Exit action performed
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    closeLogFiles();
    System.exit(0);
  }

  //Help | About action performed
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    ApplicationIDE_AboutBox dlg = new ApplicationIDE_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.show();
  }

  public void jMenuRunSimulation_actionPerformed(ActionEvent e) {
    SimulationViewer simulationFrame = new SimulationViewer();
    boolean packFrame = false;
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      simulationFrame.pack();
    }
    else {
      simulationFrame.validate();
    }
    simulationFrame.setVisible(true);
  }

  public void jMenuConfigureSimulation_actionPerformed(ActionEvent e) {
     SimulationConfigurationFrame simulationConfigurationFrame = new SimulationConfigurationFrame();
     boolean packFrame = false;
     //Validate frames that have preset sizes
     //Pack frames that have useful preferred size info, e.g. from their layout
     if (packFrame) {
       simulationConfigurationFrame.pack();
     }
     else {
       simulationConfigurationFrame.validate();
     }
     simulationConfigurationFrame.setVisible(true);
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }

  void jButtonDispatch_actionPerformed(ActionEvent e) {
    //String line = new String("hi  I am Manish Kochhal pursuing PhD in the area of wireless sensor networks and currently working on the role-based middleware project");
    //this.writeLog(commandLog, line, jTextAreaCommand);
    String command = jTextFieldCommand.getText().trim();
    System.out.println(command);
    //this.writeLog(commandLog, ("<"+getCurrentTime()+"> "+command), jTextAreaCommand);
    this.dispatchCommand(command);
  }

  void jCheckBoxApplicationLog_actionPerformed(ActionEvent e) {

  }

  void jCheckBoxCommandLog_actionPerformed(ActionEvent e) {

  }

  void jCheckBoxPredictionLogs_actionPerformed(ActionEvent e) {

  }

  void jCheckBoxSensingEventLogs_actionPerformed(ActionEvent e) {

  }

  void jCheckBoxNetworkLogs_actionPerformed(ActionEvent e) {

  }

  void jButtonSimPlay_actionPerformed(ActionEvent e) {

  }

  void jButtonSimPause_actionPerformed(ActionEvent e) {

  }

  void jButtonSimStop_actionPerformed(ActionEvent e) {

  }

  private void writeLog(Trace logHandle, String logInfo, JTextArea display) {
    logHandle.dumpTrace(logInfo);
    this.displayLog(display, logInfo);
  }

  private void displayLog(JTextArea display, String line) {
    display.append(line + "\n");
  }

  /**
   * This is the handler invoked when a  msg is received from
   * SerialForward.
   */

  public void messageReceived(int dest_addr) {
    /* if (msg instanceof OscopeMsg) {
          oscopeReceived( dest_addr, (OscopeMsg)msg);
      } else {
     throw new RuntimeException("messageReceived: Got bad message type: "+msg);
      }*/
  }

  public void oscopeReceived(int dest_addr) {
    /*        boolean foundPlot = false;
         int moteID, packetNum, channelID, channel = -1, i;

         moteID = omsg.get_sourceMoteID();
         channelID = omsg.get_channel();
         packetNum = omsg.get_lastSampleNumber();

         for(i = 0; i < NUM_CHANNELS; i++) {
             if(moteNum[i] == moteID && streamNum[i] == channelID) {
                 foundPlot = true;
                 legendActive[i] = true;
                 channel = i;
                 i = NUM_CHANNELS+1;
             }
         }

         if (!foundPlot) {
             for(i = 0; i < NUM_CHANNELS && moteNum[i] != -1; i++);
             if (i >= NUM_CHANNELS) {
                 System.err.println("\nWARNING: Cannot find empty channel for packet from mote ID "+moteID+" channelID "+channelID+" - recompile GraphViz.java with a larger setting for NUM_CHANNELS (currently "+NUM_CHANNELS+")");
                 return;
             }

             channel = i;
             moteNum[i] = moteID;
             streamNum[i] = channelID;
             lastPacketNum[i] = packetNum;
             legendActive[i] = true;
             dataLegend[i] = "Mote "+moteID+" Chan "+channelID;
         }
         if(channel < 0) {
     System.err.println("All data streams full.  Please clear data set.");
             return;
         }
         if(lastPacketNum[channel] == -1) lastPacketNum[channel] = packetNum;

         int packetLoss = packetNum - lastPacketNum[channel] - NUM_READINGS;

         for(int j = 0; j < packetLoss; j++) {
             // Add "NUM_READINGS" blank points for each lost packet
             for(i = 0; i < NUM_READINGS; i++)
                 add_point(null, channel);
         }
         lastPacketNum[channel] = packetNum;
         int limit = omsg.numElements_data();
         for (i = 0; i < limit; i++) {
             Point2D newPoint;
             int val = omsg.getElement_data(i);

             if (VERBOSE) System.err.println("val: "+val+" (0x"+Integer.toHexString(val)+")");
             newPoint = new Point2D( ((double)(packetNum+i)), val );
             add_point( newPoint, channel);
         }
     */
  }

  public void sendMessage() {
    // Send Message of different types ...
    /*
         try {
             System.err.println("SENDING OscopeResetmsg\n");
             mote.send(MoteIF.TOS_BCAST_ADDR, new OscopeResetMsg());
         } catch (IOException ioe) {
     System.err.println("Warning: Got IOException sending reset message: "+ioe);
             ioe.printStackTrace();
         }

         int i;
         data = new Vector2[NUM_CHANNELS];
         for( i=0;i<NUM_CHANNELS;i++ ) data[i] = new Vector2();
         for( i=0;i<NUM_CHANNELS;i++ ) dataLegend[i] = "";
         for( i=0;i<NUM_CHANNELS;i++ ) legendActive[i] = false;
         for( i=0;i<NUM_CHANNELS;i++ ) lastPacketNum[i] = -1;
         for( i=0;i<NUM_CHANNELS;i++ ) streamNum[i] = -1;
         for( i=0;i<NUM_CHANNELS;i++ ) moteNum[i] = -1;
     */
  }

  // Methods added for non-gui variables and objects ...
  private void initialize() {
    this.appFile = new String("logs/ApplicationLog.txt");
    this.appLog = new Trace(this.appFile);
    this.commandFile = new String("logs/CommandLog.txt");
    this.commandLog = new Trace(this.commandFile);
    this.networkingFile = new String("logs/NetworkingLog.txt");
    this.networkingLog = new Trace(this.networkingFile);
    this.sensingFile = new String("logs/SensingLog.txt");
    this.sensingLog = new Trace(this.sensingFile);
    this.predictionFile = new String("logs/PredictionLog.txt");
    this.predictionLog = new Trace(this.predictionFile);
    // Initialize Time ...
    time = new Time(System.currentTimeMillis());
  }

  private void closeLogFiles() {
    this.appLog.closeTraceFile();
    this.commandLog.closeTraceFile();
    this.networkingLog.closeTraceFile();
    this.sensingLog.closeTraceFile();
    this.predictionLog.closeTraceFile();
  }

  private void dispatchCommand(String command) {
    // Need to parse the command to find its type ...
    String delimiters = new String(" ");
    StringTokenizer st = new StringTokenizer(command, delimiters);
    String commandName = st.nextToken();

    if (commandName.equals("led_on")) {
      dispatchLedOn();
    }
    else if (commandName.equals("led_off")) {
      dispatchLedOff();
    }
    else if (commandName.equals("radio_louder")) {
      dispatchRadioLouder();
    }
    else if (commandName.equals("radio_quieter")) {
      dispatchRadioQuieter();
    }
    else if (commandName.equals("start_sensing")) {
      dispatchStartSensing(command, st);
    }
    else if (commandName.equals("read_log")) {
      dispatchReadLog(command, st);
    }
    else if (commandName.equals("discover_network")) {
      dispatchDiscoverNetwork(command, st);
    }
    else {
      commandHelp("command <" + commandName + ">");
    }
  }

  private void dispatchLedOn() {
    this.writeLog(commandLog, ("<" + getCurrentTime() + "> led_on"),
                  jTextAreaCommand);
  }

  private void dispatchLedOff() {
    this.writeLog(commandLog, ("<" + getCurrentTime() + "> led_off"),
                  jTextAreaCommand);

  }

  private void dispatchRadioLouder() {
    this.writeLog(commandLog, ("<" + getCurrentTime() + "> radio_louder"),
                  jTextAreaCommand);
  }

  private void dispatchRadioQuieter() {
    this.writeLog(commandLog, ("<" + getCurrentTime() + "> radio_quieter"),
                  jTextAreaCommand);
  }

  private void dispatchStartSensing(String command, StringTokenizer st) {
    if (st.countTokens() == 2) {
      short nsamples = (short) Integer.valueOf(st.nextToken()).intValue();
      long interval_ms = (long) Integer.valueOf(st.nextToken()).intValue();
      this.writeLog(commandLog,
                    ("<" + getCurrentTime() + "> start_sensing " + nsamples +
                     " " + interval_ms), jTextAreaCommand);
    }
    else {
      commandHelp("arguments for command <start_sensing>");
    }
  }

  private void dispatchDiscoverNetwork(String command, StringTokenizer st) {
    if (st.countTokens() == 2) {
      long num_nodes = (long) Integer.valueOf(st.nextToken()).intValue();
      long interval_ms = (long) Integer.valueOf(st.nextToken()).intValue();
      this.writeLog(commandLog,
                    ("<" + getCurrentTime() + "> discover_network " + num_nodes +
                     " " + interval_ms), jTextAreaCommand);
    }
    else {
      commandHelp("arguments for command <discover_network>");
    }
  }

  private void dispatchReadLog(String command, StringTokenizer st) {
    if (st.countTokens() == 1) {
      short address = (short) Integer.valueOf(st.nextToken()).intValue();
      this.writeLog(commandLog,
                    ("<" + getCurrentTime() + "> read_log " + address),
                    jTextAreaCommand);
    }
    else {
      commandHelp("arguments for command <read_log>");
    }
  }

  private void commandHelp(String commandName) {
    this.writeLog(commandLog,
                  ("<" + getCurrentTime() + "> Invalid " + commandName),
                  jTextAreaCommand);
    this.writeLog(commandLog,
                  "\t <command> and [arguments] can be one of the following:",
                  jTextAreaCommand);
    this.writeLog(commandLog, "\t\tled_on", jTextAreaCommand);
    this.writeLog(commandLog, "\t\tled_off", jTextAreaCommand);
    this.writeLog(commandLog, "\t\tradio_louder", jTextAreaCommand);
    this.writeLog(commandLog, "\t\tradio_quieter", jTextAreaCommand);
    this.writeLog(commandLog, "\t\tstart_sensing [nsamples interval_ms]",
                  jTextAreaCommand);
    this.writeLog(commandLog, "\t\tdiscover_network [num_nodes interval_ms]",
                  jTextAreaCommand);
    this.writeLog(commandLog, "\t\tread_log [dest_address]", jTextAreaCommand);
  }

  private String getCurrentTime() {
    time.setTime(System.currentTimeMillis());
    return time.toString();
  }
}

class ApplicationIDE_jMenuFileExit_ActionAdapter
    implements ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jMenuFileExit_ActionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileExit_actionPerformed(e);
  }
}

class ApplicationIDE_jMenuFileOpen_ActionAdapter
    implements ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jMenuFileOpen_ActionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileOpen_actionPerformed(e);
  }
}

class ApplicationIDE_jMenuHelpAbout_ActionAdapter
    implements ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jMenuHelpAbout_ActionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuHelpAbout_actionPerformed(e);
  }
}

class ApplicationIDE_jMenuRunSimulation_ActionAdapter
    implements ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jMenuRunSimulation_ActionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuRunSimulation_actionPerformed(e);
  }
}

class ApplicationIDE_jMenuConfigureSimulation_ActionAdapter
    implements ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jMenuConfigureSimulation_ActionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuConfigureSimulation_actionPerformed(e);
  }
}

class ApplicationIDE_jButtonDispatch_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jButtonDispatch_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonDispatch_actionPerformed(e);
  }
}

class ApplicationIDE_jCheckBoxApplicationLog_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jCheckBoxApplicationLog_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxApplicationLog_actionPerformed(e);
  }
}

class ApplicationIDE_jCheckBoxCommandLog_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jCheckBoxCommandLog_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxCommandLog_actionPerformed(e);
  }
}

class ApplicationIDE_jCheckBoxPredictionLogs_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jCheckBoxPredictionLogs_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxPredictionLogs_actionPerformed(e);
  }
}

class ApplicationIDE_jCheckBoxSensingEventLogs_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jCheckBoxSensingEventLogs_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxSensingEventLogs_actionPerformed(e);
  }
}

class ApplicationIDE_jCheckBoxNetworkLogs_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jCheckBoxNetworkLogs_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxNetworkLogs_actionPerformed(e);
  }
}

class ApplicationIDE_jButtonSimPlay_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jButtonSimPlay_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonSimPlay_actionPerformed(e);
  }
}

class ApplicationIDE_jButtonSimPause_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jButtonSimPause_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonSimPause_actionPerformed(e);
  }
}


class ApplicationIDE_jButtonSimStop_actionAdapter
    implements java.awt.event.ActionListener {
  ApplicationIDE adaptee;

  ApplicationIDE_jButtonSimStop_actionAdapter(ApplicationIDE adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonSimStop_actionPerformed(e);
  }
}

