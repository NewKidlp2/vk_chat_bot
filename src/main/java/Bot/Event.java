package Bot;

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

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setObject(NewMessage object) {
        this.object = object;
    }

    public void setType(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }

    NewMessage getObject() {
        return object;
    }

}
