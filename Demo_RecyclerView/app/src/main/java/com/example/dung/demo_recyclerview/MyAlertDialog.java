package com.example.dung.demo_recyclerview;

import android.support.v7.app.AlertDialog;

/**
 * Created by Dung on 12/24/2017.
 */

public class MyAlertDialog {
    static AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyApplication.getCurrentContext());
    public static void showMyAlertDialog(String title, String message){
        alertDialogBuilder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
