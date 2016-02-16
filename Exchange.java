package hw3;

/**
 * Created by ZHHR on 5/6/15.
 */

import java.util.HashMap;
import java.util.Iterator;
import hw3.OrderGenerator.Message;
import hw3.OrderGenerator.NewOrder;
import hw3.OrderGenerator.OrderCxR;

/**
 * The Exchange class simulates the order execution process of a exchange.
 *
 * Methods includes order processing, and printing the final states of the books.
 *
 */
public class Exchange {

    // Creating a HashMap to store AskBooks books
    HashMap<String, OrderBook> AskBooks = new HashMap<String, OrderBook>();
    // Creating a HashMap to store BidBooks books
    HashMap<String, OrderBook> BidBooks = new HashMap<String, OrderBook>();
    // Mapping OrderIds to orders
    HashMap<String, NewOrderImpl> orderDictionary = new HashMap<String, NewOrderImpl>();

    /**
     * Process a new order, first check empty book situation, then execute the
     * new order, and add to corresponding book if necessary.
     *
     * @param message Received orders
     * @param IsPrint A boolean variable indicates printing the output or not
     */
    public void processOrder(Message message, boolean IsPrint) {
        if (message instanceof NewOrder) {
            NewOrder newOrder = (NewOrder) message;
            String symbol = newOrder.getSymbol();
            int size = newOrder.getSize();
            String orderId = newOrder.getOrderId();
            Double limitPrice = newOrder.getLimitPrice();

            // Creating a new order
            NewOrderImpl myOrder = new NewOrderImpl(symbol, orderId, Math.abs(size), limitPrice);

            // Implementing buy order
            if (size >= 0) {
                // If AskBooks book is empty, create new AskBooks
                if (!AskBooks.containsKey(symbol)) {
                    OrderBook newAsk = new OrderBook(symbol, Type.ASK);
                    AskBooks.put(symbol, newAsk);

                    // If BidBooks book is empty, create new BidBooks
                    if (!BidBooks.containsKey(symbol)) {
                        OrderBook newBid = new OrderBook(symbol, Type.BID);
                        BidBooks.put(symbol, newBid);
                    }

                    // Adding order to BidBooks book
                    BidBooks.get(symbol).addOrder(myOrder);
                    // Adding order to orderDictionary
                    orderDictionary.put(orderId, myOrder);
                }
                // If AskBooks book is not empty, execute the order
                else {
                    if (IsPrint == true) {
                        AskBooks.get(symbol).orderExecution(myOrder, true);
                    } else {
                        AskBooks.get(symbol).orderExecution(myOrder, false);
                    }
                    // If orders are not fully traded, put them to BidBooks book
                    if (myOrder.getSize() > 0) {
                        if (!BidBooks.containsKey(symbol)) {
                            OrderBook newBid = new OrderBook(symbol, Type.BID);
                            BidBooks.put(symbol, newBid);
                        }

                        // Adding order to BidBooks book
                        BidBooks.get(symbol).addOrder(myOrder);
                        // Adding order to orderDictionary
                        orderDictionary.put(orderId, myOrder);
                    }
                }
                if (IsPrint) {
                    AskBooks.get(symbol).printCurrentBest();
                    BidBooks.get(symbol).printCurrentBest();
                }
            }

            // Implementing sell order
            else {
                if (!BidBooks.containsKey(symbol)) {
                    OrderBook newBid = new OrderBook(symbol, Type.BID);
                    BidBooks.put(symbol, newBid);

                    if (!AskBooks.containsKey(symbol)) {
                        OrderBook newAsk = new OrderBook(symbol, Type.ASK);
                        AskBooks.put(symbol, newAsk);
                    }

                    // Adding order to AskBooks book
                    AskBooks.get(symbol).addOrder(myOrder);
                    // Adding order to orderDictionary
                    orderDictionary.put(orderId, myOrder);

                } else {
                    if (IsPrint == true) {
                        BidBooks.get(symbol).orderExecution(myOrder, true);
                    } else {
                        BidBooks.get(symbol).orderExecution(myOrder, false);
                    }
                    if (myOrder.getSize() > 0) {
                        if (!AskBooks.containsKey(symbol)) {
                            OrderBook newAsk = new OrderBook(symbol, Type.ASK);
                            AskBooks.put(symbol, newAsk);
                        }

                        // Adding order to AskBooks book
                        AskBooks.get(symbol).addOrder(myOrder);
                        // Adding order to orderDictionary
                        orderDictionary.put(orderId, myOrder);
                    }
                }

                if (IsPrint) {
                    AskBooks.get(symbol).printCurrentBest();
                    BidBooks.get(symbol).printCurrentBest();
                }
            }
        }

        else if (message instanceof OrderCxR) {
            OrderCxR orderCxR = (OrderCxR) message;
            String orderIdCxR = orderCxR.getOrderId();
            int sizeCxR = orderCxR.getSize();
            Double limitPriceCxR = orderCxR.getLimitPrice();

            // Finding the same orderId to implement replace order
            NewOrderImpl replaceOrder = orderDictionary.get(orderIdCxR);

            int sizeReplaceOrder = replaceOrder.getSize();
            String symbolReplaceOrder = replaceOrder.getSymbol();

            // Creating a new order to store the replacing order we will implement
            NewOrderImpl newReplaceOrder = new NewOrderImpl(symbolReplaceOrder, orderIdCxR, Math.abs(sizeCxR), limitPriceCxR);

            // Removing dead orders in BidBooks
            if (sizeReplaceOrder >= 0) {
                BidBooks.get(symbolReplaceOrder).addOrder(newReplaceOrder);
                replaceOrder.setSize(0);
                orderDictionary.put(orderIdCxR, newReplaceOrder);
            }
            // Removing dead orders in AskBooks
            else {
                AskBooks.get(symbolReplaceOrder).addOrder(newReplaceOrder);
                replaceOrder.setSize(0);
                orderDictionary.put(orderIdCxR, newReplaceOrder);
            }

            if (IsPrint) {
                AskBooks.get(symbolReplaceOrder).printCurrentBest();
                BidBooks.get(symbolReplaceOrder).printCurrentBest();
            }
        }
    }

    /**
     * Print the final state of the books after all executions.
     */
    public void printBookState() {
        System.out.println();
        System.out.println();
        System.out.println("Final State of the books: ");
        System.out.println();
        Iterator<String> iterBooks = AskBooks.keySet().iterator();
        while (iterBooks.hasNext()) {
            String symbol = iterBooks.next();
            System.out.println("********** " + symbol + " **********");
            AskBooks.get(symbol).printBookState();
            System.out.println("- - - - - - - - - - - ");
            BidBooks.get(symbol).printBookState();
            System.out.println();
        }
    }
}