package com.example.dung.demo_recyclerview.recycler_adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.Activity_MonAn_Of_NhaHang;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Dung on 10/6/2017.
 */

public class NhaHangRecyclerAdapter extends RecyclerView.Adapter <NhaHangRecyclerAdapter.RecyclerViewHolder_NhaHang>{
    private List<NhaHang> listData;
    MainActivity activity;
    public NhaHangRecyclerAdapter(List<NhaHang> list, MainActivity activity){
        this.activity = activity;
        listData = list;
    }

    @Override
    public int getItemCount(){
        return listData.size();
    }

    //Tra ve 1 ViewHolder
    @Override
    public RecyclerViewHolder_NhaHang onCreateViewHolder(ViewGroup viewGroup, int position){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_nhahang, viewGroup, false);

        return new RecyclerViewHolder_NhaHang(itemView);
    }

    //Gan du lieu vao ViewHolder:
    @Override
    public void onBindViewHolder(RecyclerViewHolder_NhaHang viewHolder, int position){
        viewHolder.textView_TenNhaHang.setText(listData.get(position).getTenNhaHang());
        viewHolder.textView_DiaChi.setText(listData.get(position).getDiaChi());
        //viewHolder.textView_KhuyenMai.setText(listData.get(position).getKhuyenMai() + "% Off");

        String url = listData.get(position).getUrlHinhAnh();
        if (url != null && !url.isEmpty()){
            Picasso.with(MyApplication.getCurrentContext())
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.failed_load_food)
                    .fit()
                    .into(viewHolder.imageView_HinhAnh);

        }

    }
    //Class View Holder ket noi voi file giao dien:
    class RecyclerViewHolder_NhaHang extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView_HinhAnh;
        public TextView textView_TenNhaHang;
        public TextView textView_DiaChi;
        public TextView textView_KhuyenMai;

        public RecyclerViewHolder_NhaHang(View itemView){
            super(itemView);
            imageView_HinhAnh = (ImageView)itemView.findViewById(R.id.imageView_NhaHang);
            textView_TenNhaHang = (TextView)itemView.findViewById(R.id.tv_tenNhaHang);
            textView_DiaChi = (TextView)itemView.findViewById(R.id.tv_DiaChi);
            textView_KhuyenMai = (TextView)itemView.findViewById(R.id.tv_KhuyenMai);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
             // Start Activity các món ăn của nhà hàng

            Intent intent = new Intent(activity, Activity_MonAn_Of_NhaHang.class);
            intent.putExtra("TEN_NHA_HANG", textView_TenNhaHang.getText());
            activity.startActivity(intent);

        }
    }
}
