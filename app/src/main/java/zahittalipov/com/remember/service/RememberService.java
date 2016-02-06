package zahittalipov.com.remember.service;

import com.squareup.okhttp.ResponseBody;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import zahittalipov.com.remember.entity.Dict;
import zahittalipov.com.remember.entity.SignUpError;
import zahittalipov.com.remember.entity.Token;
import zahittalipov.com.remember.entity.User;
import zahittalipov.com.remember.entity.WordReq;

/**
 * Created by Zahit Talipov on 13.12.2015.
 */
public interface RememberService {
    @POST("signin")
    Call<Token> getAccessToken(@Query("username") String username,
                               @Query("password") String password);

    @POST("signup")
    Call<SignUpError> createUser(@Query("username") String username,
                                 @Query("password") String password,
                                 @Query("email") String email,
                                 @Query("lastname") String lastname,
                                 @Query("firstname") String firstname);

    @GET("user")
    Call<User> getUser(@Query("token") String token);

    @GET("logout")
    Call<ResponseBody> logout(@Query("token") String token);

    @POST("user/update")
    Call<User> updateUser();

    @DELETE("user/dictionaries/{id}")
    Call<Dict> deleteDictionary(@Path("id") int s, @Query("token") String token);

    @PUT("user/dictionaries/")
    Call<Dict> addDictionary(@Body Dict dictionary, @Query("token") String token);

    @GET("user/dictionaries/")
    Call<List<Dict>> getAllDictionaries(@Query("token") String token);

    @GET("user/dictionaries/{id}/words/")
    Call<List<WordReq>> getDictionaryWords(@Path("id") int id, @Query("token") String token);

    @PUT("user/dictionaries/{id}/words/")
    Call<WordReq> addWord(@Path("id") int id, @Body WordReq req,@Query("token") String token);

    @DELETE("user/dictionaries/{id}/words/{idWord}")
    Call<WordReq> deleteWord(@Path("id") int id,@Path("idWord") int i,@Query("token") String token);
}
