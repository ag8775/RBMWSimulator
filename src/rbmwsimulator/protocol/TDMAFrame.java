package rbmwsimulator.protocol;
import simulator.util.Output;
import rbmwsimulator.util.Preferences;
import java.util.Vector;
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
public class TDMAFrame {
   private double totalFrameDuration; //Total size/duration of the TDMA Frame, in seconds
   private double unallocatedFrameDuration; //Unallocated TDMA Frame duration to accomodate new subframes, in seconds ...
   private double slotDuration; //Smallest subframe or unit of allocation, in seconds
   private Vector startFrameSlotMarkersList; //Keeps a list of the starting slot markers for a subframe
   private Vector endFrameSlotMarkersList;//Keeps a list of the ending slot markers for a subframe
   private Vector numSubFrameSlotsList; //Keeps a list of the number of slots in each subframe

   //{#slots, slotDuration style} constructor ...
   public TDMAFrame(long totalFrameSlots_, double slotDuration_) {
     this.totalFrameDuration = totalFrameSlots_*slotDuration_;
     this.unallocatedFrameDuration = this.totalFrameDuration;
     this.slotDuration = slotDuration_;
     this.startFrameSlotMarkersList = new Vector();
     this.endFrameSlotMarkersList = new Vector();
     this.numSubFrameSlotsList = new Vector();
   }

  //{frame duration, slot duration} style constructor
   public TDMAFrame(double totalFrameDuration_, double slotDuration_) {
     this.totalFrameDuration = totalFrameDuration_;
     this.unallocatedFrameDuration = totalFrameDuration_;
     this.slotDuration = slotDuration_;
     this.startFrameSlotMarkersList = new Vector();
     this.endFrameSlotMarkersList = new Vector();
     this.numSubFrameSlotsList = new Vector();
   }

   public double getTotalFrameDuration() {
     return this.totalFrameDuration;
   }

   public long getTotalFrameSlots() {
     if(this.slotDuration != 0)
       return Math.round(this.totalFrameDuration/this.slotDuration);
     return -1;
   }

   public long getSlotStartMarkerIndexForSubFrame(int subFrameIndex) {
     if(subFrameIndex < this.startFrameSlotMarkersList.size())
       return ((Long)(this.startFrameSlotMarkersList.elementAt(subFrameIndex))).longValue();
     return -1;
   }

   public long getSlotEndMarkerIndexForSubFrame(int subFrameIndex) {
     if(subFrameIndex < this.endFrameSlotMarkersList.size())
       return ((Long)(this.endFrameSlotMarkersList.elementAt(subFrameIndex))).longValue();
     return -1;
   }

   public long getNumSlotsForSubFrame(int subFrameIndex) {
     if(subFrameIndex < this.numSubFrameSlotsList.size())
       return ((Long)(this.numSubFrameSlotsList.elementAt(subFrameIndex))).longValue();
     return -1;
   }

   private boolean isTDMAFrameAvailableForAllocatingSubframe(double subFrameDuration) {
     if(subFrameDuration <= unallocatedFrameDuration)
       return true;
     return false;
   }

  public void addSubFrame(int subFrameIndex, long numSlots) {
    if(!addSubFrameEndSlotMarkers(subFrameIndex, numSlots))
      addSubFrameEndSlotMarkersOutOfFrameLength(subFrameIndex, numSlots);
  }

   private boolean addSubFrameEndSlotMarkers(int subFrameIndex, long numSlots) {
     return addSubFrameEndSlotMarkers(subFrameIndex, numSlots*this.slotDuration);
   }

   private void addSubFrameEndSlotMarkersOutOfFrameLength(int subFrameIndex, long numSlots) {
     addSubFrameEndSlotMarkersOutOfFrameLength(subFrameIndex, numSlots*this.slotDuration);
   }

   private boolean addSubFrameEndSlotMarkers(int subFrameIndex, double subFrameDuration) {
     long startingSubFrameSlotMarker;
     long endingSubFrameSlotMarker;
     long numSubFrameSlots;
     //SlotMarker Vectors keep track of the start and the end slots of the subFrame(s)
     //Here, the first subframe naturally starts from slot 0
     //Whereas the other subframes indexed from 1 onwards will have their startSlotMarkers from the end+1 of the previous subframe
     if(isTDMAFrameAvailableForAllocatingSubframe(subFrameDuration)) {
       if(subFrameIndex == 0) {
         startingSubFrameSlotMarker = 0;
       } else {
          startingSubFrameSlotMarker = ((Long)(this.endFrameSlotMarkersList.elementAt(subFrameIndex-1))).longValue() + 1;
       }
       numSubFrameSlots = Math.round(subFrameDuration/this.slotDuration);
       endingSubFrameSlotMarker = startingSubFrameSlotMarker + numSubFrameSlots - 1; //0+10 => 0 to 9, therefore subtract by 1
       this.startFrameSlotMarkersList.addElement(new Long(startingSubFrameSlotMarker));
       this.endFrameSlotMarkersList.addElement(new Long(endingSubFrameSlotMarker));
       this.numSubFrameSlotsList.addElement(new Long(numSubFrameSlots));
       this.unallocatedFrameDuration -= subFrameDuration;
       return true;
     }
     return false;
   }

   private void addSubFrameEndSlotMarkersOutOfFrameLength(int subFrameIndex, double subFrameDuration) {
      long startingSubFrameSlotMarker;
      long endingSubFrameSlotMarker;
      long numSubFrameSlots;
      if(!isTDMAFrameAvailableForAllocatingSubframe(subFrameDuration)) {
        //First increase the totalFrameDuration
        this.totalFrameDuration+=subFrameDuration;
        if(subFrameIndex == 0) {
          startingSubFrameSlotMarker = 0;
        } else {
          startingSubFrameSlotMarker = ((Long)(this.endFrameSlotMarkersList.elementAt(subFrameIndex-1))).longValue() + 1;
        }
        numSubFrameSlots = Math.round(subFrameDuration/this.slotDuration);
        endingSubFrameSlotMarker = startingSubFrameSlotMarker + numSubFrameSlots - 1; //0+10 => 0 to 9, therefore subtract by 1
        this.startFrameSlotMarkersList.addElement(new Long(startingSubFrameSlotMarker));
        this.endFrameSlotMarkersList.addElement(new Long(endingSubFrameSlotMarker));
        this.numSubFrameSlotsList.addElement(new Long(numSubFrameSlots));
        //set the unallocatedFrameDuration to zero, as we have none available to allocate for any more subframes ...
        this.unallocatedFrameDuration = 0;
      } else
         addSubFrameEndSlotMarkers(subFrameIndex, subFrameDuration);
   }

   public void printTDMAFrameDetails() {
     int numSubframes = this.startFrameSlotMarkersList.size();
     Output.SIMINFO("TDMAFrame: Total duration = "+this.totalFrameDuration+", slot duration = "+this.slotDuration+" unallocated duration = "+this.unallocatedFrameDuration +this.startFrameSlotMarkersList.size()+" subframes ...", Preferences.PRINT_TDMA_FRAME_DETAILS);
     for(int subframeIndex = 0; subframeIndex < numSubframes; subframeIndex++) {
       printSubframeDetails(subframeIndex);
     }
   }

   public void printSubframeDetails(int subframeIndex) {
     Output.SIMINFO("TDMASubframe: Total slots = "+((Long)(this.numSubFrameSlotsList.elementAt(subframeIndex))).longValue()+", frame slot markers = {"+((Long)(this.startFrameSlotMarkersList.elementAt(subframeIndex))).longValue()+", "+((Long)(this.endFrameSlotMarkersList.elementAt(subframeIndex))).longValue() +"}", Preferences.PRINT_TDMA_FRAME_DETAILS);
   }
}
