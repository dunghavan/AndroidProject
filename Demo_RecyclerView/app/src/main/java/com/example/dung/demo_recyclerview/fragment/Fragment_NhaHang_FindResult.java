package com.example.dung.demo_recyclerview.fragment;

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
import android.widget.TextView;

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.MyHttpURLConnection;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.recycler_adapter.NhaHangRecyclerAdapter;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dung on 11/13/2017.
 */

public class Fragment_NhaHang_FindResult extends Fragment {
    TextView tv_reload;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    NhaHangRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<NhaHang> searchResultData;
    APIService apiService;
    String nhahangKeyword = "";

    Context context;
    public Fragment_NhaHang_FindResult(){
        context = MainActivity.getMainActivityContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_recyclerview_monan, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tv_reload = (TextView)view.findViewById(R.id.textView_reload_behind_recyclerview_monan);
        tv_reload.setVisibility(View.GONE);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar_in_recyclerview_monan);
        progressBar.setVisibility(View.VISIBLE);

        //Connect to views:
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_monan_child);
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //
        nhahangKeyword += getArguments().getString("NHAHANG_KEYWORD");

        searchResultData = new ArrayList<>();
        initialData();
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialData();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if(adapter == null)
//                adapter = new NhaHangRecyclerAdapter(searchResultData, (MainActivity)MyApplication.getCurrentContext());
//            else
//                adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new NhaHangRecyclerAdapter(searchResultData, (MainActivity)MyApplication.getCurrentContext());
        recyclerView.setAdapter(adapter);
        setRetainInstance(false);
    }

    //Load du lieu theo loai da chon:
    private void initialData(){
        progressBar.setVisibility(View.VISIBLE);
        apiService = ApiUtils.getAPIService();
        apiService.searchNhaHang(nhahangKeyword).enqueue(new Callback<List<NhaHang>>() {
            @Override
            public void onResponse(Call<List<NhaHang>> call, Response<List<NhaHang>> response) {
                try{
                    searchResultData = response.body();
                    Log.d("Data length: ", String.valueOf(searchResultData.size()));
                    adapter = new NhaHangRecyclerAdapter(searchResultData, (MainActivity)MyApplication.getCurrentContext());
                    recyclerView.setAdapter(adapter);
                    setRetainInstance(false);
                    progressBar.setVisibility(View.GONE);
                    if(searchResultData.size() != 0)
                        tv_reload.setVisibility(View.GONE);
                    else
                        tv_reload.setVisibility(View.VISIBLE);
                }
                catch (Exception e){
                    Log.d("Error search NhaHang", e.getMessage());
                    tv_reload.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<NhaHang>> call, Throwable t) {
                tv_reload.setVisibility(View.VISIBLE);
            }
        });

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
            searchResultData.clear();
            try{
                JSONArray jsonArray = new JSONArray(result);

                for(int i = 0; i < jsonArray.length(); i++){
                    ObjectMapper objectMapper = new ObjectMapper();
                    NhaHang m = objectMapper.readValue(jsonArray.getJSONObject(i).toString(), NhaHang.class);
                    searchResultData.add(m);
                }
            }
            catch (Exception e){
                Log.d("Error parse Json: ", e.getMessage());
            }
            Log.d("Data length: ", String.valueOf(searchResultData.size()));
            adapter = new NhaHangRecyclerAdapter(searchResultData, (MainActivity)MyApplication.getCurrentContext());
            recyclerView.setAdapter(adapter);
            setRetainInstance(false);
        }
    }
}
