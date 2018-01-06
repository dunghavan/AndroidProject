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
import android.widget.TextView;

import com.example.dung.demo_recyclerview.MyAlertDialog;
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
    TextView tv_reload;
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
        tv_reload = (TextView)view.findViewById(R.id.textView_reload_behind_recyclerview_monan);
        tv_reload.setVisibility(View.GONE);
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialData();
            }
        });
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_monan_child);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar_in_recyclerview_monan);
        progressBar.setVisibility(View.VISIBLE);
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
        //String api = "http://orderfooduit.azurewebsites.net/api/MonAn/Get";
        //new DownloadFilesTask().execute(api);
        progressBar.setVisibility(View.VISIBLE);

        APIService apiService = ApiUtils.getAPIService();
        apiService.getByGiamGia().enqueue(new Callback<List<MonAn>>() {
            @Override
            public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                try{
                    listMonAnKhuyenMai = response.body();
                    adapter = new MonAnRecyclerAdapter(listMonAnKhuyenMai);
                    recyclerView.setAdapter(adapter);
                    setRetainInstance(false);
                    progressBar.setVisibility(View.GONE);
                    if(listMonAnKhuyenMai.size() != 0)
                        tv_reload.setVisibility(View.GONE);
                    else
                        tv_reload.setVisibility(View.VISIBLE);
                }
                catch (Exception e){
                    Log.d("Err monAnKhuyenMai", e.getMessage());
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
    static boolean isShowAlertDialog = false;
    public void showLoadFailedDialog(){
        if(!isShowAlertDialog){
            MyAlertDialog.showMyAlertDialog("Thông báo", "Không tải được danh sách món ăn khuyến mại. Hãy thử lại!");
            isShowAlertDialog = true;
            progressBar.setVisibility(View.GONE);
        }

    }
    @Override
    public void onResume() {
        super.onResume();

        isShowAlertDialog = false;
        adapter = new MonAnRecyclerAdapter(listMonAnKhuyenMai);
        recyclerView.setAdapter(adapter);
        setRetainInstance(false);
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
