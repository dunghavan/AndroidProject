package com.example.dung.demoviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dung.demoviewpager.fragment.AndroidFragment;
import com.example.dung.demoviewpager.fragment.IOSFragment;
import com.example.dung.demoviewpager.fragment.PHPFragment;

/**
 * Created by Dung on 8/25/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new AndroidFragment();
                break;
            case 1: fragment = new IOSFragment();
                break;
            case 2: fragment = new PHPFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount(){
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String title = "";
        switch (position){
            case 0: title = "Android";
                break;
            case 1: title = "IOS";
                break;
            case 2: title = "PHP";
                break;
        }
        return title;
    }
}
