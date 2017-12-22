package com.example.dung.demo_recyclerview;
//hash key: Y6GdAOm1lkSoWnONzJs3j5ZEnbw=
//website: http://www.howkteam.vn/course/khoa-hoc-lap-trinh-android-co-ban/tich-hop-mang-xa-hoi--tao-app-facebook-1219
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.model.Facebook_Profile;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * Created by Dung on 12/3/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    //private static boolean isAuthenticated = false;
    public static Facebook_Profile facebook_profile;
    public static String NAME = "";
    public static String ID = "";
    static LoginActivity activity;
    CallbackManager callbackManager;
    LoginButton fbLoginButton;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this; //Used to close Activity in static method
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

                getFbInfo();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private static void getFbInfo() {
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

                                if(activity != null)
                                    activity.finish();
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
