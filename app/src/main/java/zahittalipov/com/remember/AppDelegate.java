package zahittalipov.com.remember;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import zahittalipov.com.remember.entity.User;


/**
 * Created by Zahit Talipov on 19.11.2015.
 */
public class AppDelegate extends Application {
    public static final String NAME = "Remember";
    public static boolean existUser = false;
    private static User currentUser = null;
    private static SharedPreferences preferences;

    public static User getCurrentUser() {

        return currentUser;
    }

    public static void saveUser(User currentUser, Context context) {
        preferences = context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("access_token", currentUser.getAccessToken());
        editor.putString("username", currentUser.getUsername());
        editor.putString("id", currentUser.getId());
        editor.putString("avatar", currentUser.getmAvatarUrl());
        editor.putString("urlSystemAvatar", currentUser.getUrlSystemAvatar());
        editor.putString("firstname", currentUser.getFirstName());
        editor.putString("lastname", currentUser.getLastName());
        Log.d("editor", "" + editor.commit());
        AppDelegate.currentUser = currentUser;
    }

    public static boolean logout(Context context) {
        preferences = context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("access_token");
        editor.remove("username");
        editor.remove("id");
        editor.remove("avatar");
        editor.remove("urlSystemAvatar");
        editor.commit();
        Log.d("log", "logout");
        return true;
    }

    public static boolean isExistUser() {
        existUser = preferences.contains("username");
        return existUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences(NAME, MODE_PRIVATE);
        existUser = preferences.contains("username");
        if (existUser) {
            User user = new User();
            user.setAccessToken(preferences.getString("access_token", null));
            user.setUsername(preferences.getString("username", "username"));
            user.setId(preferences.getString("id", "id"));
            user.setmAvatarUrl(preferences.getString("avatar", "avatar"));
            user.setEmail(preferences.getString("email", null));
            user.setFirstName(preferences.getString("firstname", null));
            user.setLastName(preferences.getString("lastname", null));
            user.setUrlSystemAvatar(preferences.getString("urlSystemAvatar", null));
            currentUser = user;
            Log.d("load", "appDelegate");
        }
    }

}
