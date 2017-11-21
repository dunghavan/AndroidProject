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

import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.MyHttpURLConnection;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 11/10/2017.
 */

public class ChildFragment_MonAnKhuyenMai extends Fragment {
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
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //
        initialData();
//        adapter = new MonAnRecyclerAdapter(listMonAnKhuyenMai);
//        recyclerView.setAdapter(adapter);
        setRetainInstance(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser){
//            if(adapter == null)
//                adapter = new MonAnRecyclerAdapter(listMonAnKhuyenMai);
//            adapter.notifyDataSetChanged();
//        }
        initialData();
    }

    //Load du lieu theo loai da chon:
    private void initialData(){
        String api = "http://orderfooduit.azurewebsites.net/api/MonAn/Get";
        new DownloadFilesTask().execute(api);

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
