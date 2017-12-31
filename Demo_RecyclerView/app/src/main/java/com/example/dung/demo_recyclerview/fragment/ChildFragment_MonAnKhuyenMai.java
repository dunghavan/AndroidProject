package com.example.dung.demo_recyclerview.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.MyHttpURLConnection;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
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
 * Created by Dung on 11/10/2017.
 */

public class ChildFragment_MonAnKhuyenMai extends Fragment {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MonAnRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<MonAn> listMonAnKhuyenMai;

    Context context;
    public ChildFragment_MonAnKhuyenMai(){
        context = MyApplication.getCurrentContext();
        listMonAnKhuyenMai = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_recyclerview_monan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_monan_child);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //
        initialData();
        setRetainInstance(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //initialData();
    }

    //Load du lieu theo loai da chon:
    private void initialData(){
        String api = "http://orderfooduit.azurewebsites.net/api/MonAn/Get";
        //new DownloadFilesTask().execute(api);
        APIService apiService = ApiUtils.getAPIService();
        apiService.getByGiamGia().enqueue(new Callback<List<MonAn>>() {
            @Override
            public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                try{
                    listMonAnKhuyenMai = response.body();
                    Log.d("Data length: ", String.valueOf(listMonAnKhuyenMai.size()));
                    adapter = new MonAnRecyclerAdapter(listMonAnKhuyenMai);
                    recyclerView.setAdapter(adapter);
                    setRetainInstance(false);
                }
                catch (Exception e){
                    Log.d("Err monAnKhuyenMai", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<MonAn>> call, Throwable t) {

            }
        });

    }



    private class DownloadFilesTask extends AsyncTask<String, Integer, List<MonAn>> {
        protected List<MonAn> doInBackground(String... urls) {
            try{
                String jsonString = MyHttpURLConnection.sendGet("http://orderfooduit.azurewebsites.net/api/MonAn/Get");
                listMonAnKhuyenMai.clear();
                try{
                    JSONArray jsonArray = new JSONArray(jsonString);

                    for(int i = 0; i < jsonArray.length(); i++){
                        ObjectMapper objectMapper = new ObjectMapper();
                        MonAn m = objectMapper.readValue(jsonArray.getJSONObject(i).toString(), MonAn.class);
                        listMonAnKhuyenMai.add(m);
                    }
                }
                catch (Exception e){
                    Log.d("Error parse Json: ", e.getMessage());
                }
            }
            catch (Exception e){
                Log.d("Error while read api: ", e.getMessage());
            }
            return listMonAnKhuyenMai;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(List<MonAn> result) {
            //Log.d("Data length: ", String.valueOf(result.size()));
            layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new MonAnRecyclerAdapter(result);
            recyclerView.setAdapter(adapter);
            setRetainInstance(false);
        }
    }
}
