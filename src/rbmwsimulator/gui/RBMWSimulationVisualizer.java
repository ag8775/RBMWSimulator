package rbmwsimulator.gui;
import rbmwsimulator.gui.*;
import javax.swing.UIManager;
import java.awt.*;
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

public class RBMWSimulationVisualizer {
  private boolean packFrame;
  private ApplicationIDE frame;

  //Construct the application
  public RBMWSimulationVisualizer() {
    packFrame = false;
    try {
        jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  //createAndShowGui method
  public void createAndShowGui() {
    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        }
        else {
            frame.validate();
        }
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
      frame = new ApplicationIDE();
      System.out.println("Error creating GUI for RBMWSimulationVisualizer");
  }
}
