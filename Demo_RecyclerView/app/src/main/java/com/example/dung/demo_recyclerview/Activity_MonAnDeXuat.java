package com.example.dung.demo_recyclerview;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.model.Input_Information;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_MonAnDeXuat extends AppCompatActivity {
    RecyclerView recyclerView;
    MonAnRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<MonAn> data;
    TextView actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an_de_xuat);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);
        actionBarTitle.setText("Món ăn đề xuất");

        //Connect to views:
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_monan_child);
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(MyApplication.getCurrentContext());
        recyclerView.setLayoutManager(layoutManager);
        //
        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);

        Input_Information input_information = (Input_Information)getIntent().getSerializableExtra("Input Information");

        initialData(input_information);
    }

    private void initialData(Input_Information _inforObject){
        APIService apiService = ApiUtils.getAPIService();
        apiService.getMonAnDeXuat(_inforObject.getGioiTinh(), _inforObject.getCanNang(), _inforObject.getNamSinh(), _inforObject.getCheDo(),
                _inforObject.getNhuCau(), _inforObject.getCheDoLaoDong(), _inforObject.getBuaAn()).enqueue(new Callback<List<MonAn>>() {
            @Override
            public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                try{
                    data = response.body();

                    customRecyclerAdapter = new MonAnRecyclerAdapter(data);
                    recyclerView.setAdapter(customRecyclerAdapter);

                }
                catch (Exception e){
                    Log.d("Err ma Loai", e.getMessage());
                    MyAlertDialog.showMyAlertDialog("Thông báo", "Lỗi gán kết quả món ăn đề xuất!");
                }
            }

            @Override
            public void onFailure(Call<List<MonAn>> call, Throwable t) {
                Log.d("Err maLoai onFailure", t.getMessage());
                Toast.makeText(MyApplication.getCurrentContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                MyAlertDialog.showMyAlertDialog("Thông báo", "Không tải được danh sách món ăn, hãy thử lại!");

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
    }
}
