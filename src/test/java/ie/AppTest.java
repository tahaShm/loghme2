package ie;

import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;

public class AppTest 
{
    static String path = "src/test/resources/";
    static App app;

    @BeforeClass
    public static void init() {
        app = App.getInstance();
        String res1 = "", res2 = "", res3 = "", res4 = "", res5 = "";
        try {
            res1 = Files.readString(Paths.get(path + "restaurant1.json"));
            res2 = Files.readString(Paths.get(path + "restaurant2.json"));
            res3 = Files.readString(Paths.get(path + "restaurant3.json"));
            res4 = Files.readString(Paths.get(path + "restaurant4.json"));
            res5 = Files.readString(Paths.get(path + "restaurant5.json"));
            app.addRestaurant(res1);
            app.addRestaurant(res2);
            app.addRestaurant(res3);
            app.addRestaurant(res4);
            app.addRestaurant(res5);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRecommendedRestaurants() {
        String result = "";
        HashMap<String, Float> resultRestaurantsNames = null;
        try {
            result = app.getRecommendedRestaurants();
            ObjectMapper mapper = new ObjectMapper();
            resultRestaurantsNames = mapper.readValue(result, HashMap.class);
        }
        catch (IOException e) {
            fail();
        }
        assertTrue(resultRestaurantsNames.containsKey("Restaurant5"));
        assertTrue(resultRestaurantsNames.containsKey("Restaurant1"));
        assertTrue(resultRestaurantsNames.containsKey("Restaurant2"));

//        populations in order of restaurant name
//        0.7/221 = 0.003167
//        0.4/41 = 0.009
//        0.65/196 = 0.003163
//        0.65/229 = 0.002
//        0.5/0.01 = 50
    }

    @Test
    public void testFinalizeOrder() {
        HashMap<String, Integer> resultCart = null;
        String res1 = "", res2 = "", res3 = "", res4 = "", res5 = "", cartJson;
        try {
            res1 = Files.readString(Paths.get(path + "order1.json"));
            res2 = Files.readString(Paths.get(path + "order2.json"));
            res3 = Files.readString(Paths.get(path + "order3.json"));
            res4 = Files.readString(Paths.get(path + "order4.json"));
            res5 = Files.readString(Paths.get(path + "order5.json"));
            app.addToCart(res1);
            app.addToCart(res1);
            app.addToCart(res5);

            assertEquals(2, app.getCart().size());

            cartJson = app.finalizeOrder();

            assertEquals(0, app.getCart().size());

            ObjectMapper mapper = new ObjectMapper();
            resultCart = mapper.readValue(cartJson, HashMap.class);
            assertTrue(resultCart.containsKey("Food11"));
            assertTrue(resultCart.containsKey("Food12"));
            assertEquals(2, resultCart.get("Food11"), 0);
            assertEquals(1, resultCart.get("Food12"), 0);
        }
        catch (Exception e) {
            fail();
        }
    }

    @AfterClass
    public static void destructor() {
        app.clear();
    }

//    @Test
//    public void testAddRestaurant() {
//        assertEquals(2, App.getRestaurants().size());
//        assertEquals("Hesturan", App.getRestaurants().get(0).getName());
//        assertEquals("Shila", App.getRestaurants().get(1).getName());
//
//        assertEquals("luxury", App.getRestaurants().get(0).getDescription());
//        assertEquals("fast food", App.getRestaurants().get(1).getDescription());
//
//        assertEquals(1, App.getRestaurants().get(0).getLocation().getX(), 10);
//        assertEquals(2, App.getRestaurants().get(0).getLocation().getY(), 10);
//        assertEquals(2, App.getRestaurants().get(1).getLocation().getX(), 10);
//        assertEquals(5, App.getRestaurants().get(1).getLocation().getY(), 10);
//
//        assertEquals(2, App.getRestaurants().get(0).getMenu().size());
//        assertEquals(2, App.getRestaurants().get(1).getMenu().size());
//
//        assertEquals("Gheime", App.getRestaurants().get(0).getMenu().get(0).getName());
//        assertEquals("Kabab", App.getRestaurants().get(0).getMenu().get(1).getName());
//        assertEquals("Jooje kabab", App.getRestaurants().get(1).getMenu().get(0).getName());
//        assertEquals("Mahi", App.getRestaurants().get(1).getMenu().get(1).getName());
//
//        assertEquals("it's yummy!", App.getRestaurants().get(0).getMenu().get(0).getDescription());
//        assertEquals("it's delicious!", App.getRestaurants().get(0).getMenu().get(1).getDescription());
//        assertEquals("it's not bad!", App.getRestaurants().get(1).getMenu().get(0).getDescription());
//        assertEquals("it's ...!", App.getRestaurants().get(1).getMenu().get(1).getDescription());
//
//        assertEquals(0.8, App.getRestaurants().get(0).getMenu().get(0).getPopularity(), 10);
//        assertEquals(0.6, App.getRestaurants().get(0).getMenu().get(1).getPopularity(), 10);
//        assertEquals(0.5, App.getRestaurants().get(1).getMenu().get(0).getPopularity(), 10);
//        assertEquals(0.65, App.getRestaurants().get(1).getMenu().get(1).getPopularity(), 10);
//
//        assertEquals(20000, App.getRestaurants().get(0).getMenu().get(0).getPrice());
//        assertEquals(30000, App.getRestaurants().get(0).getMenu().get(1).getPrice());
//        assertEquals(25000, App.getRestaurants().get(1).getMenu().get(0).getPrice());
//        assertEquals(40000, App.getRestaurants().get(1).getMenu().get(1).getPrice());
//    }

//    @Test
//    public void testAddFood() {
//        String inp1 = "", inp2 = "", inp3 = "";
//        try {
//            inp1 = Files.readString(Paths.get(path + "food1.json"));
//            inp2 = Files.readString(Paths.get(path + "food2.json"));
//            inp3 = Files.readString(Paths.get(path + "food3.json"));
//            App.addFood(inp1);
//            App.addFood(inp2);
//            App.addFood(inp3);
//        }
//        catch (IOException e) {
//            fail();
//        }
//        assertEquals(3, App.getRestaurants().get(1).getMenu().size());
//        assertEquals(2, App.getRestaurants().get(0).getMenu().size());
//    }

//    @Test
//    public void rand() {
//
//    }
}
