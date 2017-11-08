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
import com.example.dung.demo_recyclerview.MyHttpURLConnection;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    Context context;
    public ChildFragment_TatCaMonAn(){
        context = MainActivity.getMainActivityContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_recyclerview_monan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Connect to views:
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_monan_child);
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //
        data = new ArrayList<>();
        initialData();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(customRecyclerAdapter == null)
                customRecyclerAdapter = new MonAnRecyclerAdapter(data);
            else
                customRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initialData();
    }

    //Load du lieu theo loai da chon:
    private void initialData(){
        String api = "http://orderfooduit.azurewebsites.net/api/MonAn/Get";
        new DownloadFilesTask().execute(api);

    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            try{
                String jsonString = MyHttpURLConnection.sendGet("http://orderfooduit.azurewebsites.net/api/MonAn/Get");
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
