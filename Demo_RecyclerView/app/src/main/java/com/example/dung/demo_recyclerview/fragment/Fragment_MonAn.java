package com.example.dung.demo_recyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 8/24/2017.
 */

public class Fragment_MonAn extends Fragment {

    RecyclerView recyclerView;
    MonAnRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<MonAn> data;

    Context context;
    public Fragment_MonAn(){
        context = MainActivity.getMainActivityContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_monan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Connect to views:
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_MonAn);
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //
        initialData();

        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);

    }

    private void initialData(){
        data = new ArrayList<>();

        data.add(new MonAn("Gỏi Cuốn", 200000.0, "http://giadinh.vcmedia.vn/k:2016/photo-0-1472785146823/nhungmonanngonchogiadinhdip29.jpg"));
        data.add(new MonAn("Gà Luộc", 510000.0, "http://alohal.com/wp-content/uploads/2015/12/1437195423-thit-ga-mia.jpg"));
        data.add(new MonAn("Cơm Gà", 250000.0, "http://www.huynhthinga.com/wp-content/uploads/2014/11/2vv1.jpg"));
        data.add(new MonAn("Gà Lên Mâm", 300000.0, "https://i.ytimg.com/vi/NHlI_FsGtow/maxresdefault.jpg"));
        data.add(new MonAn("Chả Ram", 200000.0, "http://media.vietq.vn/files/ctvhanh/mon-ngon2.jpg"));
        data.add(new MonAn("Bún Bò", 450000.0, "http://anh.24h.com.vn/upload/1-2014/images/2014-03-19/1395195457-mon-an-nhat-sanuki-udon-.jpg"));

    }

}
