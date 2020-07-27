package Bot;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class BotServer {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BotServer.class);

        VkApiInterface apiInterface = ctx.getBean("vkApiInterface", VkApiInterface.class);

        try {
            apiInterface.addCallBackServer();
        } catch (ClientException | ApiException e) {
            System.out.println("Failed to add server");
            System.exit(-1);
        }

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
