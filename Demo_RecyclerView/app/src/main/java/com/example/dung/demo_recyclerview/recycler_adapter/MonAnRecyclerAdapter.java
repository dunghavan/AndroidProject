package com.example.dung.demo_recyclerview.recycler_adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.Cart;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Dung on 9/17/2017.
 */

public class MonAnRecyclerAdapter extends RecyclerView.Adapter <MonAnRecyclerAdapter.RecyclerViewHolder_MonAn>{
    private List<MonAn> listData = new ArrayList<>();
    Context context;
    public MonAnRecyclerAdapter(List<MonAn> _listData, Context context){
        this.listData = _listData;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        return listData.size();
    }

    @Override
    public RecyclerViewHolder_MonAn onCreateViewHolder(ViewGroup viewGroup, int position){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_monan, viewGroup, false);

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

    public void removeItem(String itemId){
        Cart.removeFromCart(itemId);
    }

    public void addItem(String itemId){
        Cart.addToCart(itemId);
    }


    public class RecyclerViewHolder_MonAn extends RecyclerView.ViewHolder implements OnClickListener{
        public ImageView imageView;
        public TextView textView_TenMonAn;
        public TextView textView_Gia;

        public TextView textView_SoLuongDat;
        public ImageButton btn_Plus;
        public ImageButton btn_Minus;

        public RecyclerViewHolder_MonAn(View itemView){
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView_MonAn);
            textView_TenMonAn = (TextView)itemView.findViewById(R.id.tv_TenMonAn);
            textView_Gia = (TextView)itemView.findViewById(R.id.tv_GiaMonAn);
            btn_Plus = (ImageButton)itemView.findViewById(R.id.btn_plus);
            btn_Minus = (ImageButton)itemView.findViewById(R.id.btn_minus);
            textView_SoLuongDat = (TextView)itemView.findViewById(R.id.textview_soluongdat);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "ID of MonAn: " + listData.get(getAdapterPosition()).getId().toString(), Toast.LENGTH_LONG).show();
                }
            });
            //set listener for buttons:
            btn_Plus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "click " + getLayoutPosition(), Toast.LENGTH_LONG);
                }
            });
            btn_Minus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        // remove item when click btnDelete:
        @Override
        public void onClick(View v){

        }

    }
}
