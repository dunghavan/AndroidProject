package com.example.dung.demo_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.recycler_adapter.RecyclerAdapter_For_CartActivity;

import java.text.DecimalFormat;

/**
 * Created by Dung on 11/22/2017.
 */

public class CartActivity extends AppCompatActivity implements RecyclerAdapter_For_CartActivity.OnUpdateListener {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerAdapter_For_CartActivity adapter;
    TextView tv_tenKhachHang;
    TextView tv_soLuongMon;
    TextView tv_tongTien;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Giỏ hàng của bạn");
        setContentView(R.layout.activity_cart);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_in_cart_activity);
        recyclerView.setHasFixedSize(true);

        tv_tenKhachHang = (TextView)findViewById(R.id.textview_username_in_cart_activity);
        tv_soLuongMon = (TextView)findViewById(R.id.textview_soluongmon_in_cart_activity);
        tv_tongTien = (TextView)findViewById(R.id.textview_tongtien_in_cart_activity);

        onUpdateUI();

        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //
        adapter = new RecyclerAdapter_For_CartActivity(Cart.getCartContent());
        recyclerView.setAdapter(adapter);
        adapter.setOnUpdateListner(this);

    }

    public void onUpdateUI(){
        tv_tenKhachHang.setText("Khách hàng: " + "dunghavan@gmail.com");
        tv_soLuongMon.setText("Số lượng món: " + String.valueOf(Cart.getAllItemCount()));
        tv_tongTien.setText("Tổng tiền: " + String.valueOf(decimalFormat.format(Cart.getTotal())) + " đ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
        onUpdateUI();
    }
}
