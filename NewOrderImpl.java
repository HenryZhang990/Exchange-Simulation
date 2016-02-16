package hw3;
/**
 * Created by ZHHR on 5/6/15.
 */
import hw3.OrderGenerator.NewOrder;

public class NewOrderImpl implements NewOrder{

    /**
     * The original NewOrderImpl class implements NewOrder Interface.
     *
     * I personally add methods to reset symbols, sizes, orderIds and limitPrice of an certain order.
     */

    private String symbol;
    private String orderId;
    private int size;
    private double limitPrice;

    public NewOrderImpl(String symbol, String orderId, int size, double limitPrice) {
        this.symbol = symbol;
        this.orderId = orderId;
        this.size = size;
        this.limitPrice = limitPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getSize() {
        return size;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    /**
     * reset the symbol of an order.
     */
    public void setSymbol(String newSymbol) {
        symbol = newSymbol;
    }

    /**
     * reset the size.
     */
    public void setSize(int newSize) {
        size = newSize;
    }

    /**
     * reset the orderId.
     */
    public void setOrderId(String newOrderId) {
        orderId = newOrderId;
    }

    /**
     * reset the limitPrice.
     */
    public void setLimitPrice(double newLimitPrice) {
        limitPrice = newLimitPrice;
    }
}
