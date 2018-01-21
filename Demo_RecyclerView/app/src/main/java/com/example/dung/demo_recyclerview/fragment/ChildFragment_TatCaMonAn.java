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
import android.widget.Toast;

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyAlertDialog;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.MyConstant;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dung on 10/6/2017.
 */

public class ChildFragment_TatCaMonAn extends Fragment{
    TextView tv_reload;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MonAnRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
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
        tv_reload = (TextView)view.findViewById(R.id.textView_reload_behind_recyclerview_monan);
        tv_reload.setVisibility(View.GONE);
        //Connect to views:
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_monan_child);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar_in_recyclerview_monan);
        progressBar.setVisibility(View.VISIBLE);
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

        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialData(foodCategory, tabCategory);
            }
        });
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

        isShowAlertDialog = false;
        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);
        setRetainInstance(false);
    }

    //Load du lieu theo loai da chon:
    static boolean isShowAlertDialog = false;
    private void initialData(final int _foodCategory, String _tabCategory){
        progressBar.setVisibility(View.VISIBLE);
        //isShowAlertDialog = false;
        APIService apiService = ApiUtils.getAPIService();
        if(_tabCategory == MyConstant.DATNHIEU){
            apiService.getMonAnDatNhieuByMaLoai(_foodCategory).enqueue(new Callback<List<MonAn>>() {
                @Override
                public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                    try{
                        data = response.body();
                        Log.d("Data length: ", String.valueOf(data.size()));

                        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
                        recyclerView.setAdapter(customRecyclerAdapter);
                        setRetainInstance(false);
                        progressBar.setVisibility(View.GONE);
                        if(data.size() != 0)
                            tv_reload.setVisibility(View.GONE);
                        else
                            tv_reload.setVisibility(View.VISIBLE);

                    }
                    catch (Exception e){
                        Log.d("Err ma Loai", e.getMessage());
                        progressBar.setVisibility(View.GONE);
                        tv_reload.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<MonAn>> call, Throwable t) {
                    showLoadFailedDialog();
                    progressBar.setVisibility(View.GONE);
                    tv_reload.setVisibility(View.VISIBLE);
                }
            });
        }
        else {
            apiService.getAllMonAnByMaLoai(_foodCategory).enqueue(new Callback<List<MonAn>>() {
                @Override
                public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                    try{
                        data = response.body();
                        Log.d("Data length: ", String.valueOf(data.size()));

                        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
                        recyclerView.setAdapter(customRecyclerAdapter);
                        setRetainInstance(false);
                        progressBar.setVisibility(View.GONE);
                        if(data.size() != 0)
                            tv_reload.setVisibility(View.GONE);
                        else
                            tv_reload.setVisibility(View.VISIBLE);
                    }
                    catch (Exception e){
                        Log.d("Err ma Loai", e.getMessage());
                        progressBar.setVisibility(View.GONE);
                        tv_reload.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<MonAn>> call, Throwable t) {
                    //Toast.makeText(MyApplication.getCurrentContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    showLoadFailedDialog();
                    progressBar.setVisibility(View.GONE);
                    tv_reload.setVisibility(View.VISIBLE);
                }
            });
        }

    }
    public void showLoadFailedDialog(){
        if(!isShowAlertDialog){
            MyAlertDialog.showMyAlertDialog("Thông báo", "Không tải được danh sách món ăn. Hãy thử lại!");
            isShowAlertDialog = true;
        }
    }

}
