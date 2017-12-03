package com.example.dung.demo_recyclerview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Dung on 10/27/2017.
 */

public class MyApplication extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        AppEventsLogger.activateApp(this);
        context = getApplicationContext();
    }
    public static synchronized Context getCurrentContext(){
        return context;
    }
    public static void setCurrentContext(Activity activity){
        context = activity;
    }
}
