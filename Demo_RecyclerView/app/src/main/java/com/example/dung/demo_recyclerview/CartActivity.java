package com.example.dung.demo_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;

/**
 * Created by Dung on 11/22/2017.
 */

public class CartActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    MonAnRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Gio hang cua ban");
        setContentView(R.layout.activity_cart);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_in_cart_activity);
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //
        adapter = new MonAnRecyclerAdapter(Cart.getCartContent());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
    }
}
