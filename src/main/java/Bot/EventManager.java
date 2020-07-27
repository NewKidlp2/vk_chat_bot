package Bot;

import com.google.gson.Gson;
import com.vk.api.sdk.actions.Messages;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Sends answer for http request depending on event that happened.
 */
@Component
class EventManager {

    @Autowired
    private VkApiInterface vkApiInterface;

    /**
     * Decides which event happened and what answer should be given.
     * @param json object received in controller.
     * @return answer for http request.
     */
    String manageEvent (String json){
        Event event = new Gson().fromJson(json, Event.class);

        if (event.getType().equals("confirmation")) {
            return vkApiInterface.getConfirmationCode();
        }

        if (event.getType().equals("message_new")) {
            sendMessage(event.getObject().getBody(), event.getObject().getUserId());
        }

        return "ok";
    }

    /**
     * Creates response message for the message sent by user
     * @param message message that user sent to bot
     * @param peerId id of the user, that sent message
     */
    private void sendMessage (String message, int peerId) {
        Messages responseMessage = new Messages(vkApiInterface.getVkApiClient());

        try {
            if (!message.equals("")) {
                responseMessage.send(vkApiInterface.getGroupActor())
                        .peerId(peerId)
                        .message("You've written: " + message)
                        .execute();

            } else {
                responseMessage.send(vkApiInterface.getGroupActor())
                        .peerId(peerId)
                        .message("You've sent message without text")
                        .execute();

            }
        } catch (ApiException | ClientException e) {
            System.out.println("Failed to send message");
        }
    }

}
