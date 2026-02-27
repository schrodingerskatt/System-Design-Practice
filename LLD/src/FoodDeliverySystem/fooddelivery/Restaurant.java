package fooddelivery;
import java.util.*;

public class Restaurant{

private final String restaurantId;
private final String name;
private Set<String> servicablePincodes;
private final FoodItem foodItem;
private final Map<String, Review> reviewsByUser = new HashMap<>();

private int ratingSum = 0;
private int ratingCount = 0;
private double avgRating = 0.0;

public Restaurant(String name, String pincodes, String foodName, int price, int quantity){

    this.restaurantId = UUID.randomUUID().toString();
    this.name = name;
    this.servicablePincodes = new HashSet<>(Arrays.asList(pincodes.split("/")));
    this.foodItem = new FoodItem(foodName, price, quantity);
}

public String getName(){
    return name;
}

public double getAvgRating(){
    return avgRating;
}

public int getPrice(){
    return foodItem.getPrice();
}

public String getFoodName(){
    return foodItem.getName();
}

public Set<String> getServicablePincodes(){
    return servicablePincodes;
}

public void updateLocation(String pincodes){
    this.servicablePincodes = new HashSet<>(Arrays.asList(pincodes.split("/")));
}

public void increaseQuantity(int quantity) {
        foodItem.addQuantity(quantity);
}

public boolean placeOrder(int quantity) {
        return foodItem.reduceQuantity(quantity);
}

public void addReview(Review review) {

    if(reviewsByUser.containsKey(review.getUserId())){
        System.out.println("User had already reviewed the restaurant");
        return;
    }
    reviewsByUser.put(review.getUserId(), review);
    ratingSum+=review.getRating();
    ratingCount++;
    avgRating = (double) ratingSum / ratingCount;
}

}