package zahittalipov.com.remember.service;

import com.squareup.okhttp.OkHttpClient;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Zahit Talipov on 13.12.2015.
 */
public class ApiFactory {
    public static final OkHttpClient client = new OkHttpClient();
    private static String baseUrl = "http://192.168.1.78:8080/api/";

    static {
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);
    }

    public static RememberService getRememberService() {
        return getRetrofit().create(RememberService.class);
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client).build();
    }
}
