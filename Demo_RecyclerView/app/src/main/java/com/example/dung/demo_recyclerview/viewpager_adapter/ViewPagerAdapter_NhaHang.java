package com.example.dung.demo_recyclerview.viewpager_adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dung.demo_recyclerview.fragment.ChildFragment_NhaHangDatNhieu;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_TatCaNhaHang;

/**
 * Created by Dung on 10/7/2017.
 */

public class ViewPagerAdapter_NhaHang extends FragmentStatePagerAdapter{
    Context context;
    public ViewPagerAdapter_NhaHang(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new ChildFragment_NhaHangDatNhieu();
                break;
            case 1: fragment = new ChildFragment_TatCaNhaHang();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount(){
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String title = "";
        switch (position){
            case 0: title = "Đặt nhiều";
                break;
            case 1: title = "Tất cả";
                break;
        }
        return title;
    }
}
