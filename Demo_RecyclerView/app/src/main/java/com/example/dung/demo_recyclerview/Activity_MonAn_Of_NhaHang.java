package com.example.dung.demo_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.viewpager_adapter.ViewPagerAdapter_MonAn_2_Tabs;

public class Activity_MonAn_Of_NhaHang extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private static Context context;
    public Activity_MonAn_Of_NhaHang(){
    }

    public static Context getContext(){
        return context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monan_of_nhahang);
        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("TEN_NHA_HANG"));

        tabLayout = (TabLayout)findViewById(R.id.tabLayout_monan_of_nhahang);
        viewPager = (ViewPager)findViewById(R.id.view_pager_monan_of_nhahang);

        FragmentManager fm = getSupportFragmentManager();
        ViewPagerAdapter_MonAn_2_Tabs viewPagerAdapter_monAn_of_nhaHang = new ViewPagerAdapter_MonAn_2_Tabs(fm);

        viewPager.setAdapter(viewPagerAdapter_monAn_of_nhaHang);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
    }
}
