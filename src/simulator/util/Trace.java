package simulator.util;
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
public class Trace {
        private String traceFileName;
        private FormatWriter fileout ;


        public Trace(String fileName) {
                openTraceFile(fileName);
        }

        public void openTraceFile(String fileName)
    {
      try {
            this.traceFileName = new String(fileName);
            this.fileout = new FormatWriter(new BufferedWriter(new FileWriter(traceFileName)), 5);
          }
      catch (IOException ae)
       {
          System.out.println("IO exception thrown: " + ae);
       }
    }

  public void closeTraceFile()
   {
     try {
           fileout.close();
         }
     catch (Exception io)
      {
       System.out.println("Error during closing Trace file");
      }
  }

  public void dumpTrace(String str)
   {
     try
      {
         fileout.println(str);
      }
     catch(Exception ae)
     {
       System.out.println("IO exception Thrown: " + ae);
     }
  }

} // End of Trace Class ...
