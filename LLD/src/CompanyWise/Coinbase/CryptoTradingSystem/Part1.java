import java.util.*;

enum OrderStatus{
    LIVE,
    PAUSED,
    CANCELLED
}

class Order{

    String id;
    String currency;
    int amount;
    int timeStamp;
    String type;
    String userId;
    OrderStatus status;

    public Order(String id, String currency, int amount, int timeStamp, String type, String userId){
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

    public CryptoTradingSystem(){
        this.orders = new HashMap<>();
    }

    public String placeOrder(String id, String currency, int amount, int timeStamp, String type,
    string userId){

        if(orders.containsKey(id)){
            return "";
        }
        Order order = new Order(id, currency, amount, timeStamp, type, userId);
        orders.put(id, order);
        return id;
    }

    public String pauseOrder(String id){

        Order order = orders.get(id);
        if(order == null || order.status != OrderStatus.LIVE){
            return "";
        }
        order.status = OrderStatus.PAUSED;
        return id;
    }

    public String resumeOrder(String id){

        Order order = orders.get(id);
        if(order == null || order.status != OrderStatus.PAUSED){
            return "";
        }
        order.status = OrderStatus.LIVE;
        return id;
    }

    public String cancelOrder(String id){

        Order order = orders.get(id);
        if(order == null || order.status == OrderStatus.CANCELLED){
            return "";
        }
        order.status = OrderStatus.CANCELLED;
        return id;
    }

    public List<String> displayLiveOrders(){

        List<Order> liveOrders = new ArrayList<>();

        for(Order order : orders.values()){
            if(order.status == OrderStatus.LIVE){
                liveOrders.add(order);
            }
        }
        liveOrders.sort(Comparator.comparingInt(o -> o.timeStamp));
        List<String> result = new ArrayList<>();
        for(Order order : liveOrders){
            result.add(order.id);
        }
    return liveOrders;
    }


}