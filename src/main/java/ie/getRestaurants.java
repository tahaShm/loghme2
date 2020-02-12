package ie;

public class getRestaurants implements Command{
    public String handle(String param) {
        return "<!DOCTYPE html>\n" +
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
                "    <ul>\n" +
                "        <li>food 1:\u200C 2</li>\n" +
                "        <li>food 2: 3</li>\n" +
                "        <li>food 3: 1</li>\n" +
                "    </ul>\n" +
                "    <form action=\"\" method=\"POST\">\n" +
                "        <button type=\"submit\">finalize</button>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
    }
}
