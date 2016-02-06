package zahittalipov.com.remember;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import zahittalipov.com.remember.entity.Token;
import zahittalipov.com.remember.entity.User;
import zahittalipov.com.remember.service.ApiFactory;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean> {

    Callback<Token> callback;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView textView;
    private Boolean request = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = (View) findViewById(R.id.email_login_form);
        textView = (TextView) findViewById(R.id.errorText);
        textView.setVisibility(View.GONE);
        callback = new Callback<Token>() {
            @Override
            public void onResponse(Response<Token> response, Retrofit retrofit) {
                Log.d("code",response.code()+"");
                if (response.code() == 200) {
                    textView.setVisibility(View.GONE);
                    Log.d("response", response.body().getToken());
                    Intent intent = new Intent(LoginActivity.this, RememberActivity.class);
                    intent.putExtra("token", response.body().getToken());
                    startActivity(intent);
                    finish();
                } else if (response.code() == 401) {
                    textView.setVisibility(View.VISIBLE);
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Throwable t) {
                User user= new User();
                user.setLastName("Default");
                user.setFirstName("User");
                user.setEmail("default@mail.ru");
                user.setUsername("def");
                AppDelegate.saveUser(user,getApplicationContext());
                Log.d("response", "error");
                showProgress(false);
            }
        };
    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);


        String login = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        if (TextUtils.isEmpty(login)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            textView.setVisibility(View.GONE);
            ApiFactory.getRememberService().getAccessToken(login, password).enqueue(callback);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public void login(View view) {
        attemptLogin();
    }

    public void signupClick(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {

    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }


}

