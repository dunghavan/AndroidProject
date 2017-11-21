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

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyConstant;
import com.example.dung.demo_recyclerview.MyHttpURLConnection;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;

import org.json.JSONArray;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Dung on 10/6/2017.
 */

public class ChildFragment_TatCaMonAn extends Fragment{
    RecyclerView recyclerView;
    MonAnRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<MonAn> data;
    int foodCategoryName;
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
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //
        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);

        foodCategoryName = getArguments().getInt(MyConstant.KEY_FOR_CATEGORY_FOOD);
        tabCategory = getArguments().getString(MyConstant.KEY_FOR_CATEGORY_TAB);
        initialData(foodCategoryName, tabCategory);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d("Call method", "setUserVisibleHint");
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initialData(foodCategoryName, tabCategory);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //initialData(foodCategoryName, tabCategory);
    }

    //Load du lieu theo loai da chon:
    private void initialData(int foodCategoryName, String tabCategory){
        Log.d("Call method", "initialData");
        String api = "http://orderfooduit.azurewebsites.net/api/MonAn/GetByMaLoai/" + String.valueOf(foodCategoryName);
        new ReadApiTask().execute(api);
        Log.d("API", api);

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
            customRecyclerAdapter = new MonAnRecyclerAdapter(data);
            recyclerView.setAdapter(customRecyclerAdapter);
            setRetainInstance(false);
        }
    }
}
