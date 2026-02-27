package fooddelivery;

import java.util.*;

public class Order{

    private final String orderId;
    private final String userId;
    private final String restaurantName;
    private final String foodName;
    private final int quantity;
    private final LocalDateTime createdAt;
    private OrderStatus status;

    public Order(String userId, String restaurantName, String foodName, String quantity){
        this.orderId = UUID.randomUUID().toString();
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PLACED;
    }

    public String getOrderId(){
        return orderId;
    }

    @Override
    public String toString(){
        return "OrderID=" + orderId +
        ", Restaurant=" + restaurantName +
        ", Item=" + foodName +
        ", Qty=" + quantity +
        ", Status=" + status +
        ", Time=" + createdAt;
    }
}