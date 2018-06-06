package rbmwsimulator.gui;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;

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
public class NetworkEditor {
  private JTextPane networkEditorPane;
  private String newline = "\n";

  public NetworkEditor(JTextPane editorPane) {
    this.networkEditorPane = editorPane;
  }

  public NetworkEditor() {
    this.networkEditorPane = createTextPane();
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public JTextPane getNetworkEditorPane() {
    return this.networkEditorPane;
  }

  private JTextPane createTextPane() {
      String[] initString =
              { "This is an editable JTextPane, ",            //regular
                "another ",                                   //italic
                "styled ",                                    //bold
                "text ",                                      //small
                "component, ",                                //large
                "which supports embedded components..." + newline,//regular
                " " + newline,                                //button
                "...and embedded icons..." + newline,         //regular
                " ",                                          //icon
                newline + "JTextPane is a subclass of JEditorPane that " +
                  "uses a StyledEditorKit and StyledDocument, and provides " +
                  "cover methods for interacting with those objects."
               };

      String[] initStyles =
              { "regular", "italic", "bold", "small", "large",
                "regular", "button", "regular", "icon",
                "regular"
              };

      JTextPane textPane = new JTextPane();
      StyledDocument doc = textPane.getStyledDocument();
      addStylesToDocument(doc);

      try {
          for (int i=0; i < initString.length; i++) {
              doc.insertString(doc.getLength(), initString[i],
                               doc.getStyle(initStyles[i]));
          }
      } catch (BadLocationException ble) {
          System.err.println("Couldn't insert initial text into text pane.");
      }
      return textPane;
  }

  protected void addStylesToDocument(StyledDocument doc) {
      //Initialize some styles.
      Style def = StyleContext.getDefaultStyleContext().
                      getStyle(StyleContext.DEFAULT_STYLE);

      Style regular = doc.addStyle("regular", def);
      StyleConstants.setFontFamily(def, "SansSerif");

      Style s = doc.addStyle("italic", regular);
      StyleConstants.setItalic(s, true);

      s = doc.addStyle("bold", regular);
      StyleConstants.setBold(s, true);

      s = doc.addStyle("small", regular);
      StyleConstants.setFontSize(s, 10);

      s = doc.addStyle("large", regular);
      StyleConstants.setFontSize(s, 16);
  }

  private void jbInit() throws Exception {
  }

}
