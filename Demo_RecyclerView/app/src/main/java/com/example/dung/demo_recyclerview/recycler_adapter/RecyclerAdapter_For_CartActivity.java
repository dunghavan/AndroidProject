package com.example.dung.demo_recyclerview.recycler_adapter;

/**
 * Created by Dung on 11/24/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.example.dung.demo_recyclerview.Cart;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 9/17/2017.
 */

public class RecyclerAdapter_For_CartActivity extends RecyclerView.Adapter <RecyclerAdapter_For_CartActivity.ViewHolder_MonAn>{
    private List<MonAn> listData = new ArrayList<>();
    Context context;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");

    public interface OnUpdateListener {
        void onUpdateCartUI();
    }
    private OnUpdateListener listener;
    public void setOnUpdateListner(OnUpdateListener listener) {
        this.listener = listener;
    }

    public RecyclerAdapter_For_CartActivity(List<MonAn> _listData){
        this.listData = _listData;
    }

    @Override
    public int getItemCount(){
        if(listData == null)
            return 0;
        return listData.size();
    }

    @Override
    public ViewHolder_MonAn onCreateViewHolder(ViewGroup viewGroup, int position){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_monan_in_cart_activity, viewGroup, false);

        return new ViewHolder_MonAn(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder_MonAn viewHolder, int position) {
        MonAn monAnSelected = listData.get(position);
        viewHolder.textView_TenMonAn.setText(monAnSelected.getTenMonAn());
        viewHolder.tv_tenNhaHang.setText(monAnSelected.getTenNhaHang());

        String itemIdSelected = monAnSelected.getId();
        String count = String.valueOf(Cart.getItemCountById(itemIdSelected));
        viewHolder.textView_SoLuongDat.setText(count);
        // Tinh tong tien:
        Double giaKhuyenMai = monAnSelected.getGiaKhuyenMai();
        String giaKhuyenMai_String = String.format("%s", decimalFormat.format(giaKhuyenMai));
        Double tongTien = giaKhuyenMai * Cart.getItemCountById(itemIdSelected);
        String tongTien_String = String.format("%s", decimalFormat.format(tongTien));
        viewHolder.textView_Gia.setText(giaKhuyenMai_String + "đ x " + count + " = " + tongTien_String + "đ");

        String url = listData.get(position).getHinhAnh();
        Picasso.with(MainActivity.getMainActivityContext())
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.failed_load_food)
                .fit()
                .into(viewHolder.imageView);
    }

//    public void removeItem(String itemId){
//        Cart.removeFromCart(itemId);
//    }
//
//    public void addItem(MonAn item){
//        Cart.addToCart(item);
//    }


    public class ViewHolder_MonAn extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView_TenMonAn;
        public TextView tv_tenNhaHang;
        public TextView textView_Gia;

        public TextView textView_SoLuongDat;
        public ImageButton btn_Plus;
        public ImageButton btn_Minus;

        public ViewHolder_MonAn(View itemView){
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView_MonAn_in_cart);
            textView_TenMonAn = (TextView)itemView.findViewById(R.id.tv_TenMonAn_in_cart);
            textView_Gia = (TextView)itemView.findViewById(R.id.tv_GiaMonAn_in_cart);
            btn_Plus = (ImageButton)itemView.findViewById(R.id.btn_plus_in_cart);
            btn_Minus = (ImageButton)itemView.findViewById(R.id.btn_minus_in_cart);
            textView_SoLuongDat = (TextView)itemView.findViewById(R.id.textview_soluongdat_in_cart);
            tv_tenNhaHang = (TextView)itemView.findViewById(R.id.tv_tenNhaHang_in_cart);

            // Click vao Mon An:
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Toast.makeText(context, "ID of MonAn: " + listData.get(getAdapterPosition()).getId().toString(), Toast.LENGTH_LONG).show();
//                    //Tao giao dien alertDialog:
//                    LayoutInflater li = LayoutInflater.from(MyApplication.getCurrentContext());
//                    View alertDialogView = li.inflate(R.layout.alert_dialog_monan, null);
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyApplication.getCurrentContext());
//                    alertDialog.setView(alertDialogView);
//
//                    // Lay MonAn hien tai:
//                    final MonAn monAn = listData.get(getAdapterPosition());
//                    TextView textViewTenMonAn = (TextView)alertDialogView.findViewById(R.id.textview_tenmonan_in_dialog);
//                    textViewTenMonAn.setText(monAn.getTenMonAn().toString());
//
//                    // So luong dat:
//                    final TextView textView_SoLuongDat = (TextView)alertDialogView.findViewById(R.id.textview_soluongdat_in_dialog);
//                    String count = String.valueOf(Cart.getItemCountById(monAn.getId()));
//                    textView_SoLuongDat.setText(count);
//
//                    // Gia, khuyen mai, gia khuyen mai:
//                    TextView textViewGia = (TextView)alertDialogView.findViewById(R.id.textview_gia_in_dialog);
//                    TextView textViewGiaKhuyenMai = (TextView)alertDialogView.findViewById(R.id.textview_giakhuyenmai_in_dialog);
//                    TextView textViewKhuyenMai = (TextView)alertDialogView.findViewById(R.id.textview_khuyenmai_in_dialog);
//
//                    if(monAn.getKhuyenMai() != 0){
//                        textViewGiaKhuyenMai.setText(String.valueOf(decimalFormat.format(monAn.getGiaKhuyenMai())) + " đ");
//                        textViewKhuyenMai.setText("-" + String.valueOf(monAn.getKhuyenMai()) + "%");
//                        textViewGiaKhuyenMai.setVisibility(View.VISIBLE);
//                        textViewKhuyenMai.setVisibility(View.VISIBLE);
//                        textViewGia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                        textViewGia.setTextColor(Color.parseColor("#FFD19366"));
//                    }
//                    else {
//                        textViewGiaKhuyenMai.setVisibility(View.INVISIBLE);
//                        textViewKhuyenMai.setVisibility(View.INVISIBLE);
//                    }
//                    textViewGia.setText(String.valueOf(decimalFormat.format(monAn.getDonGia())) + " đ");
//
//                    // Tong tien:
//                    final TextView textViewTongTien = (TextView)alertDialogView.findViewById(R.id.textview_tongtien_in_dialog);
//                    Double tongTien = Cart.getItemCountById(monAn.getId()) * monAn.getGiaKhuyenMai();
//                    textViewTongTien.setText(String.valueOf(decimalFormat.format(tongTien)) + " đ");
//
//                    // Hinh anh:
//                    ImageView hinhAnh = (ImageView)alertDialogView.findViewById(R.id.imageview_monan_in_dialog);
//                    String imageUrl = monAn.getHinhAnh();
//                    Picasso.with(MyApplication.getCurrentContext())
//                            .load(imageUrl)
//                            .placeholder(R.drawable.loading)
//                            .error(R.drawable.failed_load_food)
//                            .fit()
//                            .into(hinhAnh);
//
//                    // Mo ta:
//                    TextView moTa = (TextView)alertDialogView.findViewById(R.id.textview_mota_monan);
//                    moTa.setText(monAn.getMoTa());
//
//                    // Nang luong, Protein:
//                    TextView tv_nangLuong = (TextView)alertDialogView.findViewById(R.id.textview_nangluong);
//                    TextView tv_protein = (TextView)alertDialogView.findViewById(R.id.textview_protein);
//                    tv_nangLuong.setText("Năng lượng: " + String.valueOf(monAn.getNangLuong()) + " Kcal");
//                    tv_protein.setText("Protein: " + String.valueOf(monAn.getProtein()) + " g");
//
//                    //Click 2 button tren dialog:
//                    final TextView tv_SoLuongDat_In_Dialog = (TextView)alertDialogView.findViewById(R.id.textview_soluongdat_in_dialog);
//                    ImageButton btn_minus_dialog = (ImageButton)alertDialogView.findViewById(R.id.btn_minus_in_dialog);
//                    btn_minus_dialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if(Cart.getCartContent().size() == 1 && Cart.getCartContent().get(0).getItemCount() == 1){
//                                //Mon an cuoi cung trong Cart && So luong == 1
//                            }
//                            else {
//                                String itemIdSelected = monAn.getId();
//                                Cart.removeFromCart(itemIdSelected);
//
//                                Double tongTien = Cart.getItemCountById(monAn.getId()) * monAn.getGiaKhuyenMai();
//                                textViewTongTien.setText(String.valueOf(decimalFormat.format(tongTien)) + " đ");
//
//                                String count = String.valueOf(Cart.getItemCountById(itemIdSelected));
//                                tv_SoLuongDat_In_Dialog.setText(count);
//                                //textView_SoLuongDat.setText(count);
//                                update_SoLuongDat(count,itemIdSelected);
//                                MainActivity.setupBadge(Cart.getAllItemCount());
//                                Activity_MonAn_Of_NhaHang.setupBadge(Cart.getAllItemCount());
//                            }
//
//                        }
//                    });
//                    ImageButton btn_plus_dialog = (ImageButton)alertDialogView.findViewById(R.id.btn_plus_in_dialog);
//                    btn_plus_dialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            String itemIdSelected = monAn.getId();
//                            Cart.addToCart(monAn);
//
//                            Double tongTien = Cart.getItemCountById(monAn.getId()) * monAn.getGiaKhuyenMai();
//                            textViewTongTien.setText(String.valueOf(decimalFormat.format(tongTien)) + " đ");
//
//                            String count = String.valueOf(Cart.getItemCountById(itemIdSelected));
//                            tv_SoLuongDat_In_Dialog.setText(count);
//                            //textView_SoLuongDat.setText(count);
//                            update_SoLuongDat(count, itemIdSelected);
//                            MainActivity.setupBadge(Cart.getAllItemCount());
//                            Activity_MonAn_Of_NhaHang.setupBadge(Cart.getAllItemCount());
//                        }
//                    });
//
//                    alertDialog.setCancelable(false)
//                            .setPositiveButton("Xong", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //get date here...
//                                    textView_SoLuongDat.setText(String.valueOf(Cart.getItemCountById(monAn.getId())));
//                                    //Toast.makeText(MyApplication.getCurrentContext(), "Đã thêm vào giỏ" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                    //Create a dialog:
//                    AlertDialog dialogToShow = alertDialog.create();
//                    dialogToShow.show();
//                }
//            });
            //Su kien 2 Buttons tren item:
            btn_Plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "click + " + getLayoutPosition(), Toast.LENGTH_LONG).show();
                    MonAn itemSelected = listData.get(getLayoutPosition());
                    Cart.addToCart(itemSelected);
                    String count = String.valueOf(Cart.getItemCountById(itemSelected.getId()));
                    update_SoLuongDat(count, itemSelected.getId());
                    MainActivity.setupBadge(Cart.getAllItemCount());
                    Activity_MonAn_Of_NhaHang.setupBadge(Cart.getAllItemCount());
                    notifyDataSetChanged();
                    //Toast.makeText(context, "Item in Cart: " + Cart.getAllItemCount(), Toast.LENGTH_SHORT).show();

                }
            });
            btn_Minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "click - " + getLayoutPosition(), Toast.LENGTH_LONG).show();
                    String itemIdSelected = listData.get(getLayoutPosition()).getId();
                    Cart.removeFromCart(itemIdSelected);
                    String count = String.valueOf(Cart.getItemCountById(itemIdSelected));
                    update_SoLuongDat(count, itemIdSelected);
                    MainActivity.setupBadge(Cart.getAllItemCount());
                    Activity_MonAn_Of_NhaHang.setupBadge(Cart.getAllItemCount());
                    notifyDataSetChanged();
                    //Toast.makeText(context, "Item in Cart: " + Cart.getAllItemCount(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        public void update_SoLuongDat(String count, String itemIdSelected){
            textView_SoLuongDat.setText(count);
            // Tinh tong tien:
            Double giaKhuyenMai = Cart.getGiaKhuyenMaiById(itemIdSelected);
            String giaKhuyenMai_String = String.format("%s", decimalFormat.format(giaKhuyenMai));
            Double tongTien = giaKhuyenMai * Cart.getItemCountById(itemIdSelected);
            String tongTien_String = String.format("%s", decimalFormat.format(tongTien));
            textView_Gia.setText(giaKhuyenMai_String + "đ x " + count + " = " + tongTien_String + "đ");
            //update UI in CartActivity
            if(listener != null){
                listener.onUpdateCartUI();
            }
        }

        @Override
        public void onClick(View v){

        }
    }
}

