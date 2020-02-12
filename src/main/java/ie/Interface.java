package ie;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import java.io.*;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;

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
        String loghmeBody = "";
        try {
            loghmeBody = getUrlBody("http://138.197.181.131:8080/restaurants");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Restaurant> Restaurants = new ArrayList<>();
        ObjectMapper nameMapper = new ObjectMapper();
        Restaurants = nameMapper.readValue(loghmeBody, ArrayList.class);

        App app = App.getInstance();
        app.setRestaurants(Restaurants);

        Javalin jvl = Javalin.create().start(7070);
        String str = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>User</title>\n" +
                "    <style>\n" +
                "        li, div, form {\n" +
                "        \tpadding: 5px\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div>restaurant name</div>\n" +
                "    <ul>\n" +
                "        <li>food 1:\u200C 2</li>\n" +
                "        <li>food 2: 3</li>\n" +
                "        <li>food 3: 1</li>\n" +
                "    </ul>\n" +
                "    <form action=\"\" method=\"POST\">\n" +
                "        <button type=\"submit\">finalize</button>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
        Javalin s = jvl.get("*",
                ctx -> {
                System.out.println(ctx.url());
                StringTokenizer tokenizer = new StringTokenizer(ctx.url(), "/");
                String httpStr = tokenizer.nextToken();
                String domainStr = tokenizer.nextToken();
                String firstToken = "", secondToken = "";
                if (tokenizer.hasMoreElements())
                    firstToken = tokenizer.nextToken();
                if (tokenizer.hasMoreElements())
                    secondToken = tokenizer.nextToken();

                Class<Command> commandClass = (Class<Command>) Class.forName("ie." + firstToken);
                Command newCommand = commandClass.getDeclaredConstructor().newInstance();
                String response = newCommand.handle(secondToken);
                ctx.contentType("text/html");
                ctx.result(response);
        });
//        Javalin s2 = jvl.get("/showRestaurant", ctx -> {ctx.result("Hello World ko");});
//        Javalin s2 = jvl.get("/showRestaurant", ctx -> {ctx.result("Hello World ko");});
//        Javalin s2 = jvl.get("/showRestaurant", ctx -> {ctx.result("Hello World ko");});
//        Javalin s2 = jvl.get("/showRestaurant", ctx -> {ctx.result("Hello World ko");});
    }
}