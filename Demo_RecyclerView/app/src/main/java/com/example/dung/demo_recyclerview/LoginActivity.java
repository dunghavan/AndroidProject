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

import com.example.dung.demo_recyclerview.fragment.Fragment_Profile;
import com.example.dung.demo_recyclerview.model.Facebook_Profile;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public static String EMAIL = "";
    public static boolean isLoggedInWithFacebook;
    static LoginActivity activity;
    CallbackManager callbackManager;
    LoginButton fbLoginButton;
    TextView actionBarTitle;
    TextView tv_label;

    public static String googleImageUrl = "";


    public interface OnUpdateListener{
        void onUpdateCartUI();
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
    private static GoogleApiClient mGoogleApiClient;
    private SignInButton  btn_googleLogin;
    Button btn_googleLogout, btn_googleRevokeAccess;
    private ProgressDialog mProgressDialog;
    static GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this; //Used to close Activity in static method

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        tv_label = (TextView)findViewById(R.id.textview_label_in_login_activity);
        btn_googleLogin = (SignInButton )findViewById(R.id.google_login_button);
        btn_googleLogin.setSize(SignInButton.SIZE_WIDE);
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


        fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        //https://developers.facebook.com/docs/facebook-login/permissions#reference
        fbLoginButton.setReadPermissions("email");

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "======Facebook login success======");
                Log.d(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
                Toast.makeText(MyApplication.getCurrentContext(), "Login Facebook success.", Toast.LENGTH_SHORT).show();

                Fragment_Profile.isFromLogoutRequest = false;
                getFbInfo();
                //finish();
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
                googleSignIn();
                break;

            case R.id.google_logout_button:
                googleSignOut();
                break;

            case R.id.google_revoke_access_button:
                googleRevokeAccess();
                break;
        }
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void googleSignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                        finish();
                    }
                });
    }

    private void googleRevokeAccess() {
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

        getGoogleInfor();
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        if(!mProgressDialog.isShowing())
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
            String googleID = acct.getId();

            Log.e(TAG, "Name: " + personName + ", email: " + email + ", ID: " + googleID);

            ID = googleID;
            NAME = acct.getDisplayName();
            EMAIL = email;
            if(acct.getPhotoUrl() != null)
                googleImageUrl = acct.getPhotoUrl().toString();
            else
                googleImageUrl = "";
            //Send information to server
            APIService apiService = ApiUtils.getAPIService();
            apiService.guiThongTinKhachHang(NAME, EMAIL, ID).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(MyApplication.getCurrentContext(), "Send gg infor success" + ID, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MyApplication.getCurrentContext(), "Send gg infor failure" + ID, Toast.LENGTH_SHORT).show();
                }
            });

            isLoggedInWithFacebook = false;
            updateUI(true);
            //finish();
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
        updateUIByInterface();
    }

    private void updateUI(boolean isSignedInWithGoogle) {
        if (isSignedInWithGoogle) {
            btn_googleLogin.setVisibility(View.GONE);
            btn_googleLogout.setVisibility(View.VISIBLE);
            btn_googleRevokeAccess.setVisibility(View.GONE);

            fbLoginButton.setVisibility(View.GONE);
            tv_label.setVisibility(View.VISIBLE);
            tv_label.setText("Bạn đã đăng nhập với tài khoản " + EMAIL);
        } else {
            if(isLoggedInWithFacebook){
                btn_googleLogin.setVisibility(View.GONE);
                btn_googleLogout.setVisibility(View.GONE);
                btn_googleRevokeAccess.setVisibility(View.GONE);

                fbLoginButton.setVisibility(View.VISIBLE);
                tv_label.setVisibility(View.GONE);
            }
            else {
                btn_googleLogin.setVisibility(View.VISIBLE);
                btn_googleLogout.setVisibility(View.GONE);
                btn_googleRevokeAccess.setVisibility(View.GONE);

                fbLoginButton.setVisibility(View.VISIBLE);
                tv_label.setVisibility(View.VISIBLE);
                tv_label.setText("Bạn chưa đăng nhập. Đăng nhập với Facebook hoặc Google để tiếp tục");
            }
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

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            finish();
        }
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
                                EMAIL = me.optString("email");

                                //Send information to server
                                APIService apiService = ApiUtils.getAPIService();
                                apiService.guiThongTinKhachHang(NAME, EMAIL, ID).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(MyApplication.getCurrentContext(), "Send fb infor success" + ID, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(MyApplication.getCurrentContext(), "Send fb infor failure" + ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                updateUIByInterface();
                                isLoggedInWithFacebook = true;

                                //CartActivity.isCheckAuthen = true;
                                //Toast.makeText(MyApplication.getCurrentContext(), "Name: " + me.optString("name"), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(MyApplication.getCurrentContext(), "ID: " + me.optString("id"), Toast.LENGTH_SHORT).show();

                                if(activity != null && !Fragment_Profile.isFromLogoutRequest)
                                    activity.finish();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            //parameters.putString("fields", "id, name, link, gender, birthday, email, user_mobile_phone");
            parameters.putString("fields", "id, name, link, email");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    private static void updateUIByInterface(){
        if(listener != null)
            listener.onUpdateCartUI();
        if(ui_listner != null)
            ui_listner.updateProfileUI();
    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);


        // Check fb
        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token == null){
            isLoggedInWithFacebook = false;
        }

        // Check Google and updateUI
        getGoogleInfor();
    }

    public void getGoogleInfor(){

        if(mGoogleApiClient == null)
            return;
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

    public static boolean isAuthenticated() {
        CartActivity.isCheckAuthen = true;
        // FB
        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token != null){
            getFbInfo();
            return true;
        }

        // Google
        if(mGoogleApiClient == null){
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(MyApplication.getCurrentContext())
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                ID = acct.getId();
                NAME = acct.getDisplayName();
                if(acct.getPhotoUrl() != null)
                    googleImageUrl = acct.getPhotoUrl().toString();

                updateUIByInterface();
                return true;
            }
        }

        ID = null;
        NAME = null;
        return false;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( mProgressDialog != null && mProgressDialog.isShowing() ){
            mProgressDialog.cancel();
        }
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
