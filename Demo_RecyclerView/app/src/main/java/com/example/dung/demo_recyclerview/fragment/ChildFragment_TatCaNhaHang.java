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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyAlertDialog;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
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

public class ChildFragment_TatCaNhaHang extends Fragment {
    TextView tv_reload;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    NhaHangRecyclerAdapter nhaHangRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<NhaHang> listNhaHang = new ArrayList<>();
    Context context;

    public ChildFragment_TatCaNhaHang(){
        context = MainActivity.getMainActivityContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_recyclerview_nhahang, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar_in_recyclerview_nhahang);
        progressBar.setVisibility(View.VISIBLE);
        tv_reload = (TextView)view.findViewById(R.id.textView_reload_behind_recyclerview_nhahang);
        tv_reload.setVisibility(View.GONE);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_nhahang);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        initializeData();
        nhaHangRecyclerAdapter = new NhaHangRecyclerAdapter(listNhaHang, (MainActivity)context);
        recyclerView.setAdapter(nhaHangRecyclerAdapter);

        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeData();
            }
        });
    }

    public void initializeData(){
        progressBar.setVisibility(View.VISIBLE);
        APIService apiService;
        apiService = ApiUtils.getAPIService();
        apiService.getAllNhaHang().enqueue(new Callback<List<NhaHang>>() {
            @Override
            public void onResponse(Call<List<NhaHang>> call, Response<List<NhaHang>> response) {
                try{
                    listNhaHang = response.body();
                    Log.d("response.body()", response.body().get(0).getHinhAnh());
                    nhaHangRecyclerAdapter = new NhaHangRecyclerAdapter(listNhaHang, (MainActivity)context);
                    recyclerView.setAdapter(nhaHangRecyclerAdapter);

                    if(listNhaHang.size() == 0)
                        tv_reload.setVisibility(View.VISIBLE);
                    else
                        tv_reload.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
                catch (Exception e){
                    if(e.getMessage() != null)
                        Log.d("Error get all NhaHang", e.getMessage());
                    showLoadFailedDialog();
                    tv_reload.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<NhaHang>> call, Throwable t) {
                Log.d("onFailure getallNhaHang", call.toString());
                showLoadFailedDialog();
                tv_reload.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        isShowAlertDialog = false;
        nhaHangRecyclerAdapter = new NhaHangRecyclerAdapter(listNhaHang, (MainActivity)context);
        recyclerView.setAdapter(nhaHangRecyclerAdapter);
        setRetainInstance(false);
    }

    private static boolean isShowAlertDialog;
    public void showLoadFailedDialog(){
        if(!isShowAlertDialog){
            MyAlertDialog.showMyAlertDialog("Thông báo", "Không tải được danh sách nhà hàng. Hãy thử lại!");
            isShowAlertDialog = true;
        }
    }
}
