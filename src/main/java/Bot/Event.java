package Bot;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to deserialize json file received in controller.
 */
class Event {
    @SerializedName("group_id")
    private int groupId;

    @SerializedName("object")
    private NewMessage object;

    @SerializedName("type")
    private String type;

    String getType() {
        return type;
    }

    NewMessage getObject() {
        return object;
    }

}
