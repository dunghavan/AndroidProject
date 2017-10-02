package com.example.dung.demo_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 9/17/2017.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter <CustomRecyclerAdapter.RecyclerViewHolder>{
    private List<MonAn> listData = new ArrayList<>();
    public CustomRecyclerAdapter(List<MonAn> _listData){
        this.listData = _listData;
    }

    @Override
    public int getItemCount(){
        return listData.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item, viewGroup, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        viewHolder.textView_TenMonAn.setText(listData.get(position).getTenMonAn());
        viewHolder.textView_Gia.setText(String.format("%s", listData.get(position).getGia()));
        //viewHolder.imageView.setIma(listData.get(position).getTenMonAn());
        String url = listData.get(position).getImgUrl();
        Picasso.with(MainActivity.getMainActivityContext())
                .load(url)
                .into(viewHolder.imageView);
    }

    public void removeItem(int position){
        listData.remove(position);
        notifyItemRemoved(position);
    }

//    public void addItem(int position, String data){
//        listData.add(position, data);
//        notifyItemInserted(position);
//    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        public ImageView imageView;
        public TextView textView_TenMonAn;
        public TextView textView_Gia;
        public ImageButton btnDelete;

        public RecyclerViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            textView_TenMonAn = (TextView)itemView.findViewById(R.id.tv_name);
            textView_Gia = (TextView)itemView.findViewById(R.id.tv_price);
            btnDelete = (ImageButton)itemView.findViewById(R.id.btn_delete);

            //set listener for btnDelete:
            btnDelete.setOnClickListener(this);
        }
        // remove item when click btnDelete:
        @Override
        public void onClick(View v){
            removeItem(getAdapterPosition());
        }

    }
}
