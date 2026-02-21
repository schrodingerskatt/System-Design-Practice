import java.util.*;

enum OrderStatus{

    LIVE,
    PAUSED,
    CANCELED
}

class Order{

    String id;
    String currency;
    int amount;
    int timeStamp;
    String type;
    String userId;
    OrderStatus status;

    public Order(String id, String currency, int amount, int timeStamp, String type, String userId,
    OrderStatus status){

        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.type = type;
        this.userId = userId;
        this.status = OrderStatus.LIVE;
    }
}

class CryptoTradingSystem{

    private Map<String, Order> orders;
    private Map<String, Set<String>> useOrders;

    public CryptoTradingSystem(){
        this.orders = new HashMap<>();
        this.useOrders = new HashMap<>();
    }

    // Part 1 - Basic Operations

    public String placeOrder(String id, String currency, int amount, int timestamp, String type, 
    String userId){

        if(orders.containsKey(id)){
            return "";
        }

        Order order = new Order(id, currency, amount, timeStamp, type, userId);
        orders.put(id, order);

        useOrders.putIfAbsent(userId, new HashSet<>());
        useOrders.get(userId).add(id);

        return id;
    }

    public String pauseOrder(String id){

        Order order = orders.get(id);
        if(order == null || order.status != OrderStatus.LIVE){
            return null;
        }
        order.status = OrderStatus.PAUSED;
        return id;

    }

    public String resumeOrder(String id){

        Order order = orders.get(id);
        if(order == null || order.status != OrderStatus.PAUSED){
            return null;
        }
        order.status = OrderStatus.LIVE;
        return id;
    }

    public String cancelOrder(String id){

        Order order = orders.get(id);
        if(order == null || order.status == OrderStatus.CANCELED){
            return null;
        }
        order.status = OrderStatus.CANCELED;
        return id;
    }

    public List<String> displayLiveOrders(){

        List<Order> liveOrders = new ArrayList<>();

        for(Order order : orders.values()){
            if(order.status == OrderStatus.LIVE){
                liveOrders.add(order);
            }
        }

        liveOrders.sort(Comparator.comparingInt(o -> o.timestamp));
        List<String> result = new ArrayList<>();
        for(Order order : liveOrders){
            result.add(order.id);
        }

        return result;

    }

    // Bulk User Cancel

    public int cancelAllOrders(String userId){

        if(!useOrders.containsKey(userId)){
            return 0;
        }

        int count = 0;
        Set<String> orderIds = useOrders.get(userId);

        for(String id : orderIds){

            Order order = orders.get(id);
            if(order != null && order.status != OrderStatus.CANCELED){
                order.status = OrderStatus.CANCELED;
                count++;
            }
        }
    return count;
    }
}