package rbmwsimulator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
public class ListDialogRunner {
  private ListDialog listDialog;
  private String[] listNames;
  private String listTitle;
  private JList jList;

  public ListDialogRunner(Frame owner, String title, boolean modal, String[] listNames, String listTitle) {
    this.listNames = listNames;
    this.listTitle = listTitle;
    createJList();
    this.listDialog = new ListDialog(owner, title, modal,  this.listTitle, this.jList);
    this.listDialog.setVisible(true);
  }

  private void createJList() {
    //main part of the dialog
    this.jList = new JList(this.listNames) {
    //Subclass JList to workaround bug 4832765, which can cause the
    //scroll pane to not let the user easily scroll up to the beginning
    //of the list.  An alternative would be to set the unitIncrement
    //of the JScrollBar to a fixed value. You wouldn't get the nice
    //aligned scrolling, but it should work.
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
      int row;
      if (orientation == SwingConstants.VERTICAL && direction < 0 && (row = getFirstVisibleIndex()) != -1) {
         Rectangle r = getCellBounds(row, row);
         if ((r.y == visibleRect.y) && (row != 0))  {
            Point loc = r.getLocation();
            loc.y--;
            int prevIndex = locationToIndex(loc);
            Rectangle prevR = getCellBounds(prevIndex, prevIndex);
            if (prevR == null || prevR.y >= r.y) {
               return 0;
            }
            return prevR.height;
         }
      }
      return super.getScrollableUnitIncrement(visibleRect, orientation, direction);
    }};
  } //end of createJList

}
