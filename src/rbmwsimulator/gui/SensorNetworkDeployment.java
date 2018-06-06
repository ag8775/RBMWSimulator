package rbmwsimulator.gui;
import javax.swing.*;
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
public class SensorNetworkDeployment {

  private JPanel networkPanel;
  private JLabel coordinateLabel;
  private Point clickPoint, cursorPoint;
  private int maxX, maxY;

  public SensorNetworkDeployment(JPanel jPanel, int maxX, int maxY) {
    this.networkPanel = jPanel;
    this.maxX = maxX;
    this.maxY = maxY;
    networkPanel.setLayout(new BoxLayout(networkPanel, BoxLayout.PAGE_AXIS));
    CoordinateArea coordinateArea = new CoordinateArea(this, this.maxX, this.maxY);
    networkPanel.add(coordinateArea);

    coordinateLabel = new JLabel();
    resetLabel();
    networkPanel.add(coordinateLabel);

    //Align the left edges of the components.
    coordinateArea.setAlignmentX(Component.LEFT_ALIGNMENT);
    coordinateLabel.setAlignmentX(Component.LEFT_ALIGNMENT); //redundant
  }

  public void updateCursorLocation(int x, int y) {
        if (x < 0 || y < 0) {
            cursorPoint = null;
            updateLabel();
            return;
        }

        if (cursorPoint == null) {
            cursorPoint = new Point();
        }

        cursorPoint.x = x;
        cursorPoint.y = y;
        updateLabel();
    }

    public void updateClickPoint(Point p) {
        clickPoint = p;
        updateLabel();
    }

    public void resetLabel() {
        cursorPoint = null;
        updateLabel();
    }

    protected void updateLabel() {
        String text = "";

        if ((clickPoint == null) && (cursorPoint == null)) {
            text = "Click or move the cursor within the framed area.";
        } else {

            if (clickPoint != null) {
                text += "The last click was at ("
                        + clickPoint.x + ", " + clickPoint.y + "). ";
            }

            if (cursorPoint != null) {
                text += "The cursor is at ("
                        + cursorPoint.x + ", " + cursorPoint.y + "). ";
            }
        }

        coordinateLabel.setText(text);
    }


}
