package ie.commands;

import ie.App;
import ie.Food;
import ie.Restaurant;
import ie.exp.FoodFromOtherRestaurantInCartExp;

import java.util.StringTokenizer;

public class addToCart implements Command {
    public String handle(String body) throws Exception {
        App app = App.getInstance();
        StringTokenizer tokenizer = new StringTokenizer(body, "&");
        String indexBody = tokenizer.nextToken();
        String restaurantNameBody = tokenizer.nextToken();

        StringTokenizer indexTokenizer = new StringTokenizer(indexBody, "=");
        StringTokenizer restaurantTokenizer = new StringTokenizer(restaurantNameBody, "=");

        indexTokenizer.nextToken();
        int index = Integer.parseInt(indexTokenizer.nextToken());
        restaurantTokenizer.nextToken();
        String restaurantId = restaurantTokenizer.nextToken();

        Restaurant restaurant = app.getRestaurantById(restaurantId);
        Food food = restaurant.getMenu().get(index);
        try {
            app.addToCart(food, restaurant);
        }
        catch (FoodFromOtherRestaurantInCartExp e) {
            return "/getRestaurants";
        }

        return "/getRestaurant/" + restaurantId;
    }
}
