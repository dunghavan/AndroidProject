package com.example.dung.demo_recyclerview.viewpager_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dung.demo_recyclerview.fragment.ChildFragment_MonAnDatNhieu;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_TatCaMonAn;
import com.example.dung.demo_recyclerview.fragment.Fragment_MonAn_2_Tabs;

/**
 * Created by Dung on 10/6/2017.
 */

public class ViewPagerAdapter_MonAn_6_Tabs extends FragmentStatePagerAdapter {
    public ViewPagerAdapter_MonAn_6_Tabs(FragmentManager fm){
        super(fm);
    }

    public static String CATEGORY_NAME = "Name";
    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = "Cơm trưa";
                break;
            case 1: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = "Bún phở";
                break;
            case 2: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = "Đồ uống";
                break;
            case 3: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = "Tráng miệng";
                break;
            case 4: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = "Vỉa hè";
                break;
        }
        return fragment;
    }

    @Override
    public int getCount(){
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String title = "";
        switch (position){
            case 0: title = "A";
                break;
            case 1: title = "B";
                break;
            case 2: title = "C";
                break;
            case 3: title = "D";
                break;
            case 4: title = "E";
                break;
        }
        return title;
    }
}
