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
import android.widget.TextView;

import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.viewpager_adapter.ViewPagerAdapter_MonAn_6_Tabs;

/**
 * Created by Dung on 10/6/2017.
 */

public class Fragment_MonAn_6_Tabs extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView textView_categoryName;

    public Fragment_MonAn_6_Tabs(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_monan_6tabs, container, false);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView_categoryName = (TextView)view.findViewById(R.id.textview_category);
        textView_categoryName.setText(R.string.com);

        viewPager = (ViewPager)view.findViewById(R.id.view_pager_monan_6tabs);
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout_monan_6tabs);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        ViewPagerAdapter_MonAn_6_Tabs viewPagerAdapter_monAn_6Tabs = new ViewPagerAdapter_MonAn_6_Tabs(fm);

        viewPager.setAdapter(viewPagerAdapter_monAn_6Tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Set icon for tabs:
        tabLayout.getTabAt(0).setIcon(R.drawable.com_6tabs);
        tabLayout.getTabAt(1).setIcon(R.drawable.bun_pho_6tabs);
        tabLayout.getTabAt(2).setIcon(R.drawable.do_uong_6tabs);
        tabLayout.getTabAt(3).setIcon(R.drawable.trang_mieng_6tabs);
        tabLayout.getTabAt(4).setIcon(R.drawable.via_he_6tabs);


        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            // Display title of category when tab selected
            public void onPageSelected(int position) {
                textView_categoryName.setText(ViewPagerAdapter_MonAn_6_Tabs.CATEGORY_NAME);
            }
        });

    }

}
