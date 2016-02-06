package zahittalipov.com.remember.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zahit Talipov on 18.12.2015.
 */
public class Dict {
    public Dict(String name, String description, Boolean access) {
        this.name = name;
        this.description = description;
        this.access = access;
    }

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("access")
    private Boolean access;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
