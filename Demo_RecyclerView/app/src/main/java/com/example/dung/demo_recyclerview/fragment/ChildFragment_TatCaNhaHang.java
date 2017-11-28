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
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.recycler_adapter.NhaHangRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 10/7/2017.
 */

public class ChildFragment_TatCaNhaHang extends Fragment {

    RecyclerView recyclerView;
    NhaHangRecyclerAdapter nhaHangRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<NhaHang> listNhaHang;
    Context context;

    public ChildFragment_TatCaNhaHang(){
        context = MainActivity.getMainActivityContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_recyclerview_nhahang, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_nhahang);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        initializeData();
        nhaHangRecyclerAdapter = new NhaHangRecyclerAdapter(listNhaHang, (MainActivity)context);
        recyclerView.setAdapter(nhaHangRecyclerAdapter);
    }

    public void initializeData(){
        listNhaHang = new ArrayList<>();
        listNhaHang.add(new NhaHang("Nhà Hàng Đại Thống", "182 Lê Đại Hành, Phường 15, Quận 11, HCM", "https://www.foodnewsfeed.com/sites/foodnewsfeed.com/files/feature-images/fifty-future.jpg"));
        listNhaHang.add(new NhaHang("Nhà Hàng 5", "123 Nguyễn Tất Thành, Phường 2, Quận 3", "http://www.mistralprinceton.com/wp-content/uploads/2014/07/our-menu-at-mistral-restaurant-in-princeton-nj-1030x772.jpg"));
        listNhaHang.add(new NhaHang("Nhà Hàng 6", "98 Bạch Đằng, Phường 2, Bình Thạnh", "http://www.terrass-hotel.com/sites/default/files/styles/colorbox/public/Restauration/restaurant.png?itok=v-Y67a03&title=Plat4"));
        listNhaHang.add(new NhaHang("Nhà Hàng 7", "100 Nguyễn Hữu Cảnh, Quận 1", "https://cdn.theatlantic.com/assets/media/img/mt/2017/08/RTR3FGEP/lead_960.jpg?1502219510"));
        listNhaHang.add(new NhaHang("Nhà Hàng 8", "456 Mai Chí Thọ, Quận 2", "http://www.eaglestreetpier.com.au/-/media/retail-sites/eagle-street-pier/images/content/restaurants-and-bars/sake_480x400.jpg?h=400&la=en&w=480&hash=702377F15295741FFE52A9974F615E6415A7FBF3"));
        listNhaHang.add(new NhaHang("Nhà Hàng 1", "Linh Trung, Thủ Đức, HCM", "http://cdn-image.travelandleisure.com/sites/default/files/styles/1600x1000/public/201306-w-best-disney-restaurants-victoria-and-alberts.jpg?itok=JkGqZ60R"));
        listNhaHang.add(new NhaHang("Nhà Hàng 2", "64 Điện Biên Phủ, Phường 11, Q. Bình Thạnh", "https://www.omnihotels.com/-/media/images/hotels/bospar/restaurants/bospar-omni-parker-house-parkers-restaurant-1170.jpg"));
        listNhaHang.add(new NhaHang("Nhà Hàng 3", "23 Tô Hiến Thành, Phường 14, Quận 11, HCM", "https://www.adlers-innsbruck.com/fileadmin/stadthotel/restaurant_bar/Restaurant/Restaurant_Gesamt_aDLERS_72dpi.jpg"));

    }
}
