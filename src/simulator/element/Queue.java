package simulator.element;

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
import java.util.*;

class QueueNode {
   Object item;
   QueueNode link ;

  }


public class Queue
{

    private QueueNode front;

    private QueueNode rear;

    private int count = 0;


    public Queue() {};

   public boolean isEmpty()
    {
      return (count == 0);
     }

   public int length()
    {
      return count;
    }

   public void enqueue(Object newItem)
     {

       QueueNode temp = new QueueNode();

       temp.item = newItem;

       temp.link = null ;

       if (rear == null)
         {
            front = rear =  temp ;
          }
       else
         {
           rear.link = temp ;
           rear = temp;
         }
         count++;
      } //end Insert

      public Object dequeue()
        {
          if (count==0)
            return null;
          else
            {
               Object tempItem = front.item;
               front = front.link;
                if (front == null)
                  {
                    rear = null;
                  }
                  count--;

                  return tempItem;
             }
         }

      public Object checkFront()
       {
          if(count == 0)
            return null;
          else
           return front.item;
       }

}
