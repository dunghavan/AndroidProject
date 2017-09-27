package com.example.dung.demo_recyclerview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Dung on 9/27/2017.
 */

public class CheckInternetState {
    public static boolean checkInternetConnection() {

        Context context = MainActivity.getMainActivityContext();

        ConnectivityManager connManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(context, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(context, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(context, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(context, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }
}
