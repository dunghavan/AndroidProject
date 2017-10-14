package com.example.dung.demo_recyclerview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.viewpager_adapter.ViewPagerAdapter_NhaHang;

/**
 * Created by Dung on 8/24/2017.
 */

public class Fragment_NhaHang extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    public Fragment_NhaHang(){    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_nhahang, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout_nhahang);
        viewPager = (ViewPager)view.findViewById(R.id.viewPager_nhahang);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        ViewPagerAdapter_NhaHang viewPagerAdapter_nhaHang = new ViewPagerAdapter_NhaHang(fm);

        viewPager.setAdapter(viewPagerAdapter_nhaHang);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}
