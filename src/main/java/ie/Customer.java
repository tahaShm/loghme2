package ie;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Customer {
    private boolean restaurantIsSet = false;
    private String restaurantId;
    private String restaurantName;
    private Map<String, Integer> foodCart = new HashMap<String, Integer>();
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private int credit;

    public void clear() {
        restaurantIsSet = false;
        restaurantId = "";
        foodCart.clear();
    }

    public boolean isRestaurantSet() {
        return restaurantIsSet;
    }

    public void setRestaurant(boolean restaurantIsSet) {
        this.restaurantIsSet = restaurantIsSet;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Map<String, Integer> getFoodCart() { return foodCart; }

    public void addFoodToCart(String foodName, String restaurantId) {
        restaurantIsSet = true;
        this.restaurantId = restaurantId;
        for (Map.Entry<String, Integer> entry : foodCart.entrySet()) {
            if (entry.getKey().equals(foodName)){
                entry.setValue(entry.getValue() + 1);
                return;
            }
        }
        foodCart.put(foodName, 1);
    }

    public String getCartJson() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapperObj = new ObjectMapper();
        String foodCartJson = mapperObj.writeValueAsString(foodCart);
        return foodCartJson;
    }

    public void freeCart() {
        restaurantIsSet = false;
        restaurantId = "";
        restaurantName = "";
        foodCart.clear();
    }

    public void addCredit(int toAddCredit) {
        credit += toAddCredit;
    }
}
