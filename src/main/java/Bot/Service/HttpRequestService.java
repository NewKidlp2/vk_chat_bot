package Bot.Service;

import Bot.RequestException;
import com.google.gson.JsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;


/**
 * Constructs and sends http requests.
 */
@Component
public class HttpRequestService {


    /**
     * @param params parameters for the request.
     * @param url where to send a request.
     * @return server response.
     * @throws RequestException occurs when server returns error in json.
     */
    public String execute (Collection<NameValuePair> params, String url) throws RequestException {

        String response = "";

        try {
            response = Request.Post(url)
                    .bodyForm(params, Charset.forName("UTF-8"))
                    .execute()
                    .returnContent()
                    .asString();

            checkResponseForError(response);
        } catch (IOException e) {
            System.out.println("Problems with connection occurred:" + e.getMessage());
            System.exit(-1);
        }

        return response;
    }

    /**
     * Checks response JSON if it contains object "error".
     * @param response server response for http request.
     * @throws RequestException occurs when server returns error in JSON.
     */
    private void checkResponseForError(String response) throws RequestException {
        boolean isError = JsonParser
                .parseString(response)
                .getAsJsonObject()
                .has("error");

        if (isError) {
            throw new RequestException(JsonParser
                    .parseString(response)
                    .getAsJsonObject()
                    .getAsJsonObject("error")
                    .getAsJsonPrimitive("error_msg")
                    .getAsString());
        }
    }
}
