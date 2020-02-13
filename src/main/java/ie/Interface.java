package ie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Interface {
    private static App app;

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

        ArrayList<Restaurant> restaurants;
        ObjectMapper nameMapper = new ObjectMapper();
        restaurants = nameMapper.readValue(loghmeBody, ArrayList.class);
        ArrayList<Restaurant> convertedRestaurants = nameMapper.convertValue(restaurants, new TypeReference<ArrayList<Restaurant>>() { });

        App app = App.getInstance();
        app.setRestaurants(convertedRestaurants);

        Javalin jvl = Javalin.create().start(7070);
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
    }
}