package com.example.dung.demo_recyclerview.fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyAlertDialog;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.MyConstant;
import com.example.dung.demo_recyclerview.MyHttpURLConnection;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Dung on 10/6/2017.
 */

public class ChildFragment_TatCaMonAn extends Fragment{
    RecyclerView recyclerView;
    MonAnRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    List<MonAn> data;
    int foodCategory;
    String tabCategory;

    Context context;
    public ChildFragment_TatCaMonAn(){
        context = MainActivity.getMainActivityContext();
        data = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_recyclerview_monan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("Call method", "onViewCreated");
        //Connect to views:
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_monan_child);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        progressBar.setMax(100);
        progressBar.setProgress(5);
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //
        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);

        foodCategory = getArguments().getInt(MyConstant.KEY_FOR_CATEGORY_FOOD);
        tabCategory = getArguments().getString(MyConstant.KEY_FOR_CATEGORY_TAB);
        initialData(foodCategory, tabCategory);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("Call method", "setUserVisibleHint");
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            initialData(foodCategoryName, tabCategory);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();

        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);
        setRetainInstance(false);
    }

    //Load du lieu theo loai da chon:
    boolean isShowAlertDialog = false;
    private void initialData(final int _foodCategory, String _tabCategory){
//        Log.d("Call method", "initialData");
//        String api = "http://orderfooduit.azurewebsites.net/api/MonAn/GetByMaLoai/" + String.valueOf(foodCategoryName);
//        new ReadApiTask().execute(api);
//        Log.d("API", api);
        isShowAlertDialog = false;
        APIService apiService = ApiUtils.getAPIService();
        if(_tabCategory == MyConstant.DATNHIEU){
            apiService.getMonAnDatNhieuByMaLoai(_foodCategory).enqueue(new Callback<List<MonAn>>() {
                @Override
                public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                    try{
                        progressBar.setVisibility(View.VISIBLE);
//                        data = response.body();
//                        Log.d("Data length: ", String.valueOf(data.size()));

                        //Test progressBar
                        int n = response.body().size();
                        progressBar.setMax(n - 1);
                        for(int i = 0; i < n; i++) {
                            data.add(response.body().get(i));
                            progressBar.setProgress(i);
                        }

                        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
                        recyclerView.setAdapter(customRecyclerAdapter);
                        setRetainInstance(false);
                        progressBar.setVisibility(View.GONE);

                    }
                    catch (Exception e){
                        Log.d("Err ma Loai", e.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<MonAn>> call, Throwable t) {
                    if(!isShowAlertDialog){
                        MyAlertDialog.showMyAlertDialog("Thông báo", "Không tải được danh sách món ăn đặt nhiều mã loại: " + _foodCategory + ", hãy thử lại!");
                        isShowAlertDialog = true;
                    }
                }
            });
        }
        else {
            apiService.getAllMonAnByMaLoai(_foodCategory).enqueue(new Callback<List<MonAn>>() {
                @Override
                public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                    try{
                        progressBar.setVisibility(View.VISIBLE);
//                        data = response.body();
//                        Log.d("Data length: ", String.valueOf(data.size()));

                        //Test progressBar
                        int n = response.body().size();
                        progressBar.setMax(n - 1);
                        for(int i = 0; i < n; i++) {
                            data.add(response.body().get(i));
                            progressBar.setProgress(i);
                        }

                        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
                        recyclerView.setAdapter(customRecyclerAdapter);
                        setRetainInstance(false);
                        progressBar.setVisibility(View.GONE);
                    }
                    catch (Exception e){
                        Log.d("Err ma Loai", e.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<MonAn>> call, Throwable t) {
                    Log.d("Err maLoai onFailure", t.getMessage());
                    Toast.makeText(MyApplication.getCurrentContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    if(!isShowAlertDialog){
                        MyAlertDialog.showMyAlertDialog("Thông báo", "Không tải được danh sách tất cả món ăn mã loại: " + _foodCategory + ", hãy thử lại!");
                        isShowAlertDialog = true;
                    }
                }
            });
        }

    }

}
