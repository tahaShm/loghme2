package ie.commands;

import ie.App;
import ie.Food;
import ie.Restaurant;
import ie.exp.Forbidden403Exp;

public class getRestaurant implements Command {
    public String handle(String id) throws Exception {
        App app = App.getInstance();
        Restaurant restaurant = app.getRestaurantById(id);
        if (restaurant.getLocation().sendDistance() > 170)
            throw new Forbidden403Exp();
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Restaurant</title>\n" +
                "    <style>\n" +
                "        img {\n" +
                "        \twidth: 50px;\n" +
                "        \theight: 50px;\n" +
                "        }\n" +
                "        li {\n" +
                "            display: flex;\n" +
                "            flex-direction: row;\n" +
                "        \tpadding: 0 0 5px;\n" +
                "        }\n" +
                "        div, form {\n" +
                "            padding: 0 5px\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <ul>\n" +
                "        <li>id: " + restaurant.getId() + "</li>\n" +
                "        <li>name: " + restaurant.getName() + "</li>\n" +
                "        <li>location: (" + restaurant.getLocation().getX() + ", " + restaurant.getLocation().getY() + ")</li>\n" +
                "        <li>logo: <img src=\"" + restaurant.getLogo() + "\" alt=\"logo\"></li>\n" +
                "        <li>menu: \n" +
                "        \t<ul>\n";
        for (Food food: restaurant.getMenu()) {
            content +=
                            "<li>\n" +
                            "    <img src=\"" + food.getImage() + "\" alt=\"logo\">\n" +
                            "    <div>" + food.getName() + "</div>\n" +
                            "    <div>" + food.getPrice() + " Toman</div>\n" +
                            "    <form action=\"\" method=\"POST\">\n" +
                            "        <button type=\"submit\">addToCart</button>\n" +
                            "    </form>\n" +
                            "</li>\n";
        }
        content +=
                "        \t</ul>\n" +
                "        </li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>";
        return content;
    }
}
