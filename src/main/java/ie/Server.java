package ie;

import ie.commands.Command;
import ie.exp.Forbidden403Exp;
import ie.exp.NotEnoughCreditExp;
import ie.exp.NotFound404Exp;
import io.javalin.Javalin;

import java.util.StringTokenizer;

public class Server {
    public static Javalin jvl;

    public static void run() {
        jvl = Javalin.create().start(7070);
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
                        if (e instanceof NotFound404Exp || e instanceof ClassNotFoundException) {
                            response = "Page not found";
                            ctx.status(404);
                        }
                        else if (e instanceof Forbidden403Exp) {
                            response = "Forbidden access";
                            ctx.status(403);
                        }
                    }
                    ctx.contentType("text/html");
                    ctx.result(response);
                });

        Javalin postServer = jvl.post("*",
                ctx -> {
                    boolean noRedirect = false;
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
                        ctx.status(400);
                        if (e instanceof NotEnoughCreditExp)
                            ctx.result("Not enough credit!");
                        noRedirect = true;
                    }
                    if (!noRedirect)
                        ctx.redirect(redirectUrl);
                });
    }

    public static void stop() {
        jvl.stop();
    }
}
