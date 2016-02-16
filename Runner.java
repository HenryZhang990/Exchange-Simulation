package hw3;

/**
 * Created by ZHHR on 5/6/15.
 */

import java.util.Iterator;

import hw3.OrderGenerator.Message;
import hw3.OrderGenerator.OrdersIterator;

/**
 * The Runner class used to simulate the exchange process and print the process
 * and final state of the book.
 *
 */
public class Runner {

    /**
     * Main used to simulate exchange process.
     *
     * @param args
     */
    public static void main(String[] args) {
        Runner run = new Runner();
        run.runner();
    }

    /**
     * runner method used to replay the supplied iterator and after processing
     * every order, print top of the book and print the state of the book
     * finally.
     */
    public void runner() {
        Exchange exchange = new Exchange();
        Iterator<Message> iter = OrdersIterator.getIterator();
        while (iter.hasNext()) {
            exchange.processOrder(iter.next(), true);
        }

        // print the state of the book
        exchange.printBookState();
    }

}

