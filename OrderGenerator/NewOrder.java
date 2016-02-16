package hw3.OrderGenerator;

import hw3.OrderGenerator.Message;

/**
 * Created by ZHHR on 5/6/15.
 */
public interface NewOrder extends Message{



    /**
     * @return The symbol for this new order.
     */
    public String getSymbol();

    /**
     * @return The size of the order. Negative for sell
     */
    public int getSize();

    /**
     * @return The orderId for this new order
     */
    public String getOrderId();

    /**
     * @return The limit price for the order. NaN for market Order.
     */
    public double getLimitPrice();
}