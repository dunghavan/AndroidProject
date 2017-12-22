package com.example.dung.demo_recyclerview.fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.dung.demo_recyclerview.MainActivity;
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
        //initialData(foodCategoryName, tabCategory);
    }

    //Load du lieu theo loai da chon:
    private void initialData(int _foodCategory, String _tabCategory){
//        Log.d("Call method", "initialData");
//        String api = "http://orderfooduit.azurewebsites.net/api/MonAn/GetByMaLoai/" + String.valueOf(foodCategoryName);
//        new ReadApiTask().execute(api);
//        Log.d("API", api);

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
                    Log.d("Err maLoai onFailure", t.getMessage());
                    Toast.makeText(MyApplication.getCurrentContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

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
                }
            });
        }

    }

    private class ReadApiTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            Log.d("Call method", "doInBackground");
            try{
                String jsonString = MyHttpURLConnection.sendGet(urls[0]);
                return  jsonString;
            }
            catch (Exception e){
                Log.d("Error while read api: ", e.getMessage());
            }
            return "";
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Call method", "onPostExecute");
            data.clear();
            try{
                JSONArray jsonArray = new JSONArray(result);

                for(int i = 0; i < jsonArray.length(); i++){
                    ObjectMapper objectMapper = new ObjectMapper();
                    MonAn m = objectMapper.readValue(jsonArray.getJSONObject(i).toString(), MonAn.class);
                    data.add(m);
                }
            }
            catch (Exception e){
                Log.d("Error parse Json: ", e.getMessage());
            }
            Log.d("Data length: ", String.valueOf(data.size()));
            Toast.makeText(MyApplication.getCurrentContext(), "Data length: " + String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
            customRecyclerAdapter = new MonAnRecyclerAdapter(data);
            recyclerView.setAdapter(customRecyclerAdapter);
            setRetainInstance(false);
        }
    }
}
