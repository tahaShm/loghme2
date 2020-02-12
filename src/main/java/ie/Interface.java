package ie;

import io.javalin.Javalin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Interface {
    private static App app;
    public static void handleAction(String action, String jsonData) throws IOException {
        try {
            switch (action) {
            case "addRestaurant":
                app.addRestaurant(jsonData);
                break;
            case "addFood":
                app.addFood(jsonData);
                break;
            case "getRestaurants":
                System.out.print(app.getRestaurantsInfo());
                break;
            case "getRestaurant":
                System.out.println(app.getRestaurant(jsonData));
                break;
            case "getFood":
                System.out.println(app.getFood(jsonData));
                break;
            case "addToCart":
                app.addToCart(jsonData);
                break;
            case "getCart":
                System.out.println(app.getCartJson());
                break;
            case "finalizeOrder":
                System.out.println(app.finalizeOrder());
                System.out.println("Your order was submitted.");
                break;
            case "getRecommendedRestaurants":
                System.out.println(app.getRecommendedRestaurants());
                break;
            default:
                System.out.println("Wrong command!");
                break;
            }
        }
        catch (Exception e) {
            if (e instanceof FoodNotFoundExp)
                System.out.println("Food does not exist!");
            else if (e instanceof RestaurantNotFoundExp)
                System.out.println("Restaurant does not exist!");
            else if (e instanceof FoodFromOtherRestaurantInCartExp)
                System.out.println("There is food from another restaurant in your cart!");
            else if (e instanceof FoodAlreadyExistsExp)
                System.out.println("Food already exists!");
            else if (e instanceof RestaurantAlreadyExistsExp)
                System.out.println("Restaurant already exists!");
            else
                System.out.println("Bad input!");
        }
    }

    public static String getUrlBody(String url) throws Exception {
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        urlConnection.getInputStream()));
        String body = "", inputLine = "";

        while ((inputLine = in.readLine()) != null)
            body += inputLine;
        in.close();
        return body;
    }

    public static void main(String[] args) throws IOException {
        String loghmeBody;
        try {
            loghmeBody = getUrlBody("http://138.197.181.131:8080/restaurants");
            System.out.println(loghmeBody);
        }
        catch (Exception e) {
            System.out.println("exception occurred!");
        }
//        Javalin app = Javalin.create().start(7070);
//        Javalin s = app.get("/", ctx -> {ctx.result("Hello World");});
    }
}