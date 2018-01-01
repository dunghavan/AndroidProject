package com.example.dung.demo_recyclerview;
//hash key: Y6GdAOm1lkSoWnONzJs3j5ZEnbw=
//website: http://www.howkteam.vn/course/khoa-hoc-lap-trinh-android-co-ban/tich-hop-mang-xa-hoi--tao-app-facebook-1219
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.model.Facebook_Profile;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

//import com.google.android.gms.auth.api.signin.GoogleSignInClient;


import org.json.JSONObject;

/**
 * Created by Dung on 12/3/2017.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
, View.OnClickListener{
    private static final String TAG = LoginActivity.class.getSimpleName();
    //private static boolean isAuthenticated = false;
    public static Facebook_Profile facebook_profile;
    public static String NAME = "";
    public static String ID = "";
    static LoginActivity activity;
    CallbackManager callbackManager;
    LoginButton fbLoginButton;
    TextView actionBarTitle;

    public interface OnUpdateListener{
        void onUpdateUI();
    }
    public interface OnUpdateProfileUIListener{
        void updateProfileUI();
    }
    static OnUpdateProfileUIListener ui_listner;
    public void setOnUpdateProfileUIListener(OnUpdateProfileUIListener ui_listner){
        this.ui_listner = ui_listner;
    }
    static OnUpdateListener listener;
    public void setOnUpdateListener(OnUpdateListener listener){
        this.listener = listener;
    }

    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton  btn_googleLogin;
    Button btn_googleLogout, btn_googleRevokeAccess;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this; //Used to close Activity in static method

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btn_googleLogin = (SignInButton )findViewById(R.id.google_login_button);
        btn_googleLogout = (Button)findViewById(R.id.google_logout_button);
        btn_googleRevokeAccess = (Button)findViewById(R.id.google_revoke_access_button);
        btn_googleLogin.setOnClickListener(this);
        btn_googleLogout.setOnClickListener(this);
        btn_googleRevokeAccess.setOnClickListener(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);
        if(isAuthenticated())
            actionBarTitle.setText("Đăng xuất");
        else
            actionBarTitle.setText("Đăng nhập");

        callbackManager = CallbackManager.Factory.create();
        facebook_profile = new Facebook_Profile();

        SignInButton googleSignInButton = findViewById(R.id.google_login_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        //https://developers.facebook.com/docs/facebook-login/permissions#reference
        fbLoginButton.setReadPermissions("email");

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "======Facebook login success======");
                Log.d(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
                Toast.makeText(MyApplication.getCurrentContext(), "Login Facebook success.", Toast.LENGTH_SHORT).show();

                getFbInfo();
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MyApplication.getCurrentContext(), "Login Facebook cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "======Facebook login error======");
                Log.e(TAG, "Error: " + error.toString());
                Toast.makeText(MyApplication.getCurrentContext(), "Login Facebook error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.google_login_button:
                signIn();
                break;

            case R.id.google_logout_button:
                signOut();
                break;

            case R.id.google_revoke_access_button:
                revokeAccess();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            //String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email);

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btn_googleLogin.setVisibility(View.GONE);
            btn_googleLogout.setVisibility(View.VISIBLE);
            btn_googleRevokeAccess.setVisibility(View.VISIBLE);
        } else {
            btn_googleLogin.setVisibility(View.VISIBLE);
            btn_googleLogout.setVisibility(View.GONE);
            btn_googleRevokeAccess.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static void getFbInfo() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                Log.i("Login: ", me.optString("name"));
                                Log.i("ID: ", me.optString("id"));
                                Log.i("ID: ", me.optString("picture"));

                                ID = me.optString("id");
                                NAME = me.optString("name");

//                                facebook_profile.setId(me.optString("id"));
//                                facebook_profile.setName(me.optString("name"));
//                                facebook_profile.setGender(me.optString("gender"));
//                                facebook_profile.setBirthday(me.optString("birthday"));
//                                facebook_profile.setEmail(me.optString("email"));
//                                facebook_profile.setUser_mobile_phone(me.optString("user_mobile_phone"));

                                if(listener != null)
                                    listener.onUpdateUI();
                                if(ui_listner != null)
                                    ui_listner.updateProfileUI();

                                CartActivity.isCheckAuthen = true;
                                Toast.makeText(MyApplication.getCurrentContext(), "Name: " + me.optString("name"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(MyApplication.getCurrentContext(), "ID: " + me.optString("id"), Toast.LENGTH_SHORT).show();

//                                if(activity != null)
//                                    activity.finish();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            //parameters.putString("fields", "id, name, link, gender, birthday, email, user_mobile_phone");
            parameters.putString("fields", "id, name, link");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
    }

    public static boolean isAuthenticated() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token != null){
            getFbInfo();
            return true;
        }
        ID = null;
        NAME = null;
        return false;
    }

    public static void logout(){
        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token != null){
            LoginManager.getInstance().logOut();
        }
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken == null){
                finish();
            }
        }
    };
}
