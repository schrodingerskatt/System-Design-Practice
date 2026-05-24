/* Data Models
1. Cart
2. Cart Item
3. Customization
*/

class CartItem{

    String name;
    double basePrice;
    List<Customization>customizations;
    int quantity;

    double getPrice(){
        double total = basePrice;
        for(Customization c : customizations){
            total+=c.price;
        }
        return total*quantity;
    }
}


class Cart{

    List<CartItem> items;

    double getSubtotal(){
        double sum = 0;
        for(CartItem c : items){
            sum+=c.getPrice();
        }
    return sum;
    }
}


class Customization{
    String name;
    double price;
}

/* Context Object */
class PricingContext{
    Cart cart;
    boolean isUberOne;
    boolean isSurge;
    double surgeMultiplier;
    String promocode;
    double taxRate;
    double baseDeliveryFee;

}

/* Each pricing component behaves like a transformation on price, so I’ll define a rule interface. */
interface PricingRule {
    double apply(double currentTotal, PricingContext context);
}

/* Now we need an Orchestrator that ddoes this work */

class PricingEngine{

    List<PricingRule> rules;

    double calculate(PricingContext context){
        double total = context.cart.getSubtotal();

        for(PricingRule rule : rules){
            total = rule.apply(total, context);
        }
    return total;
    }
}
