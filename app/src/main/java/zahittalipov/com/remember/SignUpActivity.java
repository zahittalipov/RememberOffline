package zahittalipov.com.remember;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import zahittalipov.com.remember.entity.SignUpError;
import zahittalipov.com.remember.entity.User;
import zahittalipov.com.remember.service.ApiFactory;


public class SignUpActivity extends AppCompatActivity {
    private TextView usernameView;
    private TextView passwordView;
    private TextView confirmView;
    private TextView emailView;
    private TextView firstNameView;
    private TextView lastNameView;
    private View focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        usernameView = (TextView) findViewById(R.id.loginSignUp);
        passwordView = (TextView) findViewById(R.id.passwordSignUp);
        confirmView = (TextView) findViewById(R.id.confirmSignUp);
        emailView = (TextView) findViewById(R.id.emailSignUp);
        firstNameView = (TextView) findViewById(R.id.firstNameSignUp);
        lastNameView = (TextView) findViewById(R.id.lastNameSignUp);
    }

    public void signup(View view) {
        attemptSignUp();
    }

    private void attemptSignUp() {
        final String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
        String confirm = confirmView.getText().toString();
        final String email = emailView.getText().toString();
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        boolean cancel = false;
        if (TextUtils.isEmpty(password)) {
            passwordView.setError("Введите пароль");
            focusView = passwordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            usernameView.setError("Введите ваш логин");
            focusView = usernameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            emailView.setError("Введите email");
            focusView = emailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(firstName)) {
            firstNameView.setError("Введите вашу фамилию");
            focusView = firstNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameView.setError("Введите ваше имя");
            focusView = lastNameView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) & password.length() < 6) {
            passwordView.setError("Пароль слишком лекгий");
            focusView = passwordView;
            cancel = true;
        } else {
            if (!TextUtils.equals(password, confirm)) {
                confirmView.setError("Пароли не совпадают");
                focusView = confirmView;
                cancel = true;
            }
        }
        if (cancel)
            focusView.requestFocus();
        else {
            final User user = new User();
            user.setLastName(lastName);
            user.setFirstName(firstName);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            ApiFactory.getRememberService().createUser(username, password, email, lastName, firstName)
                    .enqueue(new Callback<SignUpError>() {
                        @Override
                        public void onResponse(Response<SignUpError> response, Retrofit retrofit) {
                            Log.d("code", response.code() + "");
                            if (response.body() != null) {
                                for (String error : response.body().getErrors()
                                        ) {
                                    switch (error) {
                                        case "username": {
                                            usernameView.setError("Данный логин уже существует");
                                            focusView = usernameView;
                                            focusView.requestFocus();
                                            break;
                                        }
                                        case "email": {
                                            emailView.setError("Данный email уже существует");
                                            focusView = emailView;
                                            focusView.requestFocus();
                                            break;
                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Регистрация прошла успешно", Toast.LENGTH_LONG);
                                onBackPressed();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d("code", "error");
                        }
                    });
        }
    }

    public void testSignUp(View view) {
        usernameView.setText("zahit");
        emailView.setText("zahit@mail.ru");
        lastNameView.setText("Zagit");
        firstNameView.setText("Talipov");
        passwordView.setText("aminaa");
        confirmView.setText("aminaa");
    }
}
