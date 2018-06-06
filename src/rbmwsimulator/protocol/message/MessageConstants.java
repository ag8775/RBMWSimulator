package rbmwsimulator.protocol.message;

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
public class MessageConstants {
 // Bit-flag variant of traditional int enum pattern
 public static final int HELLO            = 1;  // 1 => Hello Message for neighbor discovery ...
 public static final int HELLO_REPLY      = 2;  // 2 => Hello Message reply for Hello ...
 public static final int QUERY            = 3;  // 3 => Query
 public static final int QUERY_HIT        = 4;  // 4 => Query Hit
 public static final int DATA_TRANSFER    = 5;  // 5 => Data Transfer ...
 public static final int HEART_BEAT       = 6;  // 6 => HeartBeat Message to detect lost neighbors and new Neighbors ...

 // Minimum-Maximum RBMW Protocol Message Sizes in multiple of MTU's
 public static final int HELLO_MSG_SIZE_MULTIPLE = 2;
 public static final int HELLO_REPLY_MSG_SIZE_MULTIPLE = 5;

 public static int getMessageSize(int msgType) {
   int msgSize = 0;

   switch(msgType) {
     case HELLO:
       msgSize = HELLO_MSG_SIZE_MULTIPLE;
       break;
     case HELLO_REPLY:
       msgSize = HELLO_REPLY_MSG_SIZE_MULTIPLE;
       break;
   }
   return msgSize;
 }


}
