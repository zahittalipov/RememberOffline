package zahittalipov.com.remember.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Zahit Talipov on 18.12.2015.
 */
public class Word extends RealmObject {
    @PrimaryKey
    private int id;
    private int idDict;
    private String name;
    @SerializedName("value")
    private String value;

    public Word(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Word() {

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

    public int getIdDict() {
        return idDict;
    }

    public void setIdDict(int idDict) {
        this.idDict = idDict;
    }
}
