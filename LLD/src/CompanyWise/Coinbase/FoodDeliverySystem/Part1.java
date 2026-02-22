import java.util.*;

class Restaurant{

    int id;
    int x;
    int y;

    Restaurant(int x, int y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
    }
}

class Item{

    int restaurantId;
    String name;
    int price;

    Item(int restaurantId, String name, int price){
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
    }
}

public class FoodDeliverySystem{

    private Map<Integer, Restaurant> restaurantMap;
    private Map<String, List<Item>> itemMap;

    public FoodDeliverySystem(List<Restaurant> restaurants, List<Item> items){

        restaurantMap = new HashMap<>();
        itemMap = new HashMap<>();

        for(Restaurant r : restaurants){
            restaurantMap.put(r.id, r);
        }

        for(Item item : items){
            itemMap.computeIfAbsent(item.name, k -> new ArrayList<>()).add(item);
        }
    }

    // Find cheapest restaurant
    public Integer findCheapestRestaurant(String itemName){

        if(!itemMap.containsKey(itemName)) return null;

        List<Item> list = itemMap.get(itemName);
        int minPrice = Integer.MAX_VALUE;
        Integer result = null;

        for(Item i : list){
            if(i.price < minPrice){
                minPrice = i.price;
                result = i.restaurantId;
            }
        }
    return result;
    }

    // Find Nearest restaurant
    public Integer findNearestRestaurant(int userX, int userY, String itemName){

        if(!itemMap.containsKey(itemName)) return null;

        List<Item> list = itemMap.get(itemName);
        double minDist = Double.MAX_VALUE;
        Integer result = null;

        for(Item item : list){
            Restaurant r = restaurantMap.get(item.restaurantId);
            if(r == null) continue;

            double dx = r.x-userX;
            double dy = r.y-userY;
            double dist = dx*dx+dy*dy;
            if(dist < minDist){
                minDist = dist;
                result = r.id;
            }
        }
    return result;
    }
}