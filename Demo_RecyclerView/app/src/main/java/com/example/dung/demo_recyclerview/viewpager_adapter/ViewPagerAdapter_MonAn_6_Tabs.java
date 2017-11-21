package com.example.dung.demo_recyclerview.viewpager_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.dung.demo_recyclerview.MyConstant;
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

    public static String CATEGORY_NAME = MyConstant.COM;
    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        Log.d("Position in adapter: ", String.valueOf(position));
        switch (position){
            case 0: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = MyConstant.COM;
                break;
            case 1: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = MyConstant.BUNPHO;
                break;
            case 2: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = MyConstant.DOUONG;
                break;
            case 3: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = MyConstant.TRANGMIENG;
                break;
            case 4: fragment = new Fragment_MonAn_2_Tabs();
                CATEGORY_NAME = MyConstant.VIAHE;
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
            case 0: title = "";
                break;
            case 1: title = "";
                break;
            case 2: title = "";
                break;
            case 3: title = "";
                break;
            case 4: title = "";
                break;
        }
        return title;
    }
}
