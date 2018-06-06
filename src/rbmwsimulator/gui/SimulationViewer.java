package rbmwsimulator.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

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
public class SimulationViewer extends JFrame {
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder1;
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JLabel jlbl_maxX = new JLabel();
  JTextField jtf_maxX = new JTextField();
  JLabel jlbl_maxY = new JLabel();
  JTextField jtf_maxY = new JTextField();
  JButton jbtn_genScen = new JButton();
  JProgressBar jpb_genTopo = new JProgressBar();
  JButton jbtn_viewTopo = new JButton();
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  JMenu menuOperation = new JMenu();
   JMenu menuExit = new JMenu();
  JMenuItem jmiOpen = new JMenuItem();
  JMenuItem jmiGenerate = new JMenuItem();
  JMenuItem jmiView = new JMenuItem();
  JMenuItem jmiClose = new JMenuItem();
  JLabel jbl_numNodes = new JLabel();
  JTextField jtf_numNodes = new JTextField();
  JMenuItem jmiPrint = new JMenuItem();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JMenuItem jmiHelp = new JMenuItem();
  // JMenuItem jmiAbout = new JMenuItem();
  ButtonGroup buttonGroup2 = new ButtonGroup();
  //Create a file chooser
   ImageIcon openIcon = new ImageIcon("../images/open.gif");
   ImageIcon helpIcon = new ImageIcon("../images/help.gif");
   ImageIcon printIcon = new ImageIcon("../images/print.gif");
   ImageIcon viewIcon = new ImageIcon("../images/view.gif");
   ImageIcon clearIcon = new ImageIcon("../images/clear1.gif");
   ImageIcon generateIcon = new ImageIcon("../images/generate.gif");
   ImageIcon aboutIcon = new ImageIcon("../images/about2.gif");
   ImageIcon closeIcon = new ImageIcon("../images/close.gif");


   final JFileChooser fc = new JFileChooser();
   JMenuItem jmiClear = new JMenuItem();
   SensorNetworkDeployment wsnDeploymentVisualizer;
   int mouseX = -1, mouseY = -1;
   int maxX, maxY;

  public SimulationViewer() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {    //setIconImage(Toolkit.getDefaultToolkit().createImage(SensorTopologyViewer.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(1025, 978));
    this.setTitle("Sensing Applications Simulation Visualizer");
    this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        this_mouseMoved(e);
      }
    });
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        this_mouseClicked(e);
      }
      public void mouseReleased(MouseEvent e) {
        this_mouseReleased(e);
      }
      public void mouseExited(MouseEvent e) {
        this_mouseExited(e);
      }
    });
    jPanel1.setBorder(titledBorder1);
    jPanel1.setDoubleBuffered(false);
    jlbl_maxX.setText("maxX");
    jtf_maxX.setColumns(4);
    jlbl_maxY.setText("maxY");
    jtf_maxY.setColumns(4);
    jbtn_genScen.setToolTipText("");
    jbtn_genScen.setActionCommand("Generate Scenario");
    jbtn_genScen.setIcon(generateIcon);
    jbtn_genScen.setText("Gen. Scenario");
    jbtn_genScen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbtn_genScen_actionPerformed(e);
      }
    });
    jbtn_viewTopo.setActionCommand("View Scen");
    jbtn_viewTopo.setIcon(viewIcon);
    jbtn_viewTopo.setText("View");
    jbtn_viewTopo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jbtn_viewTopo_actionPerformed(e);
      }
    });
    jPanel2.setBorder(titledBorder2);
    jPanel3.setBackground(Color.white);
    jPanel3.setBorder(titledBorder3);
    jPanel3.setDoubleBuffered(false);
    menuOperation.setMnemonic('O');
    menuOperation.setText("Operation");
    menuExit.setMnemonic('X');
    menuExit.setText("Exit");
    jmiOpen.setText("Open Topology File");
    jmiOpen.setIcon(openIcon);
    jmiOpen.setMnemonic('F');
    jmiOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(SimulationViewer.this);
                jtf_numNodes.setText("");
                jtf_maxX.setText("");
                jtf_maxY.setText("");
                jPanel3.repaint();
                jpb_genTopo.setValue(0);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //openFile = true;
                    //openedFile = file.getName();
                    //pathFile = file.getAbsolutePath();
                    //printInfo(" Opening File ..."+openedFile+" with Path ..."+pathFile);
                   //this is where a real application would open the file.
                //    log.append("Opening: " + file.getName() + "." + newline);
                } else {
                  //  log.append("Open command cancelled by user." + newline);
                }
            }
        });

    jmiGenerate.setIcon(generateIcon);
    jmiGenerate.setMnemonic('G');
    jmiGenerate.setText("Generate Topology");
    jmiGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              //generateTopology();
              //openFile = false;
            }});

    jmiView.setIcon(viewIcon);
    jmiView.setMnemonic('V');
    jmiView.setText("View Topology");
    jmiView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String fileName;
            /*  if(!openFile) {
                int numNodes = Integer.parseInt(jtf_numNodes.getText().trim());
                int maxX = Integer.parseInt(jtf_maxX.getText().trim()) ;
                int maxY = Integer.parseInt(jtf_maxY.getText().trim());
                fileName = new String("nodes_"+numNodes+"_maxx_"+(new Integer(maxX)).toString()+"_maxy_"+(new Integer(maxY)).toString()+"_n_"+n+".scen");
              }
              else
              {
                fileName = openedFile;
               }
                viewTopology(fileName);*/
            }});

    jmiClose.setIcon(closeIcon);
    jmiClose.setMnemonic('C');
    jmiClose.setText("Close");
    jmiClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jmiClose_actionPerformed(e);
      }
    });
    jbl_numNodes.setText("Number of Nodes");
    jtf_numNodes.setColumns(5);
    //jpb_genTopo.setNextFocusableComponent(jbtn_viewTopo);

    jpb_genTopo.setValue(0);
    jpb_genTopo.setStringPainted(true);
    //jpb_genTopo.setMaximum(this.n);

    jmiPrint.setIcon(printIcon);
    jmiPrint.setMnemonic('P');
    jmiPrint.setText("Print Topology");
    jmiHelp.setIcon(helpIcon);
    jmiHelp.setMnemonic('H');
    jmiHelp.setText("Help");
    jmiClear.setIcon(clearIcon);
    jmiClear.setMnemonic('L');
    jmiClear.setText("Clear Topology");
    jmiClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              jtf_numNodes.setText("");
              jtf_maxX.setText("");
              jtf_maxY.setText("");
              jPanel3.repaint();
              jpb_genTopo.setValue(0);
              //openFile = false;
            }});
    contentPane.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jbl_numNodes, null);
    jPanel1.add(jtf_numNodes, null);
    jPanel1.add(jlbl_maxX, null);
    contentPane.add(jPanel2, BorderLayout.SOUTH);
    jPanel1.add(jtf_maxX, null);
    jPanel1.add(jlbl_maxY, null);
    jPanel1.add(jtf_maxY, null);
    jPanel2.add(jbtn_genScen, null);
    jPanel2.add(jpb_genTopo, null);
    jPanel2.add(jbtn_viewTopo, null);
    contentPane.add(jPanel3, BorderLayout.CENTER);
    wsnDeploymentVisualizer = new SensorNetworkDeployment(jPanel3, this.maxX, this.maxY);
    jMenuBar1.add(menuOperation);
    jMenuBar1.add(menuExit);
    menuOperation.add(jmiOpen);
    menuOperation.add(jmiGenerate);
    menuOperation.add(jmiClear);
    menuOperation.add(jmiView);
    menuOperation.add(jmiPrint);
    menuExit.add(jmiClose);
    menuExit.add(jmiHelp);
    this.setJMenuBar(jMenuBar1);
  }

  void this_mouseReleased(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
    Graphics g = jPanel3.getGraphics();
    g.drawString("", mouseX, mouseY);
  }

void this_mouseExited(MouseEvent e) {
  mouseX = e.getX();
  mouseY = e.getY();
  Graphics g = jPanel3.getGraphics();
  g.drawString("", mouseX, mouseY);
}

void this_mouseMoved(MouseEvent e) {
  Graphics g = jPanel3.getGraphics();
  g.drawString("", mouseX, mouseY);
}

void this_mouseClicked(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
}

 void jbtn_genScen_actionPerformed(ActionEvent e) {

 }

 void jbtn_viewTopo_actionPerformed(ActionEvent e) {

 }

 void jmiClose_actionPerformed(ActionEvent e) {
     System.exit(0);
  }

  void jbtn_clearTopo_actionPerformed(ActionEvent e) {
       jtf_numNodes.setText("");
       jtf_maxX.setText("");
       jtf_maxY.setText("");
       jPanel3.repaint();
       jpb_genTopo.setValue(0);
       //openFile = false;
   }

   void jbtn_openTopology_actionPerformed(ActionEvent e) {
         int returnVal = fc.showOpenDialog(SimulationViewer.this);
         jtf_numNodes.setText("");
         jtf_maxX.setText("");
         jtf_maxY.setText("");
         jPanel3.repaint();
         jpb_genTopo.setValue(0);
         /*if (returnVal == JFileChooser.APPROVE_OPTION) {
             File file = fc.getSelectedFile();
              openFile = true;
              openedFile = file.getName();
              pathFile = file.getAbsolutePath();
              printInfo(" Opening File ..."+openedFile+" with Path ..."+pathFile);
             //this is where a real application would open the file.
              //    log.append("Opening: " + file.getName() + "." + newline);
          } else {
                   //  log.append("Open command cancelled by user." + newline);
                 }
          */
   }
}

