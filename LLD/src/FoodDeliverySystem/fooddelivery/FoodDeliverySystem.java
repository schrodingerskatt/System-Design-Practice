package fooddelivery;

import java.util.*;

public class FoodDeliverySystem{

    private final Map<String, User> usersById = new HashMap<>();
    private final Map<String, List<User>> usersByPhone = new HashMap<>();
    private final Map<String, Restaurant> restaurants = new HashMap<>();
    private final Map<String, Order> orders = new HashMap<>();

    private User loggedInUser;

    public void registerUser(String name, String gender, String phone, String pincode){
        
        User user = new User(name, phone, pincode, gender);
        usersById.put(user.getUserId(), user);
        usersByPhone.computeIfAbsent(phone, k -> new ArrayList<>()).add(user);
    }

    public void loginUser(String phone){

        List<User>users = usersByPhone(phone);
        if(users == null || users.isEmpty()){
            System.out.println("Users not found");
            return;
        }
        loggedInUser = users.get(0);
        System.out.println("Logged in: " + loggedInUser.getUserId());
    }

    // RESTAURANT

    public void registerRestaurant(String name, String pincodes, String foodName, int price, int quantity){
        restaurants.put(name, new Restaurant(name, pincodes, foodName, price, quantity));

    }

    public void updateQuantity(String name, int quantity){
        Restaurant r = restaurants.get(name);
        r.increaseQuantity(quantity);
        System.out.println(name + ", "
                + String.join("/", r.getServiceablePincodes())
                + ", " + r.getFoodName()
                + " - " + r.getAvailableQuantity());
    }

    public void updateLocation(String name, String pincodes){
        Restaurant r = restaurants.get(name);
        r.updateLocation(pincodes);
         System.out.println(name + ", "
                + pincodes + ", "
                + r.getFoodName()
                + " - " + r.getAvailableQuantity());
    }

    // REVIEW

    public void rateRestaurant(String name, int rating, String comment){
        Restaurant r = restaurants.get(name);
        r.addReview(new Review(loggedInUser.getUserId(), rating, comment));
    }


    // SHOW
    public void showRestaurant(String sortBy){

        List<Restaurant>available = restaurants.values().stream()
                                    .filter(r ->
                                    r.getServicablePincodes()
                                    .contains(loggedInUser.getPincode())
                                    && r.getAvailableQuantity() > 0)
                                    .collect(Collectors.toList());

       if(sortBy.equalsIgnoreCase("price")){
        available.sort((a, b) -> b.getPrice()-a.getPrice());
       }else{
        available.sort(a, b) -> Double.compare(b.getAvgRating(), a.getAvgRating());
       }
       for(Restaurant r : restaurants){
        System.out.println(r.getName() + "," + r.getFoodName());
       }
    }

    // ORDER

    public void placeOrder(String name, int quantity){

        Restaurant r = restaurants.get(name);
        if(r == null || !r.getServicablePincodes().contains(loggedInUser.getPincode())||
        !r.placeOrder(quantity)){
            System.out.println("Cannot place order");
            return;
        }
        Order order = new Order(loggedInUser.getUserId(), r.getName(), r.getFoodName(),
        quantity);
        orders.put(order.getOrderId(), order);
        loggedInUser.getOrderIds().add(order.getOrderId());
        System.out.println("Order Placed Successfully. Order ID: "
                + order.getOrderId());
    }

    public void orderHistory() {
        for (String id : loggedInUser.getOrderIds()) {
            System.out.println(orders.get(id));
        }
    }

}