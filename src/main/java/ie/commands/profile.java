package ie.commands;

import ie.App;
import ie.Customer;

public class profile implements Command {
    public String handle(String id) throws Exception {
        App app = App.getInstance();
        Customer customer = app.getCustomer();
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>User</title>\n" +
                "    <style>\n" +
                "        li {\n" +
                "        \tpadding: 5px\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <ul>\n" +
                "        <li>id: " + customer.getId() + "</li>\n" +
                "        <li>full name: " + customer.getName() +"</li>\n" +
                "        <li>phone number: " + customer.getPhoneNumber() + "</li>\n" +
                "        <li>email: " + customer.getEmail() + "</li>\n" +
                "        <li>credit: " + customer.getCredit() + " Toman</li>\n" +
                "        <form action=\"/addCredit\" method=\"POST\">\n" +
                "            <button type=\"submit\">increase</button>\n" +
                "            <input type=\"text\" name=\"credit\" value=\"\" />\n" +
                "        </form>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>";
        return content;
    }
}
