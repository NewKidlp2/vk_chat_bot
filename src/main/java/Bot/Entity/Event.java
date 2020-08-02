package Bot.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to deserialize json file received in controller.
 */
public class Event {
    @SerializedName("group_id")
    private int groupId;

    @SerializedName("object")
    private NewMessage object;

    @SerializedName("type")
    private String type;

    public String getType() {
        return type;
    }

    public NewMessage getObject() {
        return object;
    }

}
