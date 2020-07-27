package Bot;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to deserialize json file received in controller.
 */
public class NewMessage {
    @SerializedName("body")
    private String body;

    @SerializedName("user_id")
    private int userId;

    int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public void setBody(String body) {
        this.body = body;
    }

    String getBody() {
        return body;
    }
}
