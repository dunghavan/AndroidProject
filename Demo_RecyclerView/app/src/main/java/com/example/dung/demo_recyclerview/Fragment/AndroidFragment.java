package com.example.dung.demo_recyclerview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung.demo_recyclerview.R;


/**
 * Created by Dung on 8/24/2017.
 */

public class AndroidFragment extends Fragment {
    public AndroidFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_monan, container, false);
    }
}
