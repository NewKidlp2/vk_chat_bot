package Bot;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to deserialize JSON file received in controller.
 */
public class NewMessage {
    @SerializedName("body")
    private String body;

    @SerializedName("user_id")
    private int userId;

    int getUserId() {
        return userId;
    }

    String getBody() {
        return body;
    }
}
