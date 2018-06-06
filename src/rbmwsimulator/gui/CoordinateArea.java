package rbmwsimulator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

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
public  class CoordinateArea extends JComponent implements MouseInputListener {
  public CoordinateArea() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  Point point = null;
       SensorNetworkDeployment controller;
       Dimension preferredSize = new Dimension(400,75);
       Color gridColor;
       int gridspace;
       int xMargin, yMargin;
       int maxX, maxY;

       public CoordinateArea(SensorNetworkDeployment controller, int maxX, int maxY) {
           this.controller = controller;
           this.gridspace = 40;
           this.xMargin = 20;
           this.yMargin = 20;
           this.maxX = maxX;
           this.maxY = maxY;
           //Add a border of 5 pixels at the left and bottom,
           //and 1 pixel at the top and right.
           //setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));
           addMouseListener(this);
           addMouseMotionListener(this);
           setBackground(Color.WHITE);
           setOpaque(true);
       }

       public Dimension getPreferredSize() {
           return preferredSize;
       }

       protected void paintComponent(Graphics g) {
           //Paint background if we're opaque.
           if (isOpaque()) {
               g.setColor(getBackground());
               g.fillRect(0, 0, getWidth(), getHeight());
           }

           //Paint gridspace x gridspace grid.
           g.setColor(Color.GRAY);
           drawGrid(g, this.gridspace);

           //If user has chosen a point, paint a small dot on top.
           if (point != null) {
               g.setColor(getForeground());
               g.fillRect(point.x - 3, point.y - 3, 7, 7);
           }
       }



       //Draws a 20x20 grid using the current color.
       private void drawGrid(Graphics g, int gridSpace) {
           Insets insets = getInsets();
           int firstX = insets.left + this.xMargin;
           int firstY = insets.top + this.yMargin;
           int lastX = getWidth() - insets.right - this.xMargin;
           int lastY = getHeight() - insets.bottom - this.yMargin;
           lastX -= lastX%gridSpace;
           lastY -= lastY%gridSpace;
           System.out.println("firstX = "+firstX+", lastX = "+lastX+" firstY = "+firstY+", lastY = "+lastY);
           Font font1 = new Font("Serif", Font.PLAIN, 12);
           Font font2 = new Font("SansSerif", Font.BOLD + Font.ITALIC, 40);
           Font font3 = g.getFont();
           //int[] xpoints = {(lastX-xMargin/2), (lastX-xMargin/2), (lastX-xMargin/2)};
           //int[] ypoints = {(firstY+yMargin/2), (firstY-yMargin/2), firstY};
           //Polygon myTriangle = new Polygon(xpoints, ypoints, 3);

           // Draw XY-axes
           g.setFont(font2);
           g.setColor(Color.BLACK);
           g.drawLine(firstX,firstY,lastX-xMargin/2,firstY); // Drawing X Axis...
           //g.drawPolygon(myTriangle);
           //g.fillPolygon(myTriangle);
           g.drawLine(firstX,firstY,firstX,lastY-yMargin/2); // Drawing Y Axis ...

           g.setFont(font1);
           g.drawString("(0, 0)", this.xMargin/2, this.yMargin*3/4);
           g.drawString("x", lastX-xMargin/2,this.yMargin*3/4);
           g.drawString("y", this.xMargin/4,lastY-yMargin/2);

           g.setFont(font3);
           g.setColor(Color.GRAY);

           //Draw vertical lines.
           int x = firstX+gridSpace;
           while (x < lastX) {
               g.drawLine(x, firstY, x, lastY-yMargin);
               x += gridSpace;
           }

           //Draw horizontal lines.
           int y = firstY+gridSpace;
           while (y < lastY) {
               g.drawLine(firstX, y, (lastX-xMargin), y);
               y += gridSpace;
           }
      }

       //Methods required by the MouseInputListener interface.
       public void mouseClicked(MouseEvent e) {
           int x = e.getX();
           int y = e.getY();
           if (point == null) {
               point = new Point(x, y);
           } else {
               point.x = x;
               point.y = y;
           }
           controller.updateClickPoint(point);
           repaint();
       }

       public void mouseMoved(MouseEvent e) {
           controller.updateCursorLocation(e.getX(), e.getY());
       }

       public void mouseExited(MouseEvent e) {
           controller.resetLabel();
       }

       public void mouseReleased(MouseEvent e) { }
       public void mouseEntered(MouseEvent e) { }
       public void mousePressed(MouseEvent e) { }
       public void mouseDragged(MouseEvent e) { }

  private void jbInit() throws Exception {
  }

}
