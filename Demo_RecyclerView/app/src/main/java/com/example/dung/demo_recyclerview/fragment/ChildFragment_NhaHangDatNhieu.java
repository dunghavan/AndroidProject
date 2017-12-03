package com.example.dung.demo_recyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.recycler_adapter.NhaHangRecyclerAdapter;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dung on 10/7/2017.
 */

public class ChildFragment_NhaHangDatNhieu extends Fragment {
    RecyclerView recyclerView;
    NhaHangRecyclerAdapter nhaHangRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<NhaHang> listNhaHang;
    Context context;
    APIService apiService;

    public ChildFragment_NhaHangDatNhieu(){
        context = MainActivity.getMainActivityContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_recyclerview_nhahang, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_nhahang);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        listNhaHang = new ArrayList<>();

        initializeData();
        nhaHangRecyclerAdapter = new NhaHangRecyclerAdapter(listNhaHang, (MainActivity)context);
        recyclerView.setAdapter(nhaHangRecyclerAdapter);
    }

    public void initializeData(){
        apiService = ApiUtils.getAPIService();
        apiService.getAllNhaHang().enqueue(new Callback<List<NhaHang>>() {
            @Override
            public void onResponse(Call<List<NhaHang>> call, Response<List<NhaHang>> response) {
                try{
                    listNhaHang = response.body();
                    Log.d("response.body()", response.body().get(0).getHinhAnh());
                    nhaHangRecyclerAdapter = new NhaHangRecyclerAdapter(listNhaHang, (MainActivity)context);
                    recyclerView.setAdapter(nhaHangRecyclerAdapter);
                }
                catch (Exception e){
                    if(e.getMessage() != null)
                        Log.d("Error get all NhaHang", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<NhaHang>> call, Throwable t) {
                Log.d("onFailure getallNhaHang", call.toString());
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        initializeData();
    }
}
