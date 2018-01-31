package com.example.dung.demo_recyclerview;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    TextView tv_reload;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MonAnRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<MonAn> data;
    TextView actionBarTitle;
    TextView tv_advice_1, tv_advice_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an_de_xuat);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);
        actionBarTitle.setText("Món ăn đề xuất");
        actionBar.setDisplayHomeAsUpEnabled(true);

        tv_advice_1 = (TextView)findViewById(R.id.textView_advice_1);
        tv_advice_2 = (TextView)findViewById(R.id.textView_advice_2);
        tv_reload = (TextView)findViewById(R.id.textView_reload_behind_recyclerview_monan_dexuat);
        tv_reload.setVisibility(View.GONE);
        progressBar = (ProgressBar)findViewById(R.id.progressbar_in_recyclerview_monan_dexuat);
        progressBar.setVisibility(View.VISIBLE);

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

        final Input_Information input_information = (Input_Information)getIntent().getSerializableExtra("Input Information");

        initialData(input_information);
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialData(input_information);
            }
        });

        //Textview advice 1:
        switch (input_information.getCheDo()){
            case 2: //Benh tim mach:
                tv_advice_1.setText(R.string.loi_khuyen_benh_tim_mach);
                break;
            case 3: //Benh beo phi:
                tv_advice_1.setText(R.string.loi_khuyen_benh_beo_phi);
                break;
            case 4: //Benh da day:
                tv_advice_1.setText(R.string.loi_khuyen_benh_da_day);
                break;
            case 5: //Benh gout:
                tv_advice_1.setText(R.string.loi_khuyen_benh_gout);
                break;
            case 6: //Benh tieu duong:
                tv_advice_1.setText(R.string.loi_khuyen_benh_tieu_duong);
                break;
            default: tv_advice_1.setText("");
                break;
        }

        //Textview advice 2:
        switch (input_information.getNhuCau()){
            case 3: //Mang thai:
                tv_advice_1.setText(R.string.loi_khuyen_phu_nu_mang_thai);
                break;
            case 4: //Cho con bu:
                tv_advice_1.setText(R.string.loi_khuyen_phu_nu_cho_con_bu);
                break;
        }
    }

    private void initialData(Input_Information _inforObject){
        progressBar.setVisibility(View.VISIBLE);
        APIService apiService = ApiUtils.getAPIService();
        apiService.getMonAnDeXuat(_inforObject.getGioiTinh(), _inforObject.getCanNang(), _inforObject.getNamSinh(), _inforObject.getCheDo(),
                _inforObject.getNhuCau(), _inforObject.getCheDoLaoDong(), _inforObject.getBuaAn(), _inforObject.isAnChay()).enqueue(new Callback<List<MonAn>>() {
            @Override
            public void onResponse(Call<List<MonAn>> call, Response<List<MonAn>> response) {
                try{
                    data = response.body();

                    customRecyclerAdapter = new MonAnRecyclerAdapter(data);
                    recyclerView.setAdapter(customRecyclerAdapter);

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
                //Log.d("Err maLoai onFailure", t.getMessage());
                //Toast.makeText(MyApplication.getCurrentContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                tv_reload.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
    }

    // Menu hien thi gio hang:
    static TextView textCartItemCount;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        final MenuItem cartItem = menu.findItem(R.id.cart);

        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.counter);

        // /Open CartActivity when click
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cartItem);
            }
        });
        setupBadge(Cart.getAllItemCount());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void setupBadge(int mCartItemCount){
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
