package simulator.util;
import java.util.*;
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
   A repository of Output utility functions.
 */
public final class Output {

  public static Trace logTrace;

  /**Print an error formatted string and exit app*/
  public static void ERR(String err, boolean print) {
    if(Preferences.SCREEN_DUMP && print)
      System.out.println("[ERROR]: " + err);
    logTrace.dumpTrace("[ERROR]: " + err);
    System.exit(0);
  }

  /** print an error message and stack trace and exit*/
  public static void ERR(String err, boolean print, Exception e) {//throws Exception {
    if(Preferences.SCREEN_DUMP && print)
      System.out.println("[ERROR]: " + err);
    logTrace.dumpTrace("[ERROR]: " + err);
    e.printStackTrace();
    //throw e;
    System.exit(0);
  }

  /** print a debug formatted string */
  public static void DEBUG(String debug, boolean print) {
    if(Preferences.SCREEN_DUMP && print)
      System.out.println("[DEBUG]: " + debug);
    logTrace.dumpTrace("[DEBUG]: " + debug);
  }

  /** print a SIMINFO formatted string */
 public static void SIMINFO(String debug, boolean print) {
   if(Preferences.SCREEN_DUMP && print)
     System.out.println("[SIMINFO]: " + debug);
   logTrace.dumpTrace("[SIMINFO]: " + debug);
 }


  /** print a message formatted string*/
  public static void MSG(String msg, boolean print) {
    if(Preferences.SCREEN_DUMP && print)
      System.out.println("[MESSAGE]: " + msg);
    logTrace.dumpTrace("[MESSAGE]: " + msg);
  }

  /** print a message formatted string but without an EOL character */
  public static void MSGN(String msg, boolean print) {
    if(Preferences.SCREEN_DUMP && print)
      System.out.print("[MESSAGE]: " + msg+"  ");
    logTrace.dumpTrace("[MESSAGE]: " + msg+"  ");
  }

  public static String intVectorToString(Vector neighbors) {
     String str = new String();
     int size = neighbors.size();
     if(size != 0) {
       for (int i = 0; i < (size - 1) ; i++) {
         Integer neighObject = (Integer) neighbors.elementAt(i);
         str += neighObject.toString()+ ", " ;
       }
       Integer neighObject = (Integer) neighbors.elementAt(size-1);
       str += neighObject.toString()  ;
     }
     return str;
   }

}
