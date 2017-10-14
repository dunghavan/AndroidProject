package com.example.dung.demo_recyclerview.viewpager_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dung.demo_recyclerview.fragment.ChildFragment_DoAn;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_DoUong;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_MonAnDatNhieu;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_TatCaMonAn;

/**
 * Created by Dung on 10/11/2017.
 */

public class ViewPagerAdapter_MonAn_2_Tabs extends FragmentStatePagerAdapter{
    public ViewPagerAdapter_MonAn_2_Tabs(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new ChildFragment_TatCaMonAn();
                break;
            case 1: fragment = new ChildFragment_MonAnDatNhieu();
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
            case 0: title = "Tất cả";
                break;
            case 1: title = "Đặt nhiều";
                break;
        }
        return title;
    }
}