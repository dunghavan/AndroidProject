//package com.example.dung.demo_recyclerview.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.dung.demo_recyclerview.R;
//import com.example.dung.demo_recyclerview.viewpager_adapter.ViewPagerAdapter_MonAn_2_Tabs;
//
///**
// * Created by Dung on 11/20/2017.
// */
//
//public class NewFragment_MonAn_2Tabs extends Fragment {
//    ViewPager viewPager;
//    TabLayout tabLayout;
//    ViewPagerAdapter_MonAn_2_Tabs viewPagerAdapter_monAn_2Tabs;
//
//    public NewFragment_MonAn_2Tabs() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_monan_2tabs, container, false);
//
//    }
//
//
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//
//        viewPager = (ViewPager) view.findViewById(R.id.view_pager_monan_2tabs);
//        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_monan_2tabs);
//
//        //FragmentManager fm = getActivity().getSupportFragmentManager();
//        viewPagerAdapter_monAn_2Tabs = new ViewPagerAdapter_MonAn_2_Tabs(getChildFragmentManager());
//
//        viewPager.setAdapter(viewPagerAdapter_monAn_2Tabs);
//        tabLayout.setupWithViewPager(viewPager);
//    }
//}
