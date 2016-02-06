package zahittalipov.com.remember.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Zahit Talipov on 18.12.2015.
 */
public class Dictionary extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;

    private String description;

    private Boolean access;
    private String itemStatus;
    private RealmList<Word> words;

 public Dictionary(){

 }
    public Dictionary(String name, String description, Boolean access) {
        this.name = name;
        this.description = description;
        this.access = access;
    }

    public Dictionary(Dictionary dictionary) {
        this.setName(dictionary.getName());
        this.setDescription(dictionary.getDescription());
        this.setAccess(dictionary.getAccess());
        this.setId(dictionary.getId());
    }

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


    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public RealmList<Word> getWords() {
        return words;
    }

    public void setWords(RealmList<Word> words) {
        this.words = words;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
