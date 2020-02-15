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
//            fail();
        }

        app.getCustomer().setId("1234");
        app.getCustomer().setName("Houman Chamani");
        app.getCustomer().setPhoneNumber("+989300323231");
        app.getCustomer().setEmail("hoomch@gmail.com");
        app.getCustomer().setCredit(100000);
    }

    @Test
    public void testGetRestaurant() {
        
    }

    @Test
    public void testFinalizeOrder() {

    }
}