package ie.commands;

import ie.App;
import ie.exp.NotEnoughCreditExp;

import java.util.Map;

public class finalizeOrder implements Command {
    public String handle(String body) throws Exception {
        App app = App.getInstance();
        int totalSum = 0;
        for (Map.Entry<String, Integer> entry : app.getCart().entrySet()) {
            totalSum += app.sendPrice(entry.getKey());
        }
        int credit = app.getCustomer().getCredit();
        if (credit >= totalSum) {
            app.getCustomer().setCredit(credit - totalSum);
            app.getCustomer().freeCart();
        }
        else return "/getRestaurants";
        return "/getProfile";
    }
}
