package rbmwsimulator.gui;
import java.awt.*;
import java.awt.event.*;
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
public class ListDialog extends JDialog implements ActionListener {
  private String listLabelText;
  private JList list;
  private JScrollPane listScroller;
  //Create a container so that we can add a title around
  //the scroll pane.  Can't add a title directly to the
  //scroll pane because its background would be white.
  //Lay out the label and scroll pane from top to bottom.
  private JPanel listPane = new JPanel();
  private JLabel label = new JLabel();
  //Lay out the buttons from left to right.
  private JPanel buttonPane = new JPanel();
  private JButton cancelButton = new JButton("Cancel");
  private JButton setButton = new JButton("Set");

  public ListDialog(Frame owner, String title, boolean modal,  String listLabelText, JList list) {
    super(owner, title, modal);
    this.listLabelText = listLabelText;
    this.list = list;
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      pack();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    listScroller = new JScrollPane(list);
    listScroller.setPreferredSize(new Dimension(250, 80));
    listScroller.setAlignmentX(LEFT_ALIGNMENT);
    listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
    label.setText(listLabelText);
    //label.setLabelFor(list);
    listPane.add(label);
    listPane.add(Box.createRigidArea(new Dimension(0,5)));
    listPane.add(listScroller);
    listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
    buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    buttonPane.add(Box.createHorizontalGlue());
    buttonPane.add(cancelButton);
    buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
    setButton.setActionCommand("Set");
    setButton.addActionListener(this);
    getRootPane().setDefaultButton(setButton);
    buttonPane.add(setButton);
    //Put everything together, using the content pane's BorderLayout.
    Container contentPane = getContentPane();
    contentPane.add(listPane, BorderLayout.CENTER);
    contentPane.add(buttonPane, BorderLayout.PAGE_END);
    //panel1.setLayout(borderLayout1);
    //getContentPane().add(panel1);
  }

  //Close the dialog on a button event
 public void actionPerformed(ActionEvent e) {

   }
}
