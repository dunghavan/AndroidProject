package com.example.dung.demo_recyclerview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.viewpager_adapter.ViewPagerAdapter_MonAn_2_Tabs;
import com.example.dung.demo_recyclerview.viewpager_adapter.ViewPagerAdapter_NangCao_2_Tabs;

/**
 * Created by Dung on 11/10/2017.
 */

public class Fragment_NangCao_2_Tabs extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter_NangCao_2_Tabs viewPagerAdapter_nangCao_2_tabs;

    public Fragment_NangCao_2_Tabs(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nangcao_2tabs, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager_nangcao_2tabs);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_nangcao_2tabs);

        //FragmentManager fm = getActivity().getSupportFragmentManager();
        viewPagerAdapter_nangCao_2_tabs = new ViewPagerAdapter_NangCao_2_Tabs(getChildFragmentManager());

        viewPager.setAdapter(viewPagerAdapter_nangCao_2_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
