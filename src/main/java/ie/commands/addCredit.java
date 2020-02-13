package ie.commands;

import ie.App;

import java.util.StringTokenizer;

public class addCredit implements Command {
    public String handle(String body) {
        String credit;
        StringTokenizer tokenizer = new StringTokenizer(body, "=");
        String attributeName = tokenizer.nextToken();
        credit = tokenizer.nextToken();
        App app = App.getInstance();
        if (!credit.isEmpty())
            app.addCredit(Integer.parseInt(credit));
        return "getProfile";
    }
}
