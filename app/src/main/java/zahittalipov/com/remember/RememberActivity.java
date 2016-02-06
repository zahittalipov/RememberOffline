package zahittalipov.com.remember;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.ResponseBody;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import zahittalipov.com.remember.database.DictionaryDBInterface;
import zahittalipov.com.remember.entity.User;
import zahittalipov.com.remember.fragments.DictionariesFragment;
import zahittalipov.com.remember.fragments.InfoFragment;
import zahittalipov.com.remember.fragments.ProfileFragment;
import zahittalipov.com.remember.service.ApiFactory;

/**
 * Created by Zahit Talipov on 14.12.2015.
 */
public class RememberActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static String FRAGMENT_INSTANCE_NAME = "fragment";
    View viewHeader;
    boolean check = false;
    boolean checkDownload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final String token = getIntent().getStringExtra("token");
        if (token == null & !AppDelegate.isExistUser()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (!AppDelegate.isExistUser() & token != null) {
            Callback<User> callback = new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    if (response.code() == 200) {
                        checkDownload = true;
                        User user = response.body();
                        user.setAccessToken(token);
                        AppDelegate.saveUser(user, getApplicationContext());
                        createDrawerLayout();
                        displayUser();
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            };
            ApiFactory.getRememberService().getUser(token).enqueue(callback);
        } else {
            createDrawerLayout();
            displayUser();
        }

    }


    void createDrawerLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
        switchFragment(R.id.nav_dictionaries);
    }

    private void displayUser() {
        TextView sLogin = (TextView) viewHeader.findViewById(R.id.session_name);
        sLogin.setText(String.format("%s %s", AppDelegate.getCurrentUser().getLastName(), AppDelegate.getCurrentUser().getFirstName()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!getFragmentManager().popBackStackImmediate())
                super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switchFragment(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(int id) {
        switch (id) {
            case R.id.nav_dictionaries: {
                Bundle bundle = new Bundle();
                bundle.putBoolean("update", checkDownload);
                DictionariesFragment fragment = new DictionariesFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutMy, fragment, "profile").commit();
                break;
            }
            case R.id.nav_exit: {
                DictionaryDBInterface dbInterface = DictionaryDBInterface.getInstance(getApplicationContext());
                dbInterface.deleteDB();
                checkLogout();
                break;
            }
            case R.id.nav_profile: {
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutMy, new ProfileFragment(), "profile").commit();
                break;
            }
            case R.id.nav_info: {
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutMy, new InfoFragment(), "profile").commit();
                break;
            }
            case R.id.nav_setting: {

            }
        }
    }

    private void checkLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выход");
        builder.setMessage(AppDelegate.getCurrentUser().getLastName() + ", вы уверены, что хотите выйти?");
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        ApiFactory.getRememberService().logout(AppDelegate.getCurrentUser().getAccessToken()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                        if (AppDelegate.logout(RememberActivity.this)) {
                            startActivity(new Intent(RememberActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                }
            }
        };
        builder.setNegativeButton("Отмена", listener);
        builder.setPositiveButton("Выйти", listener);
        builder.create().show();
    }
}