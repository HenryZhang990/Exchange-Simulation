package hw3.OrderGenerator;

import hw3.NewOrderImpl;
import hw3.OrderCxRImpl;
import java.util.Iterator;
import java.util.LinkedList;

public class OrdersIterator {

    public OrdersIterator() {
    }

    public static Iterator<Message> getIterator() {

        LinkedList<Message> msgs = new LinkedList<Message>();
        msgs.add(new NewOrderImpl("IBM", "ABC1", 1000, 100.00));
        msgs.add(new NewOrderImpl("MSFT","XYZ1", 1000, 100.00));
        msgs.add(new NewOrderImpl("IBM", "IBM2", -1000, 99.00));
        msgs.add(new OrderCxRImpl(1200, 101.01, "ABC1"));
        msgs.add(new OrderCxRImpl(1200, 101.01, "XYZ1"));
        msgs.add(new NewOrderImpl("IBM","IBM3", 100, Double.NaN));
//        msgs.add(new NewOrderImpl("IBM", "IBM2", -900, 103.00));
//        msgs.add(new NewOrderImpl("IBM", "IBM2", -900, 104.00));
//        msgs.add(new NewOrderImpl("IBM", "IBM2", 900, 102.00));
//        msgs.add(new NewOrderImpl("MSFT","XYZ1", -1000, 102.00));

        return msgs.iterator();

    }

}
