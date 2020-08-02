package Bot.Manager;

import Bot.*;
import Bot.ApiInterface.VkApiInterface;
import Bot.Entity.Event;
import Bot.Service.HttpRequestService;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Sends answer depending on event that happened.
 */
@Component
public class EventManager {

    private Integer randomId = 0;

    @Autowired
    private VkApiInterface vkApiInterface;

    @Autowired
    private HttpRequestService httpRequestService;

    /**
     * Determines which event happened and what action to provide.
     * @param json object received in controller.
     * @return answer for http request.
     */
    public String manageEvent (String json){
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
     * Creates and sends response message for the message sent by user.
     * @param message message that user sent to bot.
     * @param peerId id of the user, that sent message.
     */
    private void sendMessage (String message, Integer peerId) {
        String responseMessage;
        if (message.equals("")) {
            responseMessage = "You've sent message without text";
        } else {
            responseMessage = "You've written: " + message;
        }

        String requestUrl = Urls.sendMessageUrl.getUrl();
        final Collection<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("random_id", randomId.toString()));
        params.add(new BasicNameValuePair("peer_id", peerId.toString()));
        params.add(new BasicNameValuePair("message", responseMessage));
        params.add(new BasicNameValuePair("access_token", vkApiInterface.getAccessToken()));
        params.add(new BasicNameValuePair("v", "5.120"));

        try {
            httpRequestService.execute(params, requestUrl);
        } catch (RequestException requestException) {
            System.out.println("Problems occurred while sending message: " + requestException.getMessage());
        }

        randomId++;
    }

}
