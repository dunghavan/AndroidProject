package com.example.dung.demo_recyclerview.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Dung on 10/6/2017.
 */

public class ViewPagerAdapter_MonAn extends FragmentStatePagerAdapter {
    public ViewPagerAdapter_MonAn(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new ChildFragment_MonAnDatNhieu();
                break;
            case 1: fragment = new ChildFragment_TatCaMonAn();
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