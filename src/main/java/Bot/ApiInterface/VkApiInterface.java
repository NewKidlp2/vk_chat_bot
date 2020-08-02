package Bot.ApiInterface;

import Bot.Service.HttpRequestService;
import Bot.RequestException;
import Bot.Urls;
import com.google.gson.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Creates callback server, uses properties from application.properties file.
 */
@Component
public class VkApiInterface {
    private Integer serverId;

    @Value("${server.url}")
    private String url;

    private String accessToken;
    private Integer groupId;
    private String confirmationCode;
    @Autowired
    private HttpRequestService httpRequestService;

    /**
     * Deletes callback server when application is shutting down.
     */
    @PreDestroy
    private void onBeanDestruction () {
        String requestUrl = Urls.deleteCallBackServerUrl.getUrl();
        final Collection<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("group_id", groupId.toString()));
        params.add(new BasicNameValuePair("server_id", serverId.toString()));
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", "5.120"));

        try {
            httpRequestService.execute(params, requestUrl);
        } catch (RequestException requestException) {
            System.out.println("Problems occurred while deleting callback server: "
                    + requestException.getMessage());
        }

        System.out.println("Server successfully deleted");
    }

    VkApiInterface(@Value("${access_token}") String access_token, @Value("${group_id}") int group_id) {
        accessToken = access_token;
        groupId = group_id;
    }

    /**
     * Creates callback server with necessary settings.
     */
    public void addCallBackServer () {
        requestConfirmationCode();
        createCallBackServer();
        checkCallbackServerConfirmation();
        setCallbackServerSettings();

        System.out.println("Callback server is up");
    }

    /**
     * Creates and executes request for confirmation code for callback server and equates it to variable
     * if it successfully returned.
     */
    private void requestConfirmationCode() {
        String requestUrl = Urls.getCallbackConfirmationCodeUrl.getUrl();
        final Collection<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("group_id", groupId.toString()));
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", "5.120"));

        try {
            String response = httpRequestService.execute(params, requestUrl);

            confirmationCode = JsonParser
                    .parseString(response)
                    .getAsJsonObject()
                    .getAsJsonObject("response")
                    .getAsJsonPrimitive("code")
                    .getAsString();
        } catch (RequestException requestException) {
            System.out.println("Problems occurred while requesting confirmation code for callback server: "
                    + requestException.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Creates and executes request to add callback server.
     */
    private void createCallBackServer() {
        String requestUrl = Urls.addCallbackServerUrl.getUrl();
        final Collection<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("group_id", groupId.toString()));
        params.add(new BasicNameValuePair("url", url));
        params.add(new BasicNameValuePair("title", "Server"));
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", "5.120"));

        try {
            String response = httpRequestService.execute(params, requestUrl);

            serverId = JsonParser
                    .parseString(response)
                    .getAsJsonObject()
                    .getAsJsonObject("response")
                    .getAsJsonPrimitive("server_id")
                    .getAsInt();
        } catch (RequestException requestException) {
            System.out.println("Problems occurred while creating callback server: "
                    + requestException.getMessage());
            System.exit(-1);
        }

    }

    /**
     * Creates and executes request with callback server settings.
     */
    private void setCallbackServerSettings() {
        String requestUrl = Urls.setCallbackServerSettingsUrl.getUrl();
        final Collection<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("group_id", groupId.toString()));
        params.add(new BasicNameValuePair("server_id", serverId.toString()));
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("message_new", "1"));
        params.add(new BasicNameValuePair("v", "5.120"));

        try {
            httpRequestService.execute(params, requestUrl);
        } catch (RequestException requestException) {
            System.out.println("Problems occurred while setting callback server settings: "
                    + requestException.getMessage());
            System.exit(-1);
        }
    }

    private void checkCallbackServerConfirmation() {
        String status = "";
        String requestUrl = Urls.checkCallbackServerConfirmationUrl.getUrl();
        final Collection<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("group_id", groupId.toString()));
        params.add(new BasicNameValuePair("server_ids", serverId.toString()));
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", "5.120"));

        try {
            while (status.equals("") || status.equals("wait")) {
                String response = httpRequestService.execute(params, requestUrl);

                status = JsonParser
                        .parseString(response)
                        .getAsJsonObject()
                        .getAsJsonObject("response")
                        .getAsJsonArray("items")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonPrimitive("status")
                        .getAsString();

                if (status.equals("wait")) {
                    Thread.sleep(100);
                }
            }
        } catch (RequestException requestException) {
            System.out.println("Problems occurred while checking confirmation: "
                    + requestException.getMessage());
            System.exit(-1);

        } catch (InterruptedException interruptedException) {
            System.out.println("Thread was interrupted" + interruptedException.getMessage());
        }

        if (!status.equals("ok")) {
            System.out.println("Callback server wasn't confirmed. Check url in property file.");
            System.exit(-1);
        }
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
