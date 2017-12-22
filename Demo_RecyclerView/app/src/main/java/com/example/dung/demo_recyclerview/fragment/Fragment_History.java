package com.example.dung.demo_recyclerview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.LoginActivity;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.Payment;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.recycler_adapter.NhaHangRecyclerAdapter;
import com.example.dung.demo_recyclerview.recycler_adapter.Payment_RecyclerAdapter;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dung on 12/22/2017.
 */

public class Fragment_History extends Fragment {
    private List<Payment> listData;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Payment_RecyclerAdapter adapter;

    TextView tv_username, tv_maKhachHang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        tv_username = (TextView)view.findViewById(R.id.textview_username_in_history);
        tv_maKhachHang = (TextView)view.findViewById(R.id.textview_maKhachHang_in_history);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_history);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MyApplication.getCurrentContext());
        recyclerView.setLayoutManager(layoutManager);

        listData = new ArrayList<>();
        initializeData();
        adapter = new Payment_RecyclerAdapter(listData, MyApplication.getCurrentContext());
        recyclerView.setAdapter(adapter);
    }

    private void initializeData(){
        APIService apiService = ApiUtils.getAPIService();
        apiService.getAllPayment(LoginActivity.ID).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                try{
                    listData = response.body();
                    adapter = new Payment_RecyclerAdapter(listData ,MyApplication.getCurrentContext());
                    recyclerView.setAdapter(adapter);
                    setRetainInstance(false);
                }
                catch (Exception e){
                    Log.d("Err parse payment", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Log.d("Parse payment onFailure", t.getMessage());
            }
        });
        tv_username.setText("Khách hàng: " + LoginActivity.NAME);
        tv_maKhachHang.setText("Mã khách hàng: " + LoginActivity.ID);
    }

    @Override
    public void onResume(){
        super.onResume();
        initializeData();
    }
}
