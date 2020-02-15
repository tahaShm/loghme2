package ie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.commands.Command;
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
            System.out.println(loghmeBody);
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
        app.getCustomer().setId("1234");
        app.getCustomer().setName("Houman Chamani");
        app.getCustomer().setPhoneNumber("+989300323231");
        app.getCustomer().setEmail("hoomch@gmail.com");
        app.getCustomer().setCredit(100000);

        Javalin jvl = Javalin.create().start(7070);
        Javalin getServer = jvl.get("*",
                ctx -> {
                StringTokenizer tokenizer = new StringTokenizer(ctx.url(), "/");
                String httpStr = tokenizer.nextToken();
                String domainStr = tokenizer.nextToken();
                String firstToken = "", secondToken = "", response = "";
                if (tokenizer.hasMoreElements())
                    firstToken = tokenizer.nextToken();
                if (tokenizer.hasMoreElements())
                    secondToken = tokenizer.nextToken();
                try {
                    Class<Command> commandClass = (Class<Command>) Class.forName("ie.commands." + firstToken);
                    Command newCommand = commandClass.getDeclaredConstructor().newInstance();
                    response = newCommand.handle(secondToken);
                }
                catch (Exception e) {
                    if (e instanceof NotFound404Exp)
                        response = "Page not found";
                    else if (e instanceof Forbidden403Exp)
                        response = "Forbidden access";
                    else if (e instanceof ClassNotFoundException)
                        response = "Page not found";
                    ctx.status(400);
                }
                ctx.contentType("text/html");
                ctx.result(response);
        });
        Javalin postServer = jvl.post("*",
                ctx -> {
                    StringTokenizer tokenizer = new StringTokenizer(ctx.url(), "/");
                    String httpStr = tokenizer.nextToken();
                    String domainStr = tokenizer.nextToken();
                    String firstToken = "", redirectUrl = "";
                    if (tokenizer.hasMoreElements())
                        firstToken = tokenizer.nextToken();

                    Class<Command> commandClass = (Class<Command>) Class.forName("ie.commands." + firstToken);
                    Command newCommand = commandClass.getDeclaredConstructor().newInstance();
                    try {
                        redirectUrl = newCommand.handle(ctx.body());
                    }
                    catch (Exception e) {
                        if (e instanceof NotEnoughCreditExp)
                            ctx.result("Not enough credit!");
                        ctx.status(400);
                    }
                    ctx.redirect(redirectUrl);
        });
    }
}