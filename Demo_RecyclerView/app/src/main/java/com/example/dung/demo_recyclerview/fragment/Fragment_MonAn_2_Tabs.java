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
import com.example.dung.demo_recyclerview.viewpager_adapter.ViewPagerAdapter_MonAn_2_Tabs;

/**
 * Created by Dung on 10/13/2017.
 */

public class Fragment_MonAn_2_Tabs extends Fragment implements ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter_MonAn_2_Tabs viewPagerAdapter_monAn_2Tabs;

    public Fragment_MonAn_2_Tabs() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monan_2tabs, container, false);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        viewPager = (ViewPager) view.findViewById(R.id.view_pager_monan_2tabs);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_monan_2tabs);

        //FragmentManager fm = getActivity().getSupportFragmentManager();
        viewPagerAdapter_monAn_2Tabs = new ViewPagerAdapter_MonAn_2_Tabs(getChildFragmentManager());

        viewPager.setAdapter(viewPagerAdapter_monAn_2Tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

//                switch (tab.getPosition()){
//                    case 0 : ((ChildFragment_TatCaMonAn)viewPagerAdapter_monAn_2Tabs.getItem(tab.getPosition())).recyclerView.invalidate();
//                    case 1 : ((ChildFragment_MonAnDatNhieu)viewPagerAdapter_monAn_2Tabs.getItem(tab.getPosition())).recyclerView.invalidate();
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public void onPageSelected (int position) {
//        switch (position){
//            case 0 : ((ChildFragment_TatCaMonAn)viewPagerAdapter_monAn_2Tabs.getItem(position)).customRecyclerAdapter.notifyDataSetChanged();
//            case 1 : ((ChildFragment_MonAnDatNhieu)viewPagerAdapter_monAn_2Tabs.getItem(position)).refresh();
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
