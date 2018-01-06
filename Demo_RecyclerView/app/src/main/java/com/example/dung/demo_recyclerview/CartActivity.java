package com.example.dung.demo_recyclerview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.recycler_adapter.RecyclerAdapter_For_CartActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.text.DecimalFormat;

/**
 * Created by Dung on 11/22/2017.
 */

public class CartActivity extends AppCompatActivity implements RecyclerAdapter_For_CartActivity.OnUpdateListener,
        LoginActivity.OnUpdateListener{

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerAdapter_For_CartActivity adapter;
    TextView tv_tenKhachHang;
    TextView tv_soLuongMon;
    TextView tv_tongTien;

    Button back_btn, next_btn, login_btn;
    CartActivity cartActivity; // Used to start LoginActivity
    LoginActivity loginActivity; // Used to setOnUpdateListener in LoginActivity
    public static boolean isCheckAuthen;
    TextView actionBarTitle;

    final DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);
        actionBarTitle.setText("Giỏ hàng của bạn");
        //getSupportActionBar().setTitle("Giỏ hàng của bạn");
        setContentView(R.layout.activity_cart);
        cartActivity = this; // Used to start LoginActivity
        loginActivity = new LoginActivity();   // Used to setOnUpdateListener in LoginActivity
        loginActivity.setOnUpdateListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_in_cart_activity);
        recyclerView.setHasFixedSize(true);

        tv_tenKhachHang = (TextView)findViewById(R.id.textview_username_in_cart_activity);
        tv_soLuongMon = (TextView)findViewById(R.id.textview_soluongmon_in_cart_activity);
        tv_tongTien = (TextView)findViewById(R.id.textview_tongtien_in_cart_activity);

        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //
        adapter = new RecyclerAdapter_For_CartActivity(Cart.getCartContent());
        recyclerView.setAdapter(adapter);
        adapter.setOnUpdateListner(this);

        back_btn = (Button)findViewById(R.id.back_btn_in_cart);
        next_btn = (Button)findViewById(R.id.next_btn_in_cart);
        login_btn = (Button)findViewById(R.id.login_btn_in_cart);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cartActivity, MapActivity.class);
                cartActivity.startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cartActivity, LoginActivity.class);
                cartActivity.startActivity(intent);
            }
        });

        //onUpdateUI();

    }

    @Override
    public void onUpdateCartUI(){
        tv_soLuongMon.setText("Số lượng món: " + String.valueOf(Cart.getAllItemCount()));
        tv_tongTien.setText("Tổng tiền: " + String.valueOf(decimalFormat.format(Cart.getTotal())) + " đ");

        if(!isCheckAuthen)
        {
            isCheckAuthen = true;
            if(!LoginActivity.isAuthenticated()){
                login_btn.setVisibility(View.VISIBLE);
                next_btn.setVisibility(View.GONE);
                tv_tenKhachHang.setText("Khách hàng: [Bạn chưa đăng nhập]");
            }
            else { // Logged in
                login_btn.setVisibility(View.GONE);
                tv_tenKhachHang.setText("Khách hàng: " + LoginActivity.NAME);

                if(Cart.getCartContent().size() != 0)
                    next_btn.setVisibility(View.VISIBLE);
                else
                    next_btn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
        isCheckAuthen = false;
        onUpdateCartUI();
    }

}
