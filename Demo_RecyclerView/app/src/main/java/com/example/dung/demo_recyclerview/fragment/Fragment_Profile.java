package com.example.dung.demo_recyclerview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.LoginActivity;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.R;
import com.facebook.login.Login;
import com.squareup.picasso.Picasso;


/**
 * Created by Dung on 8/24/2017.
 */

public class Fragment_Profile extends Fragment {

    TextView tv_username;
    TextView tv_id;
    TextView tv_logout;
    ImageView imgview_avatar;
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

        tv_username.setText(LoginActivity.NAME);
        tv_id.setText("ID: " + LoginActivity.ID);

        tv_logout = (TextView)view.findViewById(R.id.textView_logout_in_profile);
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        imgview_avatar = (ImageView)view.findViewById(R.id.imageView_avatar);
        String url = "https://graph.facebook.com/" + LoginActivity.ID + "/picture?type=large";
        if (url != null && !url.isEmpty()) {
            Picasso.with(MainActivity.getMainActivityContext())
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.failed_load_food)
                    .fit()
                    .into(imgview_avatar);
        }
    }
}
