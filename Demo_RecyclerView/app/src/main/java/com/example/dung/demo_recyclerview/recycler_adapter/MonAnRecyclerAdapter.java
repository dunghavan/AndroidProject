package com.example.dung.demo_recyclerview.recycler_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 9/17/2017.
 */

public class MonAnRecyclerAdapter extends RecyclerView.Adapter <MonAnRecyclerAdapter.RecyclerViewHolder_MonAn>{
    private List<MonAn> listData = new ArrayList<>();
    public MonAnRecyclerAdapter(List<MonAn> _listData){
        this.listData = _listData;
    }

    @Override
    public int getItemCount(){
        return listData.size();
    }

    @Override
    public RecyclerViewHolder_MonAn onCreateViewHolder(ViewGroup viewGroup, int position){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item____, viewGroup, false);

        return new RecyclerViewHolder_MonAn(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder_MonAn viewHolder, int position) {
        viewHolder.textView_TenMonAn.setText(listData.get(position).getTenMonAn());
        viewHolder.textView_Gia.setText(String.format("%s", listData.get(position).getGia()));

        String url = listData.get(position).getImgUrl();
        Picasso.with(MainActivity.getMainActivityContext())
                .load(url)
                .into(viewHolder.imageView);
    }

    public void removeItem(int position){
        listData.remove(position);
        notifyItemRemoved(position);
    }


    public class RecyclerViewHolder_MonAn extends RecyclerView.ViewHolder implements OnClickListener{
        public ImageView imageView;
        public TextView textView_TenMonAn;
        public TextView textView_Gia;
        public ImageButton btnDelete;

        public RecyclerViewHolder_MonAn(View itemView){
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView_MonAn);
            textView_TenMonAn = (TextView)itemView.findViewById(R.id.tv_TenMonAn);
            textView_Gia = (TextView)itemView.findViewById(R.id.tv_GiaMonAn);
            //btnDelete = (ImageButton)itemView.findViewById(R.id.btn_delete);

            //set listener for btnDelete:
            //btnDelete.setOnClickListener(this);
        }
        // remove item when click btnDelete:
        @Override
        public void onClick(View v){
            removeItem(getAdapterPosition());
        }

    }
}
