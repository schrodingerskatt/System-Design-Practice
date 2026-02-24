import java.util.*;

class FoodDeliverySystem{

    private Map<String, List<Item>> itemMap = new HashMap<>();
    private Map<Integer, Restaurant> restaurantMap = new HashMap<>();
    private OrderAnalytics analytics;

    private double courierSpeed;
    private double deliveryCostRate;

    public FoodDeliverySystem(List<Restaurant> restaurants, List<Item> items, List<Order> orders,
                             double courierSpeed, double deliveryCostRate) {

        for(Restaurant r : restaurants){
            restaurantMap.put(r.id, r);
        }

        for(Item item : items){
            itemMap.computeIfAbsent(item.name, k -> new ArrayList<>()).add(item);
        }

        this.analytics = analytics;
        this.courierSpeed = courierSpeed;
        this.deliveryCostRate = deliveryCostRate;
    }

    
}