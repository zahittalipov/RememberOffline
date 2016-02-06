package zahittalipov.com.remember.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zahit Talipov on 19.12.2015.
 */
public class WordReq {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;

    public WordReq(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
