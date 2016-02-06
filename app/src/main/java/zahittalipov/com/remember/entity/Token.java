package zahittalipov.com.remember.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zahit Talipov on 14.12.2015.
 */
public class Token {
    @SerializedName("token")
    private String token;

    @SerializedName("live")
    private String live;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }
}
