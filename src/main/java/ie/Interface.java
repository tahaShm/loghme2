package ie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.commands.Command;
import ie.exp.BadRequest400Exp;
import ie.exp.Forbidden403Exp;
import ie.exp.NotEnoughCreditExp;
import ie.exp.NotFound404Exp;
import io.javalin.Javalin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Interface {
    public static Javalin jvl;
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

    public static void main(String args[]) {
        String loghmeBody = "";
        ArrayList<Restaurant> restaurants = null;
        ObjectMapper nameMapper = new ObjectMapper();
        try {
            loghmeBody = getUrlBody("http://138.197.181.131:8080/restaurants");
            restaurants = nameMapper.readValue(loghmeBody, ArrayList.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Restaurant> convertedRestaurants = nameMapper.convertValue(restaurants, new TypeReference<ArrayList<Restaurant>>() { });

        App app = App.getInstance();
        app.setRestaurants(convertedRestaurants);
        app.getCustomer().setId("1234");
        app.getCustomer().setName("Houman Chamani");
        app.getCustomer().setPhoneNumber("+989300323231");
        app.getCustomer().setEmail("hoomch@gmail.com");
        app.getCustomer().setCredit(100000);

        Server.run();
    }
}