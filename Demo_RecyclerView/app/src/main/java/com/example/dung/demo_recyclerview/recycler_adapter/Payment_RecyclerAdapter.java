package com.example.dung.demo_recyclerview.recycler_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.Activity_MonAn_Of_NhaHang;
import com.example.dung.demo_recyclerview.Activity_Payment_Details;
import com.example.dung.demo_recyclerview.Cart;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.model.Payment;
import com.example.dung.demo_recyclerview.viewpager_adapter.PayPal_Response;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 12/22/2017.
 */

public class Payment_RecyclerAdapter extends RecyclerView.Adapter<Payment_RecyclerAdapter.Payment_ViewHolder>{
    private List<Payment> listData = new ArrayList<>();
    Context context;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public Payment_RecyclerAdapter(List<Payment> _listData, Context context){
        this.listData = _listData;
        this.context = context;
    }

    @Override
    public int getItemCount(){
        if(listData == null)
            return 0;
        return listData.size();
    }

    @Override
    public Payment_ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_payment, viewGroup, false);

        return new Payment_ViewHolder(itemView);
    }

    public class Payment_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerViewClickListener listener;
        public TextView tv_maDonHang;
        public TextView tv_tongThanhToan;
        public TextView tv_ngayGiaoDich;

        public Payment_ViewHolder(View itemView) {
            super(itemView);
            tv_maDonHang = (TextView) itemView.findViewById(R.id.tv_maDonHang_in_payment);
            tv_tongThanhToan = (TextView) itemView.findViewById(R.id.tv_tongTien_in_payment);
            tv_ngayGiaoDich = (TextView) itemView.findViewById(R.id.tv_ngayGiaoDich_in_payment);

            // Click vao item:
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View itemView) {
            Payment payment = listData.get(getAdapterPosition());
            MainActivity activity = (MainActivity)context;
            Intent intent = new Intent(activity, Activity_Payment_Details.class);
            intent.putExtra("PaymentObject", payment);
            activity.startActivity(intent);
        }
    }

    public void onBindViewHolder(final Payment_ViewHolder viewHolder, int position) {
        final Payment payment = listData.get(position);
        viewHolder.tv_maDonHang.setText("Mã đơn hàng: " + payment.getId());
        viewHolder.tv_tongThanhToan.setText("Tổng thanh toán: " + String.format("%s", decimalFormat.format(payment.getTongTien())) + "đ");
        viewHolder.tv_ngayGiaoDich.setText("Ngày giờ giao dịch: " + payment.getNgayGioGiao());


    }
}
