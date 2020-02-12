package ie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class Restaurant {
    private String name;
    private Location location;
    private String logo;
    private ArrayList<Food> menu = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public void setMenu (ArrayList<Food> menu) {
        this.menu = menu;
    }

    public int foodIdx(String foodName) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getName().equals(foodName))
                return i;
        }
        return -1;
    }

    public void addFood(Food newFood) throws FoodAlreadyExistsExp {
        if (foodIdx(newFood.getName()) == -1)
            menu.add(newFood);
        else
            throw new FoodAlreadyExistsExp();
    }

    public String sendJsonInfo() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public String sendJsonFoodInfo(String foodName) throws IOException, FoodNotFoundExp {
        int index = -1;
        for (int i = 0; i < menu.size(); i++) {
            if (foodName.equals(menu.get(i).getName())) {
                index = i;
                break;
            }
        }
        if (index >= 0)
            return menu.get(index).sendInfo();
        else
            throw new FoodNotFoundExp();
    }

    public boolean isFoodValid(String foodName) {
        int index = -1;
        boolean isValid = false;
        for (int i = 0; i < menu.size(); i++) {
            if (foodName.equals(menu.get(i).getName())) {
                index = i;
                break;
            }
        }
        if (index >= 0)
            isValid = true;
        return isValid;
    }

    public float sendPopularity() {
        float average = sendAverageFoodPopulations();
        float distance = location.sendDistance();
        if (distance == 0)
            return Float.MAX_VALUE;
        return average / distance;
    }

    private float sendAverageFoodPopulations() {
        float total = 0;
        for (Food food : menu) {
            total += food.getPopularity();
        }
        return (total / menu.size());
    }
}
