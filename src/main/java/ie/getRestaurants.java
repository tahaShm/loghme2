package ie;

import java.util.ArrayList;

public class getRestaurants implements Command{
    public String handle(String param) {
        App app = App.getInstance();
        ArrayList<Restaurant> nearRestaurants = app.getCloseRestaurants(170);
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Restaurants</title>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            text-align: center;\n" +
                "            margin: auto;\n" +
                "        }\n" +
                "        th, td {\n" +
                "            padding: 5px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .logo{\n" +
                "            width: 100px;\n" +
                "            height: 100px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>id</th>\n" +
                "            <th>logo</th>\n" +
                "            <th>name</th>\n" +
                "        </tr>\n";
        String rows = "";
        for (Restaurant restaurant: nearRestaurants) {
            rows +=
                "        <tr>\n" +
                "            <td>" + restaurant.getId() + "</td>\n" +
                "            <td><img class=\"logo\" src=\"" + restaurant.getLogo() + "\" alt=\"logo\"></td>\n" +
                "            <td>" + restaurant.getName() + "</td>\n" +
                "        </tr>\n";
        }
        content += rows;
        content += "    </table>\n" +
                "</body>\n" +
                "</html>";

        return content;
    }
}
