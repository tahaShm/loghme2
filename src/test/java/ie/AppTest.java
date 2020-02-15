package ie;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AppTest {
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

    @Before
    public void init() {
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
    }

    @Test
    public void getRestaurant() {
        
    }
}
