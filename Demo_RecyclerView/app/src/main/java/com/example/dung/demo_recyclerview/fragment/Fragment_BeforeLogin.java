package com.example.dung.demo_recyclerview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dung.demo_recyclerview.LoginActivity;
import com.example.dung.demo_recyclerview.MyConstant;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;

/**
 * Created by Dung on 12/16/2017.
 */

public class Fragment_BeforeLogin extends Fragment {

    Button btn_login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_before_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("Call method", "onViewCreated");
        //Connect to views:
        btn_login = (Button)view.findViewById(R.id.login_button);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
