package fooddelivery;

public class Main {

    public static void main(String[] args) {

        FoodDeliverySystem system = new FoodDeliverySystem();

        system.registerUser("Pralove", "M", "phone1", "HSR");
        system.registerUser("Nitesh", "M", "phone2", "BTM");
        system.registerUser("Vatsal", "M", "phone3", "BTM");

        system.loginUser("phone1");

        system.registerRestaurant("Food Court-1",
                "BTM/HSR", "NI Thali", 100, 5);

        system.registerRestaurant("Food Court-2",
                "BTM/pincode-2", "Burger", 120, 3);

        system.loginUser("phone3");

        System.out.println("\nShow by Price:");
        system.showRestaurant("price");

        system.placeOrder("Food Court-1", 2);
        system.placeOrder("Food Court-2", 7);

        system.rateRestaurant("Food Court-2", 3, "Good Food");
        system.rateRestaurant("Food Court-1", 5, "Nice Food");

        System.out.println("\nShow by Rating:");
        system.showRestaurant("rating");

        system.loginUser("phone1");
        system.updateQuantity("Food Court-2", 5);
        system.updateLocation("Food Court-2", "BTM/HSR");

        System.out.println("\nOrder History:");
        system.orderHistory();
    }
}