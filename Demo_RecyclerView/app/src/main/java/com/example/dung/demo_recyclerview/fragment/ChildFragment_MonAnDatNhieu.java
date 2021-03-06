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

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.recycler_adapter.MonAnRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 10/6/2017.
 */

public class ChildFragment_MonAnDatNhieu extends Fragment {
    RecyclerView recyclerView;
    MonAnRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<MonAn> data;

    Context context;
    public ChildFragment_MonAnDatNhieu(){
        context = MainActivity.getMainActivityContext();
        data = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_recyclerview_monan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Connect to views:
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_monan_child);
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //
        initialData();

        customRecyclerAdapter = new MonAnRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);
        setRetainInstance(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(customRecyclerAdapter == null)
                customRecyclerAdapter = new MonAnRecyclerAdapter(data);
            else
                customRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    private void initialData(){
        //data = new ArrayList<>();

//        data.add(new MonAn("1", "Cơm Gà", 250000.0, "http://www.huynhthinga.com/wp-content/uploads/2014/11/2vv1.jpg", 20, 40, "Mô tả", 10D));
//        data.add(new MonAn("2", "Gà Lên Mâm", 300000.0, "https://i.ytimg.com/vi/NHlI_FsGtow/maxresdefault.jpg", 34, 56, "Mô tả", 10D));
//        data.add(new MonAn("3", "Chả Ram", 200000.0, "http://media.vietq.vn/files/ctvhanh/mon-ngon2.jpg", 32, 401, "Mô tả", 20D));
//        data.add(new MonAn("4", "Gỏi Cuốn", 200000.0, "http://giadinh.vcmedia.vn/k:2016/photo-0-1472785146823/nhungmonanngonchogiadinhdip29.jpg", 20, 40, "Mô tả", 100D));
//        data.add(new MonAn("5", "Gà Luộc", 510000.0, "http://alohal.com/wp-content/uploads/2015/12/1437195423-thit-ga-mia.jpg", 20, 40, "Mô tả", 10D));
//        data.add(new MonAn("6", "Cơm Gà", 250000.0, "http://www.huynhthinga.com/wp-content/uploads/2014/11/2vv1.jpg", 20, 40, "Mô tả", 10D));
//        data.add(new MonAn("7", "Gà Lên Mâm", 300000.0, "https://i.ytimg.com/vi/NHlI_FsGtow/maxresdefault.jpg", 20, 40, "Mô tả", 10D));
//        data.add(new MonAn("8", "Chả Ram", 200000.0, "http://media.vietq.vn/files/ctvhanh/mon-ngon2.jpg", 20, 40, "Mô tả", 10D));
//        data.add(new MonAn("9", "Bún Bò", 450000.0, "http://anh.24h.com.vn/upload/1-2014/images/2014-03-19/1395195457-mon-an-nhat-sanuki-udon-.jpg", 20, 40, "Mô tả", 10D));

    }
}
