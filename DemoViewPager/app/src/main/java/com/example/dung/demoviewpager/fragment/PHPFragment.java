package com.example.dung.demoviewpager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung.demoviewpager.R;

/**
 * Created by Dung on 8/24/2017.
 */

public class PHPFragment extends Fragment {
    public PHPFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_php, container, false);
    }
}
