package com.example.dung.demo_recyclerview.viewpager_adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dung.demo_recyclerview.MyConstant;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_MonAn_1_NhaHang;
import com.example.dung.demo_recyclerview.fragment.ChildFragment_TatCaMonAn;

/**
 * Created by Dung on 1/7/2018.
 */

public class VP_Adapter_MonAn_2Tabs_For_NhaHang extends FragmentPagerAdapter {
    Context context;
    private String maNhaHang;
    public static String TAB_NAME = MyConstant.DATNHIEU;
    public VP_Adapter_MonAn_2Tabs_For_NhaHang(String _maNhaHang, FragmentManager fm){
        super(fm);
        this.maNhaHang = _maNhaHang;
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("MA_NHA_HANG", maNhaHang);
        switch (position){
            case 0: bundle.putString(MyConstant.KEY_FOR_CATEGORY_TAB, MyConstant.DATNHIEU);
                fragment = new ChildFragment_MonAn_1_NhaHang();
                fragment.setArguments(bundle);
                break;
            case 1: bundle.putString(MyConstant.KEY_FOR_CATEGORY_TAB, MyConstant.TATCA);
                fragment = new ChildFragment_MonAn_1_NhaHang();
                fragment.setArguments(bundle);
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
            case 0: TAB_NAME = MyConstant.DATNHIEU;
                break;
            case 1: TAB_NAME = MyConstant.TATCA;
                break;
        }
        return TAB_NAME;
    }
}
