package ie;

import static org.junit.Assert.*;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.eclipse.jetty.util.IO;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AppTest {
    static String path = "src/test/resources/";

    @BeforeClass
    public static void init() {
        App app = App.getInstance();
        String res = "";
        try {
            for (int i = 1; i < 6; i++) {
                res = Files.readString(Paths.get(path + "restaurant" + i + ".json"));
                app.addRestaurant(res);
            }
        }
        catch (Exception e) {
            fail();
        }

        app.getCustomer().setId("1234");
        app.getCustomer().setName("Houman Chamani");
        app.getCustomer().setPhoneNumber("+989300323231");
        app.getCustomer().setEmail("hoomch@gmail.com");
        app.getCustomer().setCredit(100000);

        String[] mainArgs = new String[1];
        mainArgs[0] = "test";
        Interface.main(mainArgs);
    }

    @Test
    public void testGetRestaurant() {
        HttpResponse response = Unirest.get("http://localhost:7070/restaurant/1").asString();
        assertEquals(403, response.getStatus());
        response = Unirest.get("http://localhost:7070/restaurant/2").asString();
        assertEquals(200, response.getStatus());
        response = Unirest.get("http://localhost:7070/restaurant/3").asString();
        assertEquals(200, response.getStatus());
        response = Unirest.get("http://localhost:7070/restaurant/4").asString();
        assertEquals(403, response.getStatus());
        response = Unirest.get("http://localhost:7070/restaurant/5").asString();
        assertEquals(403, response.getStatus());
        response = Unirest.get("http://localhost:7070/restaurant/6").asString();
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testFinalizeOrder() {
        App app = App.getInstance();
        ArrayList<Restaurant> restaurants = app.getRestaurants();
        try {
            app.addToCart(restaurants.get(0).getMenu().get(0), restaurants.get(0));
            app.addToCart(restaurants.get(0).getMenu().get(1), restaurants.get(0));
            app.addToCart(restaurants.get(0).getMenu().get(0), restaurants.get(0));
            app.addToCart(restaurants.get(0).getMenu().get(0), restaurants.get(0));
        }
        catch (Exception e) {
            fail();
        }
        HttpResponse response = Unirest.post("http://localhost:7070/finalizeOrder").asString();
        assertEquals(400, response.getStatus());
    }

    @AfterClass
    public static void stopServer() {
        Interface.stop();
    }
}