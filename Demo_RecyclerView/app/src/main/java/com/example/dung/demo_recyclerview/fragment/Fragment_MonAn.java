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

/**
 * Created by Dung on 10/6/2017.
 */

public class Fragment_MonAn extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    public Fragment_MonAn(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_monan, container, false);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        viewPager = (ViewPager)view.findViewById(R.id.view_pager_monan);
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout_monan);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        ViewPagerAdapter_MonAn viewPagerAdapter_monAn = new ViewPagerAdapter_MonAn(fm);

        viewPager.setAdapter(viewPagerAdapter_monAn);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

}
