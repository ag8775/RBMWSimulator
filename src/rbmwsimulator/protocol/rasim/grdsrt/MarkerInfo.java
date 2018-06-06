package rbmwsimulator.protocol.rasim.grdsrt;

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
public class MarkerInfo {

        private int myId;
        private int markers[];
        private int hierarchy_level;
        private int nLevels;

        public MarkerInfo(int id) {
          this.myId = id;
          this.hierarchy_level = 0;
          this.nLevels = 150;
          this.markers = new int[this.nLevels];
          for (int i = 0; i < this.nLevels; i++)
            this.markers[i] = -1;
        }


        public int getMarkValueAt(int level) {
          return this.markers[level];
        }

        public boolean isMarked(int level) {
          if (this.markers[level] > 0)
            return true;
          return false;
        }

        public void markme(int level) {
          this.markers[level] = 1;
          this.nLevels = level;
        }

        public void unmarkme(int level) {
          this.markers[level] = -1;
        }

        public int getCurrentLevel() {
          return this.nLevels;
        }


        public int whoAmI() {
                return this.myId;
        }
}
