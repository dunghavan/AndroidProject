package com.example.dung.demo_recyclerview.viewpager_adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dung.demo_recyclerview.MyConstant;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_MonAnDatNhieu;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_MonAnKhuyenMai;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_TatCaMonAn;

/**
 * Created by Dung on 11/10/2017.
 */

public class ViewPagerAdapter_NangCao_2_Tabs extends FragmentPagerAdapter{
    Context context;
    public static String TAB_NAME = MyConstant.KHUYENMAI;
    public ViewPagerAdapter_NangCao_2_Tabs(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0: case 1: fragment = new ChildFragment_MonAnKhuyenMai();
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
        switch (position){
            case 0: TAB_NAME = MyConstant.KHUYENMAI;
                break;
            case 1: TAB_NAME = MyConstant.DATNHIEU;
                break;
        }
        return TAB_NAME;
    }
}
