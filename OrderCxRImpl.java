package hw3;

/**
 * Created by ZHHR on 5/6/15.
 */

import hw3.OrderGenerator.OrderCxR;

public class OrderCxRImpl implements OrderCxR{

    private int size;
    private double limitPx;
    private String orderId;


    public OrderCxRImpl(int size, double limitPx, String orderId) {
        this.size = size;
        this.limitPx = limitPx;
        this.orderId = orderId;
    }

    public int getSize() {
        return size;
    }

    public double getLimitPrice() {
        return limitPx;
    }

    public String getOrderId() {
        return orderId;
    }
}