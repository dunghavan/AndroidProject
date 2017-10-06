package com.example.dung.demo_recyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.recycler_adapter.NhaHangRecyclerAdapter;

import java.util.List;

/**
 * Created by Dung on 8/24/2017.
 */

public class Fragment_NhaHang extends Fragment {
    RecyclerView recyclerView;
    NhaHangRecyclerAdapter nhaHangRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<NhaHang> listNhaHang;
    Context context;

    public Fragment_NhaHang(){
        context = MainActivity.getMainActivityContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_nhahang, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_NhaHang);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        ///listNhaHang =
        nhaHangRecyclerAdapter = new NhaHangRecyclerAdapter(listNhaHang);
        recyclerView.setAdapter(nhaHangRecyclerAdapter);
    }
}
