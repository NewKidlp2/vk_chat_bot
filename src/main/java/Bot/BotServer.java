package Bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class BotServer {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BotServer.class);

        VkApiInterface vkApiInterface = ctx.getBean("vkApiInterface", VkApiInterface.class);

        vkApiInterface.addCallBackServer();

        startCommandListener();
    }

    private static void startCommandListener() {
        System.out.println("Now you can enter commands:");
        System.out.println("print end to end work");

        String command;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            command = scanner.next();
            if ("end".equals(command)) {
                System.exit(0);
            } else {
                System.out.println("Unknown command");
            }
        }
    }

}
