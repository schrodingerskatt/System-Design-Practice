/*

1. Create multiple shards (streams)
2. Route orders based on:
   shardId = hash(userId) % streamCount
3. Store each user’s orders in only one shard
4. Keep a global index for orderId → shardId
*/

import java.util.*;

enum OrderStatus {
    LIVE,
    PAUSED,
    CANCELED
}

class Order {
    String id;
    String currency;
    int amount;
    int timestamp;
    String type;
    String userId;
    OrderStatus status;

    public Order(String id, String currency, int amount, int timestamp, String type, String userId) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.userId = userId;
        this.status = OrderStatus.LIVE;
    }
}

class OrderShard{

    private Map<String, Order> orders =  new HashMap<>();
    private Map<String, Set<String>> userOrders = new HashMap<>();

    public String placeOrder(Order order){

        if(orders.containsKey(order.id)) return "";

        orders.put(order.id, order);
        userOrders.putIfAbsent(order.userId, new HashSet<>());
        userOrders.get(order.userId).add(order.id);
        return order.id;
    }

    public String pauseOrder(String id) {
        Order order = orders.get(id);
        if (order == null || order.status != OrderStatus.LIVE) return "";
        order.status = OrderStatus.PAUSED;
        return id;
    }

    public String resumeOrder(String id) {
        Order order = orders.get(id);
        if (order == null || order.status != OrderStatus.PAUSED) return "";
        order.status = OrderStatus.LIVE;
        return id;
    }

    public String cancelOrder(String id) {
        Order order = orders.get(id);
        if (order == null || order.status == OrderStatus.CANCELED) return "";
        order.status = OrderStatus.CANCELED;
        return id;
    }

    public int cancelAllOrders(String userId) {
        if (!userOrders.containsKey(userId)) return 0;

        int count = 0;
        for (String id : userOrders.get(userId)) {
            Order order = orders.get(id);
            if (order != null && order.status != OrderStatus.CANCELED) {
                order.status = OrderStatus.CANCELED;
                count++;
            }
        }
        return count;
    }

    public List<Order> getLiveOrders() {
        List<Order> liveOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.status == OrderStatus.LIVE) {
                liveOrders.add(order);
            }
        }
        return liveOrders;
    }
}

// --------------- Sharded System ----------------

class CryptoTradingSystem{

    private List<OrderShard> shards;
    private Map<String, Integer> orderToShard;
    private int streamCount;

    public CryptoTradingSystem(int streamCount){

            this.streamCount = streamCount;
            this.shards = new ArrayList<>();
            this.orderToShard = new HashMap<>();

        for(int i = 0; i < streamCount; i++){
            shards.add(new OrderShard());
        }
    }

    private int getshardId(String userId){

        return Math.abs(userId.hashCode()) % streamCount;
    }

    public String placeOrder(String id, String currency, int amount, int timestamp, String type, 
    String userId) {

        if(orderToShard.containsKey(id)) return "";

        int shardId = getshardId(userId);
        Order order = new Order(id, currency, amount, timeStamp, type, userId);
        String result = shards.get(shardId).placeOrder(order);
        if(!result.isEmpty()){
            orderToShard.put(id, shardId);
        }
    return result;
    }

    public String pauseOrder(String id){
        Integer shardId = orderToShard.get(id);
        if(shardId == null) return "";
        return shards.get(shardId).pauseOrder(id);
    }

    public String resumeOrder(String id){
        Integer shardId = orderToShard.get(id);
        if(shardId == null) return "";
        return shards.get(shardId).resumeOrder(id); 
    }

    public String cancelOrder(String id){
        Integer shardId = orderToShard.get(id);
        if(shardId == null) return "";
        return shards.get(shardId).cancelOrder(id);
    }

    public int cancelAllOrders(String userId){
        int shardId = orderToShard.get(id);
        return shards.get(shardId).cancelAllOrders(userId);
    }

    public List<String> displayLiveOrders(){

        List<Order>allLiveOrders = new ArrayList<>();

        for(OrderShard shard : shards){
            allLiveOrders.addAll(shard.getLiveOrders());
        }
        allLiveOrders.sort(Comparator, comparingInt(o -> o.timeStamp));
        List<String>result = new ArrayList<>();
        for(Order order : allLiveOrders){
            result.add(order.id);
        }
    return result;
    }

}