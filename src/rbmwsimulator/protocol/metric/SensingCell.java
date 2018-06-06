package rbmwsimulator.protocol.metric;

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
 * @version 2.0
 */
public class SensingCell {

    private int initialSensingProximityValue;
    private int cumulativeSPV;
    private int maxCSPV;
    private int myId;
    private Vector matchingCells;
    private long xCoord, yCoord;
    private double d_cs;
    private Vector equalSPVs;
    private Vector unequalSPVs;

    public SensingCell() {}

    public SensingCell(int nodeId, long xCoord, long yCoord) {
        this.myId = nodeId;
        this.matchingCells = new Vector();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.matchingCells = new Vector();
    }

    public SensingCell(int nodeId, double d_cs, int spv) {
        this.myId = nodeId;
        this.d_cs = d_cs;
        this.cumulativeSPV = this.initialSensingProximityValue = spv;
        this.xCoord = -1;
        this.yCoord = -1;
        this.matchingCells = new Vector();
    }

    public SensingCell(int nodeId, long xCoord, long yCoord, double d_cs) {
        this.myId = nodeId;
        this.matchingCells = new Vector();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        // this.d_cs = (((double)(Math.round(d_cs*10000)))/(double)10000);
        this.d_cs = d_cs;
        //System.out.println("Node :"+this.myId+" d_cs = "+d_cs);
    }

    public SensingCell(int nodeId, long xCoord, long yCoord, double d_cs, int spv) {
        this.myId = nodeId;
        this.matchingCells = new Vector();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        // this.d_cs = (((double)(Math.round(d_cs*10000)))/(double)10000);
        this.d_cs = d_cs;
        this.cumulativeSPV = this.initialSensingProximityValue = spv;
    }


    public int getNodeId() {
        return this.myId;
    }

    public long getCoordX() {
        return this.xCoord;
    }

    public long getCoordY() {
        return this.yCoord;
    }

    public double getDistance() {
        return this.d_cs;
    }

    public int getInitialSPV() {
        return this.initialSensingProximityValue;
    }

    public void setInitialSPV(int val) {
        this.initialSensingProximityValue = val;
        this.cumulativeSPV = val;
    }

    public int getCSPV() {
        return this.cumulativeSPV;
    }

    public void addMatchingCell(SensingCell cell) {
        this.matchingCells.addElement(cell);
    }

    public Vector getMatchingCells() {
        return this.matchingCells;
    }

    public int numMatchingCells() {
        return this.matchingCells.size();
    }

    public void cspvWithAllNeighbors() {
        //System.out.println("Finding Nodes CSPV using all its neighbors ...");
        Vector includeSPVs = new Vector();
        int spvEff;
        for (int i = 0; i < this.matchingCells.size(); i++) {
            includeSPVs.addElement(new Integer(((SensingCell) (this.
                    matchingCells.elementAt(i))).getInitialSPV()));
        }

        //spvEff = usingInverseSecondOrderReductionTechnique(includeSPVs);
        //spvEff = averageReductionTechnique(includeSPVs);
        spvEff = usingReciprocalReductionTechnique(includeSPVs);
        if (spvEff < this.initialSensingProximityValue) {
            this.maxCSPV = spvEff;
        } else {
            this.maxCSPV = this.initialSensingProximityValue;
        }
        //System.out.println("Node Id "+this.myId+" has maxCSPV = "+this.maxCSPV+" and has an initial SPV = "+this.initialSensingProximityValue);
    }

    public void cspvWithNeighbors(Vector neighbors) {
        //System.out.println("Finding Nodes CSPV using some neighbors...");
        Vector includeSPVs = new Vector();
        int spvEff;
        for (int i = 0; i < this.matchingCells.size(); i++) {
            if (neighbors.contains(new Integer(((SensingCell) (this.
                    matchingCells.elementAt(i))).getNodeId()))) {
                includeSPVs.addElement(new Integer(((SensingCell) (this.
                        matchingCells.elementAt(i))).getInitialSPV()));
            }
        }

        //spvEff = usingInverseSecondOrderReductionTechnique(includeSPVs);
        //spvEff = averageReductionTechnique(includeSPVs);
        spvEff = usingReciprocalReductionTechnique(includeSPVs);
        if (spvEff < this.initialSensingProximityValue) {
            this.cumulativeSPV = spvEff;
        } else {
            this.cumulativeSPV = this.initialSensingProximityValue;
        }
    }

    private float averageUnequalSPVs(Vector unequalSPVs) {
        // System.out.println("Average CSPV calculation for unequal SPVs...");
        //System.out.println("UnEqualSPVs "+printSPVs(unequalSPVs));
        int cspv = 0;
        for (int i = 0; i < unequalSPVs.size(); i++) {
            cspv += ((Integer) (unequalSPVs.elementAt(i))).intValue();
        }
        return (((float) cspv) / ((float) unequalSPVs.size()));
    }

    private float reduceEqualSPVs(Vector equalSPVs) {
        //System.out.println("Reduction technique for CSPV calculation for equal SPVs...");
        float cspv = 0;
        for (int index = 0; index < equalSPVs.size(); index++) {
            Vector eSPVs = (Vector) (equalSPVs.elementAt(index));
            //System.out.println("EqualSPVs "+printSPVs(eSPVs));
            float spv = (float) ((Integer) (eSPVs.elementAt(0))).intValue();
            int numEqual = eSPVs.size();
            if (numEqual > 1) {
                //cspv += (spv + Math.round(((float)(numEqual-1))*((float)(1/spv))));
                float spvEff = (spv - (((float) numEqual) / ((float) 3)));
                if (spvEff <= 0) { // Just to make sure that we dont have a negative SPV value... Any negative value is equivalent to having an SPV of 1
                    spvEff = 1;
                }
                // if(spvEff >=0)
                cspv += spvEff;
                //System.out.println("NodeId "+this.myId+" with "+numEqual+" times spv "+spv+" has an Effective spv "+spvEff);
            }
        }
        //System.out.println("Equal CSPV = "+cspv);
        return (cspv / ((float) equalSPVs.size()));
    }

    private int averageReductionTechnique(Vector includeSPVs) {
        //System.out.println("Using the Average Reduction Technique for calculating cumulative SPV...");
        //  System.out.println("IncludeSPVs "+printSPVs(includeSPVs));
        includeSPVs.addElement(new Integer(this.initialSensingProximityValue));
        if (!includeSPVs.isEmpty()) {
            parseEqualUnequalSPVs(includeSPVs);
            float avgSPVs = averageUnequalSPVs(unequalSPVs);
            float redSPVs = reduceEqualSPVs(equalSPVs);
            if ((avgSPVs > 0) && (redSPVs > 0)) {
                return Math.round((avgSPVs + redSPVs) / 2);
            }
            if (avgSPVs > 0) {
                return Math.round(avgSPVs);
            }
            if (redSPVs > 0) {
                return Math.round(redSPVs);
            }
        } //end of  if(!includeSPVs.isEmpty())
        return this.initialSensingProximityValue;
    }

    public Vector bubbleSortSPVs(Vector spvs) {
        Integer sorted[] = new Integer[spvs.size()];
        spvs.copyInto(sorted);

        int hold; // temporary holding area for swap
        for (int pass = 1; pass < sorted.length; pass++) {
            for (int i = 0; i < sorted.length - 1; i++) {
                if (sorted[i].intValue() > sorted[i + 1].intValue()) {
                    hold = sorted[i].intValue();
                    sorted[i] = sorted[i + 1];
                    sorted[i + 1] = new Integer(hold);
                }
            }
        }

        Vector includeSPVs = new Vector();
        for (int i = 0; i < sorted.length; i++) {
            includeSPVs.addElement(sorted[i]);
        }

        return includeSPVs;
    }

    public void parseEqualUnequalSPVs(Vector includeSPVs) {
        equalSPVs = new Vector();
        unequalSPVs = new Vector();
        includeSPVs = bubbleSortSPVs(includeSPVs);
        //System.out.println(printSPVs(includeSPVs));
        while (!includeSPVs.isEmpty()) {
            int element = ((Integer) (includeSPVs.elementAt(0))).intValue();
            includeSPVs.removeElementAt(0);
            if (includeSPVs.contains(new Integer(element))) {
                Vector spvs = new Vector();
                for (int i = 0; i < includeSPVs.size(); i++) {
                    if (((Integer) (includeSPVs.elementAt(i))).intValue() ==
                        element) {
                        spvs.addElement(new Integer(element));
                    }
                }
                if (!spvs.isEmpty()) {
                    spvs.addElement(new Integer(element));
                    includeSPVs.removeAll(spvs);
                    equalSPVs.addElement(spvs);
                }
            } else {
                unequalSPVs.addElement(new Integer(element));
            }
        }

        /*  for(int i = 0; i < equalSPVs.size(); i++)
         System.out.println("EEEEE "+printSPVs((Vector)(equalSPVs.elementAt(i))));

          System.out.println("UUUUU "+printSPVs(unequalSPVs));
         */
    }

    public int usingReciprocalReductionTechnique(Vector includeSPVs) {
        float delta = 0;
        if (!includeSPVs.isEmpty()) {
            for (int i = 0; i < includeSPVs.size(); i++) {
                delta += ((float) 1.0) /
                        ((float) (((Integer) (includeSPVs.elementAt(i))).
                                  intValue()));
            }
            int cspv = (int) (Math.round(((float) (this.
                    initialSensingProximityValue)) - delta));
            if (cspv < 1) {
                return 1;
            } else {
                return cspv;
            }
        }
        return this.initialSensingProximityValue;
    }

    public int usingInverseSecondOrderReductionTechnique(Vector includeSPVs) {
        float cspv = 0;
        includeSPVs.addElement(new Integer(this.initialSensingProximityValue));
        // First the unequal Ones ...
        if (!includeSPVs.isEmpty()) {
            parseEqualUnequalSPVs(includeSPVs);

            for (int i = 0; i < unequalSPVs.size(); i++) {
                cspv +=
                        Math.pow(0.5, ((Integer) (unequalSPVs.elementAt(i))).intValue());
            }
            for (int i = 0; i < equalSPVs.size(); i++) {
                Vector spvs = (Vector) (equalSPVs.elementAt(i));
                int numEquals = spvs.size();
                cspv += numEquals *
                        (Math.pow(0.5, ((Integer) (spvs.elementAt(0))).intValue()));
            }
            //System.out.println("CSPV = "+cspv);
            return (int) (Math.round(Math.log(cspv) / Math.log((double) 0.5)));
        }
        return this.initialSensingProximityValue;
    }

    public Vector listAllSPVs() {
        Vector includeSPVs = new Vector();
        for (int i = 0; i < this.matchingCells.size(); i++) {
            includeSPVs.addElement(new Integer(((SensingCell) (this.
                    matchingCells.elementAt(i))).getInitialSPV()));
        }
        includeSPVs.addElement(new Integer(this.initialSensingProximityValue));
        //System.out.println(printSPVs(includeSPVs));
        return includeSPVs;
    }

    private String printSPVs(Vector spvVector) {
        Enumeration spvEnum = spvVector.elements();
        String str = new String();
        if (spvVector.size() != 0) {
            while (spvEnum.hasMoreElements()) {
                str += " " + ((Integer) spvEnum.nextElement()).toString() + ",";
            }
            return ("Node " + this.myId + ": has spvs " + str);
        }
        return ("Node " + this.myId + ": has *NO* spvs");
    }


    public int getMaxCSPV() {
        return this.maxCSPV;
    }
} // end of SensingCell class
