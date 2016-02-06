package zahittalipov.com.remember.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zahit Talipov on 19.11.2015.
 */
public class User {
    private String accessToken;
    @SerializedName("password")
    private String password;
    private String urlSystemAvatar;
    @SerializedName("username")
    private String username;

    @SerializedName("id")
    private String Id;

    @SerializedName("email")
    private String Email;

    @SerializedName("lastname")
    private String lastName;

    @SerializedName("firstname")
    private String firstName;
    @SerializedName("avatar_url")
    private String mAvatarUrl;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getmAvatarUrl() {
        return mAvatarUrl;
    }

    public void setmAvatarUrl(String mAvatarUrl) {
        this.mAvatarUrl = mAvatarUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUrlSystemAvatar() {
        return urlSystemAvatar;
    }

    public void setUrlSystemAvatar(String urlSystemAvatar) {
        this.urlSystemAvatar = urlSystemAvatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
