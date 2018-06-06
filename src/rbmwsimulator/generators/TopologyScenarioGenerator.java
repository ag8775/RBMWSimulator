package rbmwsimulator.generators;
import java.io.*;
import java.util.StringTokenizer;
import rbmwsimulator.element.Coordinates;
import rbmwsimulator.util.Preferences;
import simulator.util.FormatWriter;
import simulator.util.Output;

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

public class TopologyScenarioGenerator {

  private int numNodes;
  private int maxX, maxY;
  private long nRandomNumbers;
  private FormatWriter fileout ;
  private String scenarioFile, randomNumbersFileName;
  private String appendPartialFileName;
  private Coordinates coords[];

  public TopologyScenarioGenerator(int numNodes, int maxX, int maxY, long nRandomNumbers, String randomNumbersFileName, String partialFileName) {
    this.numNodes = numNodes;
    this.maxX = maxX;
    this.maxY = maxY;
    this.nRandomNumbers = nRandomNumbers;
    this.randomNumbersFileName = randomNumbersFileName;
    this.appendPartialFileName = partialFileName;
  }

 private long getRandomNumber(long index, long maxX, long maxY)
  {
    String record = null;
    long recCount = index;
    long i = 1;
    long coord = 0;
    try {
          FileReader fr = new FileReader(this.randomNumbersFileName);
          BufferedReader br = new BufferedReader(fr);
          record = new String();
          while (((record = br.readLine()) != null)&&(i <= recCount))
          {
            if(i == recCount)
              coord = parseRandomNumberRecord(record, maxX, maxY);
            i++;
          }
          fr.close();
          br.close();
        }
        catch (IOException e)
        {
          // catch possible io errors from readLine()
          Output.ERR(this.toString()+":Uh oh, got an IOException error!", Preferences.PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS);
          e.printStackTrace();
        }
        return coord;
  } // end of readRandomNumbersFile()

  private long parseRandomNumberRecord(String str, long maxX, long maxY)
  {
    // Random Number Scenario File format ...
    // index: randomNumber;
    //  2: 0.5632242;

    String delimiters = new String(": ;");
    StringTokenizer st = new StringTokenizer(str, delimiters);
    long index = 0;
    long coord = 0;
    long scale = 1000;
    if(maxX < 100)
      scale = 100;
    else if(maxX < 1000)
      scale = 1000;
    while(st.hasMoreTokens())
    {
      index = Long.valueOf(st.nextToken()).longValue(); //ignore index ...
      coord = Math.round((Double.valueOf(st.nextToken()).doubleValue())*scale);
      if(coord < 0)
       coord = coord*(-1);
    }
    return coord;
  } //parseRandomNumberRecord()

    public void generateScenario()
   {
      long coordX, coordY;
      int i=1, j=1;

      dumpInfoInFile(numNodes, maxX, maxY);
      coords = new Coordinates[numNodes];
      coords[0] = new Coordinates(0, 0);
      printInfo(0+": "+0+", "+0+";");

      while(i < numNodes)
      {
        coordX = getRandomNumber(j, maxX, maxY);
        coordY = getRandomNumber(j+1, maxX, maxY);

        if(coordX > maxX)
         coordX = coordX % maxX;
        if(coordY > maxY)
         coordY = coordY % maxY;

        coords[i] = new Coordinates(coordX, coordY);

        printInfo(i+": "+coords[i].getX()+", "+coordY+";");

        i+=1;
        j+=2;
       }
      closeInfoFile();
   }

   public void dumpInfoInFile(int numNodes, int maxX, int maxY)
    {
      try {
            this.scenarioFile = new String("topo_scen_"+this.appendPartialFileName+".scen");
            this.fileout = new FormatWriter(new BufferedWriter(new FileWriter(scenarioFile)), 5);
          }
      catch (IOException ae)
       {
          Output.ERR(this.toString()+":IO exception thrown: ", Preferences.PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS, ae);
       }
    }

  public void closeInfoFile()
   {
     try {
           fileout.close();
         }
     catch (Exception io)
      {
       Output.ERR(this.toString()+":Error during closing Topology file", Preferences.PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS);
      }
  }

  public String getTopologyScenarioFile() {
    return this.scenarioFile;
  }

  public void printInfo(String str)
   {
     try
      {
         fileout.println(str);
      }
     catch(Exception ae)
     {
       Output.ERR(this.toString()+":IO exception Thrown: ", Preferences.PRINT_TOPOLOGY_SCENARIO_MODEL_DETAILS, ae);
     }
  }
}
