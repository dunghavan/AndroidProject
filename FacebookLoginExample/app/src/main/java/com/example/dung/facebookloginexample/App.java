package com.example.dung.facebookloginexample;

import android.app.Application;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Dung on 11/30/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
