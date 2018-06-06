package rbmwsimulator.generators;
import rbmwsimulator.util.Preferences;
import rbmwsimulator.element.Coordinates;
import java.io.BufferedReader;
import java.io.IOException;
import simulator.util.Output;
import java.util.StringTokenizer;
import java.io.FileReader;


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
public class MobilityStatus {
  private Coordinates currEventPos;
  private long currSpeed;
  private double theta; // Direction of movement in radians
  private double pauseTime, mobileTime ;
  private double currentSimTime ;
  private int movementStatus; // 1 => Mobile
                      // 2 => Pause

  private int maxX, maxY;
  private double startPauseTime, startMobileTime;
  private double endPauseTime, endMobileTime ;

  private float minPauseTime;
  private float maxPauseTime;

  private float minMobilityTime;
  private float maxMobilityTime;

  private int maxSpeed;
  private int minSpeed;

  private String randomNumbersFileName;
  private FileReader fr;
  private BufferedReader br;
  private int id;

  public MobilityStatus(int id_, Coordinates eventPos_, String randomNumbersFileName_, int maxX_, int maxY_, int maxSpeed_, int minSpeed_, float minPauseTime_, float maxPauseTime_, float minMobilityTime_, float maxMobilityTime_) {
    this.id = id_;
    this.currEventPos = eventPos_;
    this.randomNumbersFileName = randomNumbersFileName_;
    this.maxX = maxX_;
    this.maxY = maxY_;
    this.currSpeed = 0;
    this.currentSimTime = 0;
    this.startMobileTime = 0;
    this.startPauseTime = 0;
    this.theta = 0.0;
    this.movementStatus = Preferences.STATIONARY;
    this.pauseTime = 0;
    this.mobileTime = 0;
    this.maxSpeed = maxSpeed_;
    this.minSpeed = minSpeed_;
    this.minMobilityTime = minMobilityTime_;
    this.maxMobilityTime = maxMobilityTime_;
    this.minPauseTime = minPauseTime_;
    this.maxPauseTime = maxPauseTime_;
  }

  public MobilityStatus(int id_, Coordinates eventPos_, int maxX_, int maxY_, int maxSpeed_, int minSpeed_, float minPauseTime_, float maxPauseTime_, float minMobilityTime_, float maxMobilityTime_) {
    this.id = id_;
    this.currEventPos = eventPos_;
    this.maxX = maxX_;
    this.maxY = maxY_;
    this.currSpeed = 0;
    this.currentSimTime = 0;
    this.startMobileTime = 0;
    this.startPauseTime = 0;
    this.theta = 0.0;
    this.movementStatus = Preferences.STATIONARY;
    this.pauseTime = 0;
    this.mobileTime = 0;
    this.maxSpeed = maxSpeed_;
    this.minSpeed = minSpeed_;
    this.minMobilityTime = minMobilityTime_;
    this.maxMobilityTime = maxMobilityTime_;
    this.minPauseTime = minPauseTime_;
    this.maxPauseTime = maxPauseTime_;
  }

  //Get Methods ...

  public int getId() {
    return this.id;
  }

  public Coordinates getCurrentPosition() {
    return this.currEventPos;
  }

  public long getCurrentSpeed() {
    return this.currSpeed;
  }

  public double getTheta() {
    return this.theta;
  }

  public double getPauseTime() {
    return this.pauseTime;
  }

  public double getMobileTime() {
    return this.mobileTime;
  }

  public double getLocalTime() {
    return this.currentSimTime;
  }

  public int getMovementStatus() {
    return this.movementStatus;
  }

  public double getStartPauseTime() {
    return this.startPauseTime;
  }

  public double getStartMobileTime() {
    return this.startMobileTime;
  }

  public double getEndPauseTime() {
    return this.endPauseTime;
  }

  public double getEndMobileTime() {
    return this.endMobileTime;
  }

  public void openRandomNumberFile()
  {
    try {
      fr = new FileReader(this.randomNumbersFileName);
      br = new BufferedReader(fr);
    }
    catch (IOException e)
    {
      // catch possible io errors from readLine()
      Output.ERR(this.toString()+": Uh oh, got an IOException error!", true);
      e.printStackTrace();
    }
  }

  public void closeRandomNumberFile() {
    try {
        fr.close();
        br.close();
      }
      catch (IOException e)
      {
        // catch possible io errors from readLine()
        Output.ERR(this.toString()+": Uh oh, got an IOException error!", true);
        e.printStackTrace();
      }
  }

  private double parseRandomNumberRecord(String str)
  {
    // Random Number Scenario File format ...
    // index: randomNumber;
    //  2: 0.5632242;
    String delimiters = new String(": ;");
    StringTokenizer st = new StringTokenizer(str, delimiters);
    long index = 0;
    double randomNumber = 0.0;
    while(st.hasMoreTokens())
    {
      index = Long.valueOf(st.nextToken()).longValue(); //ignore index ...
      randomNumber = Double.valueOf(st.nextToken()).doubleValue();
    }
    return randomNumber;
  } //parseRandomNumberRecord()

  public double getRandomNumber() {
    String record = null;
    double randomNumber = 0.0;
    try {
      record = br.readLine();
    }
    catch (IOException e) {
      // catch possible io errors from readLine()
      Output.ERR(this.toString()+": Uh oh, got an IOException error!", true);
      e.printStackTrace();
    }

    if(record != null)
      randomNumber = parseRandomNumberRecord(record);

    //Output.SIMINFO("random number = "+randomNumber);

    return randomNumber;
  }

  public int getMaxX() {
    return this.maxX;
  }

  public int getMaxY() {
    return this.maxY;
  }

  public String getRandomNumbersFileName() {
    return this.randomNumbersFileName;
  }

  //Set Methods ...
  public void setCurrentPosition(Coordinates newEventPos) {
    this.currEventPos = newEventPos;
  }

  public void setCurrentSpeed(long newSpeed) {
    this.currSpeed = newSpeed;
  }

  public void setTheta(double newTheta) {
    this.theta = newTheta;
  }

  public void setPauseTime(double newPauseTime) {
    this.pauseTime = newPauseTime;
  }

  public void setMobileTime(double newMobileTime) {
    this.mobileTime = newMobileTime;
  }

  public void setLocalTime(double newLocalTime) {
    this.currentSimTime = newLocalTime;
  }

  public void setMovementStatus(int newMovementStatus) {
    this.movementStatus = newMovementStatus;
  }

  public void setStartPauseTime(double newStartPauseTime) {
    this.startPauseTime = newStartPauseTime;
  }

  public void setStartMobileTime(double newStartMobileTime) {
    this.startMobileTime = newStartMobileTime;
  }

  public void setEndPauseTime(double newEndPauseTime) {
    this.endPauseTime = newEndPauseTime;
  }

  public void setEndMobileTime(double newEndMobileTime) {
    this.endMobileTime = newEndMobileTime;
  }

  public void setRandomNumbersFileName(String rnFileName_) {
    this.randomNumbersFileName = rnFileName_;
  }

  public int getMaxSpeed() {
    return this.maxSpeed;
  }

  public int getMinSpeed() {
    return this.minSpeed;
  }

  public float getMinPauseTime() {
    return this.minPauseTime;
  }

  public float getMaxPauseTime() {
    return this.maxPauseTime;
  }

  public float getMinMobilityTime() {
    return this.minMobilityTime;
  }

  public float getMaxMobilityTime() {
    return this.maxMobilityTime;
  }

  public String toString() {
    String str = "";
    if(this.movementStatus == Preferences.STATIONARY)
       str = new String(this.id+": Position: {"+this.currEventPos.getX()+", "+this.currEventPos.getY()+"}, Status: STATIONARY, Mobility: "+this.getMobileTime()+"s, "+this.getTheta()+" degrees, "+this.getCurrentSpeed()+"m/s Pause: "+this.getPauseTime()+"s");
    else
      str = new String(this.id+": Position: {"+this.currEventPos.getX()+", "+this.currEventPos.getY()+"}, Status: MOBILE, Mobility: "+this.getMobileTime()+"s, "+this.getTheta()+" degrees, "+this.getCurrentSpeed()+"m/s Pause: "+this.getPauseTime()+"s");
    //System.out.println(str);
    return str;
  }
 }
