package ie.commands;

import ie.App;

import java.util.Map;

public class cart implements Command {
    public String handle(String body) {
        App app = App.getInstance();
        String content = "<!DOCTYPE html>\n" +
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
                "    <ul>\n";
        for (Map.Entry<String, Integer> entry : app.getCart().entrySet()) {
            content += "<li>" + entry.getKey() + " : " + entry.getValue() + "</li>\n";
        }
        content += "    </ul>\n" +
                "    <form action=\"/finalizeOrder\" method=\"POST\">\n" +
                "        <button type=\"submit\">finalize</button>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
        if (app.getCart().isEmpty())
            return "No food in cart!";
        return content;
    }
}
