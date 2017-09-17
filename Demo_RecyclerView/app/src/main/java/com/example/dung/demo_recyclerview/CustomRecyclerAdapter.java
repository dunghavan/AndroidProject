package com.example.dung.demo_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 9/17/2017.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter <CustomRecyclerAdapter.RecyclerViewHolder>{
    private List<String> listData = new ArrayList<>();
    CustomRecyclerAdapter(List<String> _listData){
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
        viewHolder.textView.setText(listData.get(position));
    }

    public void removeItem(int position){
        listData.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, String data){
        listData.add(position, data);
        notifyItemInserted(position);
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        public TextView textView;
        public ImageButton btnDelete;

        public RecyclerViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.tv_name);
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
