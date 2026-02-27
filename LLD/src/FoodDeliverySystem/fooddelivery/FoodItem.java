package fooddelivery;

public class FoodItem{

    private final String name;
    private final String price;
    private final int quantity;

    public FoodItem(String name, String price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public int getquantity(){
        return quantity;
    }

    public void addquantity(int q){
        return quantity+=q;
    }

    public boolean removequantity(int q){
        if (quantity < q) return false;
        quantity-=q;
        return true;
    }
}