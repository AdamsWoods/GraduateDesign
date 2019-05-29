package com.example.news.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.news.R;
import com.example.news.bean.LoginData;
import com.example.news.config.CONFIG;
import com.example.news.feedback.FeedBackActivity;
import com.example.news.history.HistoryActivity;
import com.example.news.news.MainActivity;
import com.example.news.setting.SettingsActivity;
import com.example.news.util.GsonUtils;
import com.example.news.video.VideoActivity;
import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements
        LoaderCallbacks<Cursor>, LoginContact.View{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginPresenter mpresenter;
    private UserLoginTask mAuthTask = null;
    private EventHandler eventHandler;
    private MyHandler myHandler = new MyHandler();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mCodeView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageView headImgView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        //set status block color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        //set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        //设置DrawerLayout
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(LoginActivity.this);

        MobSDK.init(this);//mob短信api初始化

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById (R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);

        //获取验证码按钮
        Button getCodeButton = findViewById(R.id.getcode);
        getCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                mpresenter.smsRequest(mEmailView.getText().toString());
            }
        });

        mCodeView = findViewById(R.id.code);

        //登陆注册按钮
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        setPresenter(new LoginPresenter(this));
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    public void setPresenter(Object presenter) {
        mpresenter = (LoginPresenter) presenter;
    }

    @Override
    public void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        String code = mCodeView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if ( !mpresenter.isPasswordValid(password)) {
            Snackbar.make(mLoginFormView, "password 不正确", Snackbar.LENGTH_LONG).show();
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (!mpresenter.isAccountNumberValid(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

//        if (!mpresenter.isCodeValid(code)){
//            mCodeView.setError("The code is wrong");
//            focusView = mCodeView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mpresenter.loginRequest(getApplicationContext(), email, password, new LoginCallBack() {
                @Override
                public void onSuccess(String tag, Object object ) {
                    CONFIG.LOGINSTATUS = true;
                    CONFIG.USER_NAME = email;
//                    Log.e("设置头像", (String)object);
//                    setHeadImg((String)object);
                    finish();
                }

                @Override
                public void onFail(String tag, Object e) {
                    showProgress(false);
                    mEmailView.requestFocus();
                    mEmailView.setError("Account or Password is incorrect");
                }

                @Override
                public void onNotFound(String tag, Object object) {
                    showProgress(false);
                    mpresenter.setDialog(LoginActivity.this, email, password, new LoginCallBack() {
                        @Override
                        public void onSuccess(String tag, Object object) {
                            showMessage("注册登陆成功");
                            CONFIG.LOGINSTATUS = true;
                            CONFIG.USER_NAME = email;
                            CONFIG.Head_URL = (String)object;
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFail(String tag, Object e) {

                        }

                        @Override
                        public void onNotFound(String tag, Object object) {

                        }
                    });
                }
            });
//            mAuthTask = new UserLoginTask(email, password);
////            mAuthTask.execute();
//
//            int status = mpresenter.loginRequest(getApplicationContext(), email, password);
//            Log.e("LoginStatus", status+"");
//            if (0 == status) {
//                CONFIG.LOGINSTATUS = true;
//                CONFIG.USER_NAME = email;
//                finish();
//            } else if (1 == status){
//                showProgress(false);
//                mEmailView.requestFocus();
//                mEmailView.setError("Account or Password is incorrect");
//            } else if (2 == status){
//                showProgress(false);
//                int statusR = mpresenter.setDialog(getApplicationContext(), email, password);
//                if (statusR == 0){
//                    showMessage("注册登陆成功！");
//                    finish();
//                }
//            }
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(this.mLoginFormView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setHeadImg(String url) {
        Glide.with(LoginActivity.this).load(url)
                .into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView));
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_news) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_video) {
//            Intent intent = new Intent(this, VideoActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_history) {
//            Intent intent = new Intent(this, HistoryActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_manage) {
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_login) {
//
//        } else if (id == R.id.nav_feedback) {
//            Intent intent = new Intent(this, FeedBackActivity.class);
//            startActivity(intent);
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private boolean loginStatus = false;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            mpresenter.loginRequest(getApplicationContext(), mEmail, mPassword,);
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            int status = LoginPresenter.loginStatus;
            if (0 == status) {
                CONFIG.LOGINSTATUS = true;
                CONFIG.USER_NAME = mEmail;
                finish();
            } else if (1 == status){
                showProgress(false);
                mEmailView.requestFocus();
                mEmailView.setError("Account or Password is incorrect");
            } else if (2 == status){
                showProgress(false);
                mpresenter.setDialog(LoginActivity.this, mEmail, mPassword, new LoginCallBack(){

                    @Override
                    public void onSuccess(String tag, Object object) {

                    }

                    @Override
                    public void onFail(String tag, Object e) {

                    }

                    @Override
                    public void onNotFound(String tag, Object object) {

                    }
                });
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public interface LoginCallBack {
        void onSuccess(String tag, Object object);
        void onFail(String tag, Object e);
        void onNotFound(String tag, Object object);
    }
}