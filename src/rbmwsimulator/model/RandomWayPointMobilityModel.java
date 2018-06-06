package rbmwsimulator.model;
import rbmwsimulator.model.MovementModelTypes;
import rbmwsimulator.util.Preferences;
import rbmwsimulator.generators.MobilityStatus;
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

public class RandomWayPointMobilityModel extends MobilityModel {

  public RandomWayPointMobilityModel() {
     super("RandomWayPointMobilityModel", MovementModelTypes.RANDOM_WAYPOINT_MOBILITY_MODEL);
  }

  public void initialize(MobilityStatus currentMobilityStatus) {
    //System.out.println("Event "+currentMobilityStatus.getId()+" in initialize() function");

    currentMobilityStatus.setLocalTime(0);

    // Deciding whether a particular node belongs to the static OR mobile group
    currentMobilityStatus.setMovementStatus(changeMobilityStatus(currentMobilityStatus));

    // Deciding the maxSpeed in case of mobile node OR pause time in case of static node
    if(currentMobilityStatus.getMovementStatus() == Preferences.STATIONARY)
      setPause(currentMobilityStatus, 0);  // Node is paused
     else
      setMobile(currentMobilityStatus, 0);
  }

  private int changeMobilityStatus(MobilityStatus currentMobilityStatus) {
    //System.out.println("Event "+currentMobilityStatus.getId()+" in changeMobilityStatus() function");
    double x, y;

    x = currentMobilityStatus.getRandomNumber();
    y = currentMobilityStatus.getRandomNumber();

    //System.out.println("x = "+x+", y = "+y);

    if (x > y)
      return Preferences.STATIONARY; //Paused

    return Preferences.MOBILE; //Mobile
  }

  private void setMobile(MobilityStatus currentMobilityStatus, long currentTime) {
    //System.out.println("Event "+currentMobilityStatus.getId()+" at time "+currentTime+" in setMobile() function");
    double x;
    double mobileTime ;
    double angle;

    //System.out.println("Event "+currentMobilityStatus.getId()+" at time "+currentTime+" Mobile: minMobilityTime = "+currentMobilityStatus.getMinMobilityTime()+", maxMobilityTime = "+currentMobilityStatus.getMaxMobilityTime());

    //Decide node's speed ...
    x = currentMobilityStatus.getRandomNumber();
    currentMobilityStatus.setCurrentSpeed((int) ((long)currentMobilityStatus.getMinSpeed()  +  (long)((1L + (long)currentMobilityStatus.getMaxSpeed() - (long)currentMobilityStatus.getMinSpeed())*x)));

    // Decide Node's Mobile Time
    x = currentMobilityStatus.getRandomNumber();
    long lo = Math.round(currentMobilityStatus.getMinMobilityTime());
    long hi = Math.round(currentMobilityStatus.getMaxMobilityTime());
    mobileTime = (long) ((long)lo  +  (long)((1L + (long)hi - (long)lo)*x));
    currentMobilityStatus.setMobileTime((double)mobileTime);

    // Decide Node's Direction 1 to 360 degrees
    angle = (double)1 + (double)(360)*(currentMobilityStatus.getRandomNumber());
    angle = (Math.round(angle*((double)100)))/((double)100);
    currentMobilityStatus.setTheta(angle);

    currentMobilityStatus.setStartMobileTime(currentTime);
    currentMobilityStatus.setEndMobileTime(currentMobilityStatus.getStartMobileTime() + mobileTime);
 }

 private void setPause(MobilityStatus currentMobilityStatus, long currentTime) {
   //System.out.println("Event "+currentMobilityStatus.getId()+" at time "+currentTime+" in setPause() function");
   // Node is paused
   double x;
   double randomPause;

   x = currentMobilityStatus.getRandomNumber();
   long lo = Math.round(currentMobilityStatus.getMinPauseTime());
   long hi = Math.round(currentMobilityStatus.getMaxPauseTime());
   randomPause = (long) ((long)lo  +  (long)((1L + (long)hi - (long)lo)*x));

   currentMobilityStatus.setPauseTime((double)randomPause);
   currentMobilityStatus.setTheta(0.0);
   currentMobilityStatus.setCurrentSpeed(0);
   currentMobilityStatus.setStartPauseTime(currentTime);
   currentMobilityStatus.setEndPauseTime(currentMobilityStatus.getStartPauseTime() + randomPause);
 }

 private void updateCurrentTime(MobilityStatus currentMobilityStatus, double currentTime) {
   //System.out.println("Event "+currentMobilityStatus.getId()+" at time "+currentTime+" in updateCurrentTime() function");
   currentMobilityStatus.setLocalTime(currentTime);
 }

 private void move(MobilityStatus currentMobilityStatus, long currentTime) {
   //System.out.println("Event "+currentMobilityStatus.getId()+" at time "+currentTime+" in move() function");
   long coordX = 0, coordY = 0;
   double tmpTime ;
   double sampleTime = 1; // Sample Time in Seconds OR ms OR microseconds...
   double angle;

   updateCurrentTime(currentMobilityStatus, currentTime);

   if(currentMobilityStatus.getLocalTime() <= currentMobilityStatus.getEndMobileTime()) {
     sampleTime = currentMobilityStatus.getLocalTime() - currentMobilityStatus.getStartMobileTime();
     coordX = Math.round(currentMobilityStatus.getCurrentPosition().getX() + currentMobilityStatus.getCurrentSpeed() * Math.cos(currentMobilityStatus.getTheta()) *sampleTime);
     coordY = Math.round(currentMobilityStatus.getCurrentPosition().getY() + currentMobilityStatus.getCurrentSpeed() * Math.sin(currentMobilityStatus.getTheta())* sampleTime);

     //Change angle whenever mobility goes out of boundary
     //i.e. when coordX, coordY < 0 or > maxX(/Y)

     if(coordX < 0 || coordX > currentMobilityStatus.getMaxX() || coordY < 0 || coordY > currentMobilityStatus.getMaxY()) {
       angle = (double)1 + (double)(360)*(currentMobilityStatus.getRandomNumber());
       angle = (Math.round(angle*((double)100)))/((double)100);
       currentMobilityStatus.setTheta(angle);
     }

     if(coordX < 0)
       coordX = 0;

     if(coordY < 0)
       coordY = 0;

     if (coordX > currentMobilityStatus.getMaxX())
       coordX = coordX % currentMobilityStatus.getMaxX() ;

     if (coordY > currentMobilityStatus.getMaxY())
       coordY = coordY % currentMobilityStatus.getMaxY() ;

     currentMobilityStatus.getCurrentPosition().setX(coordX);
     currentMobilityStatus.getCurrentPosition().setY(coordY);

     tmpTime = (double)currentMobilityStatus.getMobileTime();
     //System.out.println("Current Mobile Time = "+tmpTime);
     tmpTime = tmpTime - sampleTime;

      currentMobilityStatus.setMobileTime(tmpTime);
      //System.out.println("-Event "+currentMobilityStatus.getId()+" at time "+currentTime+" Current Mobile Time = "+currentMobilityStatus.getMobileTime());
      currentMobilityStatus.setStartMobileTime(currentMobilityStatus.getLocalTime());
      //System.out.println("--Event "+currentMobilityStatus.getId()+" at time "+currentTime+" Current Mobile Time = "+currentMobilityStatus.getMobileTime());
    }
  }

  private void pause(MobilityStatus currentMobilityStatus, long currentTime) {
    //System.out.println("Event "+currentMobilityStatus.getId()+" at time "+currentTime+" in pause() function");
    double tmpTime ;
    double sampleTime = 1; // Sample Time in Seconds OR ms OR microseconds...

    updateCurrentTime(currentMobilityStatus, currentTime);

    if(currentMobilityStatus.getLocalTime() <= currentMobilityStatus.getEndPauseTime()) {
      sampleTime = currentMobilityStatus.getLocalTime() - currentMobilityStatus.getStartPauseTime();
      tmpTime = (double)currentMobilityStatus.getPauseTime();
      tmpTime = tmpTime - sampleTime;
      currentMobilityStatus.setPauseTime(tmpTime);
    }
    currentMobilityStatus.setTheta((double)0.0);
    currentMobilityStatus.setStartPauseTime(currentMobilityStatus.getLocalTime());
 }

  public boolean directMobility(MobilityStatus currentMobilityStatus, long currentTime) {
    int scenario = 999;
    boolean hasMoved = false;
    //System.out.println("Event "+currentMobilityStatus.getId()+" at time "+currentTime+" in directMobility() function");
    if ((currentMobilityStatus.getMovementStatus() == Preferences.MOBILE) &&
      (currentMobilityStatus.getMobileTime() != (double) 0)) // Node is Mobile , Still Mobile
      scenario = 0;
      if ((currentMobilityStatus.getMovementStatus() == Preferences.MOBILE) &&
        (currentMobilityStatus.getMobileTime() == (double) 0)) // Node is Mobile , Mobile timer Expired
        scenario = 1;
      if ((currentMobilityStatus.getMovementStatus() == Preferences.STATIONARY) &&
        (currentMobilityStatus.getPauseTime() != (double) 0)) // Node is Paused and Pause Timer not Expired
        scenario = 2;
      if ((currentMobilityStatus.getMovementStatus() == Preferences.STATIONARY) &&
        (currentMobilityStatus.getPauseTime() == (double) 0)) // Node is Paused but Pause Timer Expired
        scenario = 3;

     //System.out.println("For event "+currentMobilityStatus.getId()+" at time "+currentTime+" scenario = "+scenario);

      switch (scenario) {
        case 0:
          move(currentMobilityStatus, currentTime);
          hasMoved = true;
          break;

        case 1:
          // Deciding whether a particular node belongs to the static OR mobile group
          currentMobilityStatus.setMovementStatus(changeMobilityStatus(currentMobilityStatus));
          if (currentMobilityStatus.getMovementStatus() == Preferences.MOBILE)
            setMobile(currentMobilityStatus, currentTime); //Node is Mobile
          else
            setPause(currentMobilityStatus, currentTime); // Node is Paused
          break;

        case 2:
          pause(currentMobilityStatus, currentTime);
          break;

        case 3:
          // Deciding whether a particular node belongs to the static OR mobile group
          currentMobilityStatus.setMovementStatus(changeMobilityStatus(currentMobilityStatus));
           if (currentMobilityStatus.getMovementStatus() == Preferences.MOBILE)
             setMobile(currentMobilityStatus, currentTime); //Node is Mobile
           else
             setPause(currentMobilityStatus, currentTime); // Node is Paused
           break;

        default:
          break;
      } // End of Switch
      return hasMoved;
    }

}
