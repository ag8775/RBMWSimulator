package rbmwsimulator.protocol.metric;
import rbmwsimulator.util.TopologyReader;
import rbmwsimulator.protocol.metric.SensingCell;
import rbmwsimulator.util.Preferences;

import java.util.*;
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
 * @version 2.0
 */
public class CumulativeSensingDegreeMetric {
    private int myId;
    private Vector myNeighbors;
    private int sensingRange;
    private int sensingCellDimension;
    private double myCSD;
    private double maxCSD;
    private TopologyReader topology;
    private long xCoord, yCoord;
    private SensingCell quad_1[], quad_2[], quad_3[], quad_4[];
    private long numCells;
    private double d_cs_min;
    private double percentageCoverage;
    private int numXpartitions, numYpartitions;

    public CumulativeSensingDegreeMetric(int nodeId, int sensingRange, int sensingCellDimension, Vector neighbors, TopologyReader topo) {
      this.myId = nodeId;
      this.myNeighbors = neighbors;
      //Output.SIMINFO("%%%%%%%%%%%%%% For node "+nodeId+" neighbors size "+neighbors.size(), Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      this.topology = topo;
      this.xCoord = this.topology.xPos(this.myId);
      this.yCoord = this.topology.yPos(this.myId);
      this.sensingRange = sensingRange;
      this.sensingCellDimension = sensingCellDimension;
      //Output.SIMINFO("Calculating Quads for Node "+this.myId+" with coordinates ("+this.xCoord+", "+this.yCoord+") ...", Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      this.d_cs_min = (Math.sqrt(2))*(this.sensingCellDimension/2);//Math.sqrt(2*Math.pow((((double)this.sensingCellDimension)/2), 2));
      this.numXpartitions = this.numYpartitions = (this.sensingRange/this.sensingCellDimension);
      //Output.SIMINFO("SensingRange = "+this.sensingRange+" Cell Dim = "+this.sensingCellDimension+" d_cs_min = "+ this.d_cs_min, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      this.quad_1 = computeSensingCellsForQuadrant(1);
      this.quad_2 = computeSensingCellsForQuadrant(2);
      this.quad_3 = computeSensingCellsForQuadrant(3);
      this.quad_4 = computeSensingCellsForQuadrant(4);
      //Output.SIMINFO("For Node "+this.myId+" Quad Lengths 1: "+this.quad_1.length+" 2: "+this.quad_2.length+" 3: "+this.quad_3.length+" 4: "+this.quad_4.length, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      this.numCells = this.quad_1.length+this.quad_2.length+this.quad_3.length+this.quad_4.length;
    }

    private SensingCell[] computeSensingCellsForQuadrant(int quad) {
      //Output.SIMINFO("*** Going for Quad Number "+quad, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      Vector quadrantCells = new Vector();
      long d_cs_factor;
      int spv;
      //Output.SIMINFO("For quad "+quad+" numXpartitions = "+this.numXpartitions+" numYpartitions = "+this.numYpartitions, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      for(int i = 0; i < numXpartitions; i++)
        for(int j = 0; j < numYpartitions; j++) {
          long squareX, squareY;
          double d_cs; //Distance between center of the circle and that of the square cell
          /*
                  |
              4   |  1
          --------|-------
              3   |  2
                  |
          */
          switch(quad) {
            case 1:
              squareX = this.xCoord + ((this.sensingCellDimension/2)*(2*i + 1));
              squareY = this.yCoord - ((this.sensingCellDimension/2)*(2*j + 1));
              break;
            case 2:
              squareX = this.xCoord + ((this.sensingCellDimension/2)*(2*i + 1));
              squareY = this.yCoord + ((this.sensingCellDimension/2)*(2*j + 1));
              break;
            case 3:
              squareX = this.xCoord - ((this.sensingCellDimension/2)*(2*i + 1));
              squareY = this.yCoord + ((this.sensingCellDimension/2)*(2*j + 1));
              break;
            case 4:
              squareX = this.xCoord - ((this.sensingCellDimension/2)*(2*i + 1));
              squareY = this.yCoord - ((this.sensingCellDimension/2)*(2*j + 1));
              break;
            default:
              squareX = this.xCoord;
              squareY = this.yCoord;
              break;
          }
          if((squareX > 0)&&(squareY > 0)) {
            d_cs = Math.sqrt(Math.pow((squareX-xCoord), 2) + Math.pow((squareY-yCoord), 2));
            d_cs_factor = Math.round(Math.sqrt((2*(i*i + j*j + i + j)+1)));
            spv = (int)d_cs_factor;
            if(d_cs <= this.sensingRange) {
              quadrantCells.addElement(new SensingCell(this.myId, squareX, squareY, d_cs, spv));
              if(spv != (int)Math.round(d_cs/d_cs_min))
              Output.SIMINFO("For cell ("+i+", "+j+") at ("+squareX+", "+squareY+") d_cs_min = "+d_cs_min+" d_cs = "+d_cs+" d_cs_factor = "+d_cs_factor+" OR "+Math.round(d_cs/d_cs_min)+" spv = "+spv, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
            }
          }
        }
        return sortSensingCells(quadrantCells);
    }

    private SensingCell[] sortSensingCells(Vector b) {
      SensingCell sorted[] = new SensingCell[b.size()];
      b.copyInto(sorted);
     // sorted = (b.toArray());
      SensingCell hold = new SensingCell(); // temporary holding area for swap

      for (int pass = 1; pass < sorted.length ; pass++)
        for(int i = 0 ; i < sorted.length - 1 ; i++)
          if(sorted[i].getDistance() > sorted[i+1].getDistance()) {
           hold = sorted[i];
           sorted[i] = sorted[i+1];
           sorted[i+1] = hold;
          }
      return sorted;
    }

    public Vector getSensingRegion() {
        Vector sensingQuads = new Vector();
        sensingQuads.addElement(this.quad_1);
        sensingQuads.addElement(this.quad_2);
        sensingQuads.addElement(this.quad_3);
        sensingQuads.addElement(this.quad_4);
        return sensingQuads;
    }

    public void updateMatchingCellsIncludingAllNeighbors() {
      for(int quad_no = 1; quad_no < 5; quad_no++) {
        SensingCell quad[];
        switch(quad_no) {
          case 1:
            quad = this.quad_1;
            break;
          case 2:
            quad = this.quad_2;
            break;
          case 3:
            quad = this.quad_3;
            break;
          case 4:
            quad = this.quad_4;
            break;
          default:
            quad = this.quad_1; // default option ...
            break;
        }
        for(int cell = 0; cell < quad.length; cell++) {
          for(int index = 0; index < this.myNeighbors.size(); index++) {
            int neighId = ((Integer)(this.myNeighbors.elementAt(index))).intValue();
            long neighX = this.topology.xPos(neighId);
            long neighY = this.topology.yPos(neighId);
            //Output.SIMINFO("^^^^^^^^^^^^^ for node "+this.myId+" quad "+quad_no+"'s cell has initial SPV = "+quad[cell].getInitialSPV(), Preferences.PRINT_METRIC_CALCULATION_DETAILS);
            if(quad[cell].getInitialSPV() > 0) {
              double d_cs = Math.sqrt(Math.pow((neighX-quad[cell].getCoordX()), 2) + Math.pow((neighY-quad[cell].getCoordY()), 2));
              //Output.SIMINFO("$$$$$$$$$$$$$$$$$ d_cs = "+d_cs+" for cell in quad "+quad_no+" for node "+this.myId, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
              if(d_cs <= this.sensingRange) {
                int spv = (int)Math.round(d_cs/d_cs_min);
                quad[cell].addMatchingCell(new SensingCell(neighId, d_cs, spv));
              }
            }
          } // end of for neighbors ...
        } // end of for cells
      } // end of for 4 quads ...
    }

   public double calculateSolitaryCSD() {
     double solitaryCSD = 0;

     for(int quad_no = 1; quad_no < 5; quad_no++) {
        SensingCell quad[];
        switch(quad_no) {
          case 1:
            quad = this.quad_1;
            break;
          case 2:
            quad = this.quad_2;
            break;
          case 3:
            quad = this.quad_3;
            break;
          case 4:
            quad = this.quad_4;
            break;
          default:
            quad = this.quad_1; // default option ...
            break;
        }
        for(int cell = 0; cell < quad.length; cell++) {
            if(quad[cell].getInitialSPV() > 0)
              solitaryCSD+=quad[cell].getInitialSPV();
        }
      }
     return solitaryCSD;
   }

   public double getIdealCSD() {
    double idealCSD = 0;
    for(int quad_no = 1; quad_no < 5; quad_no++) {
       SensingCell quad[];
       switch(quad_no) {
         case 1:
           quad = this.quad_1;
           break;
         case 2:
           quad = this.quad_2;
           break;
         case 3:
           quad = this.quad_3;
           break;
         case 4:
           quad = this.quad_4;
           break;
         default:
           quad = this.quad_1; // default option ...
           break;
       }
       // ideal SPV for a sensingCell is ONE ... So,
       // idealCSD should be the total number of
       // SensingCells for a SensorNode
       idealCSD+=quad.length;
    }
    return idealCSD;
   }

   public double calculateSomeCSD() {
     double someCSD = 0;
     for(int quad_no = 1; quad_no < 5; quad_no++) {
       SensingCell quad[];
       switch(quad_no) {
         case 1:
           quad = this.quad_1;
           break;
         case 2:
           quad = this.quad_2;
           break;
         case 3:
           quad = this.quad_3;
           break;
         case 4:
           quad = this.quad_4;
           break;
         default:
           quad = this.quad_1; // default option ...
           break;
       }
       for(int cell = 0; cell < quad.length; cell++) {
         if(quad[cell].getInitialSPV() > 0) {
           quad[cell].cspvWithAllNeighbors();
           //Output.SIMINFO("NodeId "+this.myId+" MAX CSPV = "+quad[cell].getMaxCSPV(), Preferences.PRINT_METRIC_CALCULATION_DETAILS);
           someCSD+=quad[cell].getMaxCSPV();
         }
       }
     }
     return someCSD;
   }

   public double calculateSomeCSD(Vector neighbors) {
     double someCSD = 0;
     for(int quad_no = 1; quad_no < 5; quad_no++) {
       SensingCell quad[];
       switch(quad_no) {
         case 1:
           quad = this.quad_1;
           break;
         case 2:
           quad = this.quad_2;
           break;
         case 3:
           quad = this.quad_3;
           break;
         case 4:
           quad = this.quad_4;
           break;
         default:
           quad = this.quad_1; // default option ...
           break;
       }
       for(int cell = 0; cell < quad.length; cell++) {
         if(quad[cell].getInitialSPV() > 0) {
           quad[cell].cspvWithNeighbors(neighbors);
           //Output.SIMINFO("NodeId "+this.myId+" CSPV = "+quad[cell].getCSPV(), Preferences.PRINT_METRIC_CALCULATION_DETAILS);
           someCSD+=quad[cell].getCSPV();
         }
       }
     }
     return someCSD;
   }


   public double calculatePercentageCoverage(Vector neighbors) {
      double someCSD = calculateSomeCSD(neighbors);
      double solitaryCSD = calculateSolitaryCSD();
      double idealCSD = getIdealCSD();
      //Output.SIMINFO("**For Node "+this.myId+": idealCSD = "+idealCSD+" solitaryCSD = "+solitaryCSD+" someCSD = "+someCSD+" (solitaryCSD - someCSD) = "+(solitaryCSD - someCSD)+" (1.0 - (someCSD/solitaryCSD))*100 = "+(1.0 - (someCSD/solitaryCSD))*100, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      //this.percentageCoverage = (someCSD - idealCSD)/solitaryCSD; // case-1
      //this.percentageCoverage = (1.0 - (someCSD/solitaryCSD))*100; // case-2
      //this.percentageCoverage = (someCSD/solitaryCSD)*100; // case-3
      this.percentageCoverage = (1.0 - ((someCSD - idealCSD)/(solitaryCSD - idealCSD)))*100; // case-4
      this.percentageCoverage = ((double)(Math.round(this.percentageCoverage*100)))/((double)100);
      return this.percentageCoverage;
    }

   public double calculatePercentageCoverage() {
      double someCSD = calculateSomeCSD();
      double solitaryCSD = calculateSolitaryCSD();
      double idealCSD = getIdealCSD();
      //Output.SIMINFO("**For Node "+this.myId+": idealCSD = "+idealCSD+" solitaryCSD = "+solitaryCSD+" someCSD = "+someCSD+" (solitaryCSD - someCSD) = "+(solitaryCSD - someCSD)+" (1.0 - (someCSD/solitaryCSD))*100 = "+(1.0 - (someCSD/solitaryCSD))*100, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      //this.percentageCoverage = (someCSD - idealCSD)/solitaryCSD; // case-1
      //this.percentageCoverage = (1.0 - (someCSD/solitaryCSD))*100; // case-2
      //this.percentageCoverage = (someCSD/solitaryCSD)*100; // case-3
      this.percentageCoverage = (1.0 - ((someCSD - idealCSD)/(solitaryCSD - idealCSD)))*100; // case-4
      this.percentageCoverage = ((double)(Math.round(this.percentageCoverage*100)))/((double)100);
      return this.percentageCoverage;
    }

   public double getPercentageCoverage() {
     return this.percentageCoverage;
   }

    public void calculateMaxCSD() {
      //Output.SIMINFO("Calculating Max CSD for node "+this.myId, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      double avgCSD = 0;
      int numCellsIncluded = 0;
      for(int quad_no = 1; quad_no < 5; quad_no++) {
        SensingCell quad[];
        switch(quad_no) {
          case 1:
            quad = this.quad_1;
            break;
          case 2:
            quad = this.quad_2;
            break;
          case 3:
            quad = this.quad_3;
            break;
          case 4:
            quad = this.quad_4;
            break;
          default:
            quad = this.quad_1; // default option ...
            break;
        }
        for(int cell = 0; cell < quad.length; cell++) {
            if(quad[cell].getInitialSPV() > 0) {
              quad[cell].cspvWithAllNeighbors();
              Output.SIMINFO("NodeId "+this.myId+" MAX CSPV = "+quad[cell].getMaxCSPV(), Preferences.PRINT_METRIC_CALCULATION_DETAILS);
              avgCSD+=quad[cell].getMaxCSPV();
              numCellsIncluded++;
            }
        }
      }
      Output.SIMINFO("NodeId "+this.myId+" has Sum of CSDs of Cells "+avgCSD+" and number of cells included "+numCellsIncluded, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
     avgCSD = avgCSD/(numCellsIncluded);
     this.maxCSD = avgCSD;
     //Output.SIMINFO("Calculated Max CSD for node "+this.myId+" including "+numCellsIncluded+" cells");
    } //end of calculateMaxCSD

    public void calculateCSDWithNeighbors(Vector neighbors) {
      //Output.SIMINFO("Calculating selective CSD for node "+this.myId, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
      double avgCSD = 0;
      int numCellsIncluded = 0;
      for(int quad_no = 1; quad_no < 5; quad_no++) {
        SensingCell quad[];
        switch(quad_no) {
          case 1:
            quad = this.quad_1;
            break;
          case 2:
            quad = this.quad_2;
            break;
          case 3:
            quad = this.quad_3;
            break;
          case 4:
            quad = this.quad_4;
            break;
          default:
            quad = this.quad_1; // default option ...
            break;
        }
        for(int cell = 0; cell < quad.length; cell++) {
         if(quad[cell].getInitialSPV() > 0) {
           quad[cell].cspvWithNeighbors(neighbors);
           avgCSD+=quad[cell].getCSPV();
           numCellsIncluded++;
         }
        }
      }
      avgCSD = avgCSD/(numCellsIncluded);
      this.myCSD = avgCSD;
      //Output.SIMINFO("Calculated selective CSD for node "+this.myId+" including "+numCellsIncluded+" cells", Preferences.PRINT_METRIC_CALCULATION_DETAILS);
    } //end of calculateCSDWithNeighbors


    public void printSensingRegion() {
      for(int quad_no = 1; quad_no < 5; quad_no++) {
        SensingCell[] quad;

        switch(quad_no) {
          case 1:
            quad = this.quad_1;
            break;
          case 2:
            quad = this.quad_2;
            break;
          case 3:
            quad = this.quad_3;
            break;
          case 4:
            quad = this.quad_4;
            break;
          default:
            quad = this.quad_1; // default option ...
            break;
        }
        int quadLength = quad.length-1;
        String str = new String();
        if(quadLength > 0) {
         str +="Node "+this.myId+" Sensing Quad "+quad_no+": {";
         //Output.SIMINFO("Node :"+this.myId+" quad "+quad_no+" has length "+this.quad_1.length+" and last element is "+quadLength);
         for(int cell = 0; cell < quadLength; cell++) {
           str += new String(quad[cell].getCSPV()+",");
         }
         str+= new String(quad[quadLength].getCSPV()+"}");
         Output.SIMINFO(str, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
        }
      }
    }

    public void printMaxSensingRegion() {
        for(int quad_no = 1; quad_no < 5; quad_no++) {
          SensingCell[] quad;

          switch(quad_no) {
            case 1:
              quad = this.quad_1;
              break;
            case 2:
              quad = this.quad_2;
              break;
            case 3:
              quad = this.quad_3;
              break;
            case 4:
              quad = this.quad_4;
              break;
            default:
              quad = this.quad_1; // default option ...
              break;
          }
          int quadLength = quad.length-1;
          String str = new String();
          if(quadLength > 0) {
           str+="Node-"+this.myId+"-Sensing-Quad-"+quad_no+": {";
           //Output.SIMINFO("Node :"+this.myId+" quad "+quad_no+" has length "+this.quad_1.length+" and last element is "+quadLength, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
           for(int cell = 0; cell < quadLength; cell++) {
             str+= new String(quad[cell].getMaxCSPV()+",");
           }
           str+=new String(quad[quadLength].getMaxCSPV()+"}");
           Output.SIMINFO(str, Preferences.PRINT_METRIC_CALCULATION_DETAILS);
          }
        }
      }

    public double maxCSD() {
      return  this.maxCSD;
    }

    public double myCSD() {
      return this.myCSD;
    }

  }
