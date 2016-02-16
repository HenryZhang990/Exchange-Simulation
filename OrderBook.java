package hw3;

/**
 * Created by ZHHR on 5/6/15.
 */

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;


public class OrderBook {
    /**
     * The OrderBook class represent the order books in an exchange, can either be ASK book or BID book.
     *
     * Methods includes:
     * Adding a new order to the book.
     * Executing an order.
     * Printing current best bid and ask for a certain book.
     * Printing the final state of a book.
     */

    private String symbol;
    private Type type;

    // Using a TreeMap data structure to implement the book
    private TreeMap<Double, LinkedList<NewOrderImpl>> book = new TreeMap<Double, LinkedList<NewOrderImpl>>();

    // Decimal format of the price
    private DecimalFormat df = new DecimalFormat("##.00");

    /**
     * Constructing a new book
     *
     * @param symbol symbol of the book
     * @param type   type of the book
     */
    public OrderBook(String symbol, Type type) {
        this.symbol = symbol;
        this.type = type;
    }


    /**
     * Executing an order.
     * Here we only consider the market order.
     * I also implement the limit order, you can uncomment the related block to execute.
     *
     * @param newOrder Received order
     * @param IsPrint A boolean variable indicates printing the output or not
     */
    public void orderExecution(NewOrderImpl newOrder, boolean IsPrint) {

        Double neworderLimitPrice = newOrder.getLimitPrice();
        int neworderSize = newOrder.getSize();

        // Executing market order
        if (neworderLimitPrice.isNaN()) {

            Iterator<Double> iterBook = book.keySet().iterator();
            if (type == Type.BID) {
                iterBook = book.descendingKeySet().iterator();
            }

            // Looping through the order book
            out: while (iterBook.hasNext()) {

                Double priceLevel = iterBook.next();
                Iterator<NewOrderImpl> iterOrderQueue = book.get(priceLevel)
                        .iterator();
                
                // Looping through orders with same price
                while (iterOrderQueue.hasNext()) {
                    NewOrderImpl order = iterOrderQueue.next();
                    
                    // Removing executed order from the queue.
                    if (order.getSize() == 0) {
                        book.get(priceLevel).remove();
                        continue;
                    }

                    // Different scenarios after the order executed
                    // First, order at this price is not yet empty
                    if ((neworderSize - order.getSize()) > 0) {
                        if (IsPrint == true) {
                            System.out.println("Order from " + newOrder.getOrderId()
                                    + " traded with order from " + order.getOrderId());
                            System.out.println();
                        }
                        newOrder.setSize(neworderSize - order.getSize());
                        order.setSize(0);
                    }

                    // Second, all executed.
                    else if ((neworderSize - order.getSize()) == 0) {
                        if (IsPrint == true) {
                            System.out.println("Order from " + newOrder.getOrderId()
                                    + " traded with order from " + order.getOrderId());
                            System.out.println();
                        }
                        // reset the new order size to 0.
                        newOrder.setSize(0);
                        order.setSize(0);
                        break out;
                    }

                    else {
                        if (IsPrint == true) {
                            System.out.println("Order from " + newOrder.getOrderId()
                                    + " traded with order from " + order.getOrderId());
                            System.out.println();
                        }
                        // reset new order size to 0.
                        newOrder.setSize(0);
                        order.setSize(order.getSize() - neworderSize);
                        break out;
                    }
                }
            }
        }

//        /**
//         *   limit order, uncomment to execute
//         */

//        else {
//            Iterator<Double> iterBook = book.keySet().iterator();
//            if (type == Type.BID) {
//                iterBook = book.descendingKeySet().iterator();
//            }
//
//            // loop price of the book
//            out: while (iterBook.hasNext()) {
//                Double priceLevel = iterBook.next();
//                // if priceLevel does not exceed limit price
//                if (type == Type.ASK && priceLevel <= neworderLimitPrice
//                        || type == Type.BID
//                        && priceLevel >= neworderLimitPrice) {
//                    Iterator<NewOrderImpl> iterOrderQueue = book
//                            .get(priceLevel).iterator();
//
//                    // loop orders in order queue of a specific price
//                    while (iterOrderQueue.hasNext()) {
//                        NewOrderImpl order = iterOrderQueue.next();
//                        // remove the dead order in the front of order queue,
//                        // this takes O(1) time
//                        if (order.getSize() == 0) {
//                            book.get(priceLevel).remove();
//                            continue;
//                        }
//
//                        // after trade, new order is not consumed to empty
//                        if ((neworderSize - order.getSize()) > 0) {
//                            if (IsPrint == true) {
//                                System.out.println("Order from "
//                                        + newOrder.getOrderId()
//                                        + " traded with order from "
//                                        + order.getOrderId());
//                                System.out.println();
//                            }
//                            newOrder.setSize(neworderSize - order.getSize());
//                            order.setSize(0);
//                            order = iterOrderQueue.next();
//                        }
//
//                        // after trade, new order is consumed to empty, order is
//                        // empty, perfectly execute
//                        else if ((neworderSize - order.getSize()) == 0) {
//                            if (IsPrint == true) {
//                                System.out.println("Order from "
//                                        + newOrder.getOrderId()
//                                        + " traded with order from "
//                                        + order.getOrderId());
//                                System.out.println();
//                            }
//                            newOrder.setSize(0);
//                            order.setSize(0);
//                            break out;
//                        }
//
//                        // after trade, new order is consumed empty, order has
//                        // left
//                        // volume
//                        else {
//                            if (IsPrint == true) {
//                                System.out.println("Order from "
//                                        + newOrder.getOrderId()
//                                        + " traded with order from "
//                                        + order.getOrderId());
//                                System.out.println();
//                            }
//                            newOrder.setSize(0);
//                            order.setSize(order.getSize() - neworderSize);
//                            break out;
//                        }
//                    }
//                    priceLevel = iterBook.next();
//                }
//
//                // if priceLevel exceeds limit price, break
//                else
//                    break;
//            }
//        }

    }

    /**
     * Adding a new order to the book.
     *
     * @param newOrder new order that received
     */
    public void addOrder(NewOrderImpl newOrder) {

        Double limitPrice = newOrder.getLimitPrice();

        // Create a new order if there wasn't in the queue.
        if (!book.containsKey(limitPrice)) {
            LinkedList<NewOrderImpl> orderQueue = new LinkedList<NewOrderImpl>();
            book.put(limitPrice, orderQueue);
            book.get(limitPrice).addLast(newOrder);
        }

        // Else, add the new order to the last of order queue
        else {
            book.get(limitPrice).addLast(newOrder);
        }
    }


    /**
     * Print the current best price of the book, both Bid and Ask.
     */
    public void printCurrentBest() {
        Iterator<Double> iterBook = book.keySet().iterator();
        if (type == Type.BID) {
            iterBook = book.descendingKeySet().iterator();
        }

        while (iterBook.hasNext()) {
            int sumSize = 0;
            Double price = iterBook.next();
            Iterator<NewOrderImpl> iterOrderQueue = book.get(price).iterator();
            while (iterOrderQueue.hasNext()) {
                NewOrderImpl order = iterOrderQueue.next();
                sumSize += order.getSize();
            }
            if (sumSize != 0) {
                System.out.println("---------- Current Best " + type + " of " + symbol + " ----------");
                System.out.println( sumSize + "@" + df.format(price) + "  " + type );
                System.out.println();
                break;
            }
        }

    }
    
    /**
     * Printing the final state of the books after all executions.
     */
    
    public void printBookState() {

        Iterator<Double> iterBook = book.descendingKeySet().iterator();


        while (iterBook.hasNext()) {
            int sumSize = 0;
            Double price = iterBook.next();
            Iterator<NewOrderImpl> iterOrderQueue = book.get(price).iterator();
            while (iterOrderQueue.hasNext()) {
                NewOrderImpl order = iterOrderQueue.next();
                sumSize += order.getSize();
            }
            if (sumSize != 0)
                System.out.println(sumSize + "@" + df.format(price) + "  " + type);
        }
    }

}
