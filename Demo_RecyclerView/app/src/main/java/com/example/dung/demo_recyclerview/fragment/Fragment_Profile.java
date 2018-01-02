package com.example.dung.demo_recyclerview.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.Input_Activity;
import com.example.dung.demo_recyclerview.LoginActivity;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.R;
import com.facebook.login.Login;
import com.squareup.picasso.Picasso;


/**
 * Created by Dung on 8/24/2017.
 */

public class Fragment_Profile extends Fragment implements LoginActivity.OnUpdateProfileUIListener {

    TextView tv_username;
    TextView tv_id;
    TextView tv_logout;
    ImageView imgview_avatar;

    TextView tv_tuVanNhanh;
    TextView tv_tuVanKhauPhanAn;

    LoginActivity loginActivity; //for updateUI
    public Fragment_Profile(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tv_username = (TextView)view.findViewById(R.id.textView_username_in_profile);
        tv_id = (TextView)view.findViewById(R.id.textView_id_in_profile);
        tv_logout = (TextView)view.findViewById(R.id.textView_logout_in_profile);
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        imgview_avatar = (ImageView)view.findViewById(R.id.imageView_avatar);
        tv_tuVanNhanh = (TextView)view.findViewById(R.id.textView_message_user);
        tv_tuVanKhauPhanAn = (TextView)view.findViewById(R.id.textView_tu_van_khau_phan_an_user);
        loginActivity = new LoginActivity();
        loginActivity.setOnUpdateProfileUIListener(this);

        tv_tuVanNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tao giao dien alertDialog:
                LayoutInflater li = LayoutInflater.from(MyApplication.getCurrentContext());
                View alertDialogView = li.inflate(R.layout.alert_dialog_message, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyApplication.getCurrentContext());
                alertDialog.setView(alertDialogView);

                alertDialog.setCancelable(true)
                        .setTitle("Tư vấn nhanh")
                        .setNegativeButton("Hủy", null)
                        .setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Gọi API gửi message
                            }
                        });


                AlertDialog dialogToShow = alertDialog.create();
                dialogToShow.show();

            }
        });

        tv_tuVanKhauPhanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApplication.getCurrentContext(), Input_Activity.class);
                MyApplication.getCurrentContext().startActivity(intent);
            }
        });

        updateProfileUI();
    }

    public void updateProfileUI(){
        tv_username.setText(LoginActivity.NAME);
        tv_id.setText("ID: " + LoginActivity.ID);
        String url = "";
        if(LoginActivity.isLoggedInWithFacebook)
            url = "https://graph.facebook.com/" + LoginActivity.ID + "/picture?type=large";
        else
            url = LoginActivity.googleImageUrl;
        if (url != null && !url.isEmpty()) {
            Picasso.with(MainActivity.getMainActivityContext())
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.user_icon)
                    .fit()
                    .into(imgview_avatar);
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        updateProfileUI();
    }
}
