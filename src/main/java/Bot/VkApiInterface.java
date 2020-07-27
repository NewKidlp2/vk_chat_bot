package Bot;

import com.vk.api.sdk.actions.Groups;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.responses.AddCallbackServerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;


/**
 * Creates callback server, client for API, uses properties from application.properties file.
 */
@Component
class VkApiInterface {
    private VkApiClient vkApiClient;
    private GroupActor groupActor;
    private Groups groups;
    private AddCallbackServerResponse server;

    @Value("${server.url}")
    private String url;

    private String accessToken;
    private int groupId;
    private String confirmationCode;


    /**
     * Deletes callback server when application is shutting down.
     */
    @PreDestroy
    private void onBeanDestruction () {
        System.out.println("Deleting server");

        try {
            groups.deleteCallbackServer(groupActor, server.getServerId()).execute();
        } catch (ApiException | ClientException e) {
            System.out.println("Server deleting failed");
        }

        System.out.println("Server successfully deleted");
    }

    VkApiInterface(@Value("${access_token}") String access_token, @Value("${group_id}") int group_id) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vkApiClient = new VkApiClient(transportClient);

        accessToken = access_token;
        groupId = group_id;
    }

    void addCallBackServer () throws ClientException, ApiException {
        groupActor = new GroupActor(groupId, accessToken);
        groups = new Groups(vkApiClient);

        confirmationCode = groups.getCallbackConfirmationCode(groupActor).execute().getCode();

        server = groups
                .addCallbackServer(groupActor, url, "Listener")
                .execute();

        System.out.println("Server is up (url: " + url + ")");

        int serverId = server.getServerId();

        groups.setCallbackSettings(groupActor, serverId).messageNew(true).execute();
    }

    String getConfirmationCode() {
        return confirmationCode;
    }

    VkApiClient getVkApiClient() {
        return vkApiClient;
    }

    GroupActor getGroupActor() {
        return groupActor;
    }
}
