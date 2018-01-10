package com.example.dung.demo_recyclerview.recycler_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.Activity_MonAn_Of_NhaHang;
import com.example.dung.demo_recyclerview.Cart;
import com.example.dung.demo_recyclerview.LoginActivity;
import com.example.dung.demo_recyclerview.MainActivity;
import com.example.dung.demo_recyclerview.MyAlertDialog;
import com.example.dung.demo_recyclerview.MyApplication;
import com.example.dung.demo_recyclerview.MyHttpURLConnection;
import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.paypal.android.sdk.cx.l;

/**
 * Created by Dung on 9/17/2017.
 */

public class MonAnRecyclerAdapter extends RecyclerView.Adapter <MonAnRecyclerAdapter.RecyclerViewHolder_MonAn>{
    private List<MonAn> listData = new ArrayList<>();
    Context context;
    private NhaHang nhaHang_from_json;
    private String tenNhaHang;
    private int khuyenMaiFromServer;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");


    public MonAnRecyclerAdapter(List<MonAn> _listData){
        this.listData = _listData;
    }


    @Override
    public int getItemCount(){
        if(listData == null)
            return 0;
        return listData.size();
    }

    @Override
    public RecyclerViewHolder_MonAn onCreateViewHolder(ViewGroup viewGroup, int position){
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_monan, viewGroup, false);

        return new RecyclerViewHolder_MonAn(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder_MonAn viewHolder, int position) {
        final MonAn monAnSelected = listData.get(position);
        viewHolder.textView_TenMonAn.setText(monAnSelected.getTenMonAn());
        viewHolder.textView_Gia.setText(String.format("%s", decimalFormat.format(monAnSelected.getDonGia())) + "đ");


        viewHolder.textView_GiaKhuyenMai.setVisibility(View.INVISIBLE);
        viewHolder.textView_KhuyenMai.setVisibility(View.INVISIBLE);
        APIService apiService = ApiUtils.getAPIService();
        Log.d("Mon an crash", monAnSelected.getId());
        apiService.getChietKhauByMa(monAnSelected.getId()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == null)
                    khuyenMaiFromServer = 0;
                else
                    khuyenMaiFromServer = response.body();
                monAnSelected.setKhuyenMai((double)khuyenMaiFromServer);
                if(khuyenMaiFromServer != 0){
                    viewHolder.textView_GiaKhuyenMai.setText(String.valueOf(decimalFormat.format(monAnSelected.getGiaKhuyenMai())) + " đ");
                    viewHolder.textView_KhuyenMai.setText("-" + String.valueOf(monAnSelected.getKhuyenMai()) + "%");
                    viewHolder.textView_GiaKhuyenMai.setVisibility(View.VISIBLE);
                    viewHolder.textView_KhuyenMai.setVisibility(View.VISIBLE);
                    viewHolder.textView_Gia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.textView_Gia.setTextColor(Color.parseColor("#FFD19366"));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                khuyenMaiFromServer = 0;
            }
        });

        String itemIdSelected = monAnSelected.getId();
        String count = String.valueOf(Cart.getItemCountById(itemIdSelected));
        viewHolder.textView_SoLuongDat.setText(count);

        String url = monAnSelected.getHinhAnh();
        if (url != null && !url.isEmpty()) {
            Picasso.with(MainActivity.getMainActivityContext())
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.failed_load_food)
                    .fit()
                    .into(viewHolder.imageView);
        }
        viewHolder.tv_tenNhaHang.setText(monAnSelected.getTenNhaHang());

    }


    public class RecyclerViewHolder_MonAn extends RecyclerView.ViewHolder implements OnClickListener{
        public ImageView imageView;
        public TextView textView_TenMonAn;
        public TextView textView_Gia;
        public TextView textView_GiaKhuyenMai;
        public TextView textView_KhuyenMai;
        public TextView tv_tenNhaHang;

        public TextView textView_SoLuongDat;
        public ImageButton btn_Plus;
        public ImageButton btn_Minus;

        public RecyclerViewHolder_MonAn(View itemView){
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView_MonAn);
            textView_TenMonAn = (TextView)itemView.findViewById(R.id.tv_TenMonAn);
            textView_Gia = (TextView)itemView.findViewById(R.id.textview_gia_in_item);
            textView_GiaKhuyenMai = (TextView)itemView.findViewById(R.id.textview_giakhuyenmai_in_item);
            textView_KhuyenMai = (TextView)itemView.findViewById(R.id.textview_khuyenmai_in_item);
            btn_Plus = (ImageButton)itemView.findViewById(R.id.btn_plus);
            btn_Minus = (ImageButton)itemView.findViewById(R.id.btn_minus);
            textView_SoLuongDat = (TextView)itemView.findViewById(R.id.textview_soluongdat);
            tv_tenNhaHang = (TextView)itemView.findViewById(R.id.tv_tenNhaHang);

            // Click vao Mon An:
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lay MonAn hien tai:
                    final MonAn monAn = listData.get(getAdapterPosition());
                    final String maNhaHang = monAn.getMaNhaHang();
                    //Tao giao dien alertDialog:
                    final LayoutInflater li = LayoutInflater.from(MyApplication.getCurrentContext());
                    final View alertDialogView = li.inflate(R.layout.alert_dialog_monan, null);
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyApplication.getCurrentContext());
                    alertDialog.setView(alertDialogView);

                    TextView textViewTenMonAn = (TextView)alertDialogView.findViewById(R.id.textview_tenmonan_in_dialog);
                    textViewTenMonAn.setText(monAn.getTenMonAn().toString());

                    // So luong dat:
                    final TextView textView_SoLuongDat = (TextView)alertDialogView.findViewById(R.id.textview_soluongdat_in_dialog);
                    String count = String.valueOf(Cart.getItemCountById(monAn.getId()));
                    textView_SoLuongDat.setText(count);

                    // Gia, khuyen mai, gia khuyen mai:
                    final TextView textViewGia = (TextView)alertDialogView.findViewById(R.id.textview_gia_in_dialog);
                    final TextView textViewGiaKhuyenMai = (TextView)alertDialogView.findViewById(R.id.textview_giakhuyenmai_in_dialog);
                    final TextView textViewKhuyenMai = (TextView)alertDialogView.findViewById(R.id.textview_khuyenmai_in_dialog);

                    if(monAn.getKhuyenMai() != 0){
                        textViewGiaKhuyenMai.setText(String.valueOf(decimalFormat.format(monAn.getGiaKhuyenMai())) + " đ");
                        textViewKhuyenMai.setText("-" + String.valueOf(monAn.getKhuyenMai()) + "%");
                        textViewGiaKhuyenMai.setVisibility(View.VISIBLE);
                        textViewKhuyenMai.setVisibility(View.VISIBLE);
                        textViewGia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        textViewGia.setTextColor(Color.parseColor("#FFD19366"));
                    }
                    else {
                        textViewGiaKhuyenMai.setVisibility(View.INVISIBLE);
                        textViewKhuyenMai.setVisibility(View.INVISIBLE);
                    }

                    textViewGia.setText(String.valueOf(decimalFormat.format(monAn.getDonGia())) + " đ");

                    // Tong tien:
                    final TextView textViewTongTien = (TextView)alertDialogView.findViewById(R.id.textview_tongtien_in_dialog);
                    Double tongTien = Cart.getItemCountById(monAn.getId()) * monAn.getGiaKhuyenMai();
                    textViewTongTien.setText(String.valueOf(decimalFormat.format(tongTien)) + " đ");

                    //Ten nha hang:
                    TextView tenNH = (TextView)alertDialogView.findViewById(R.id.textview_tennhahang_in_dialog);
                    tenNH.setText(tenNhaHang);
                    // Hinh anh:
                    ImageView hinhAnh = (ImageView)alertDialogView.findViewById(R.id.imageview_monan_in_dialog);
                    String imageUrl = monAn.getHinhAnh();
                    if (imageUrl != null && !imageUrl.isEmpty())
                    {
                        Picasso.with(MyApplication.getCurrentContext())
                                .load(imageUrl)
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.failed_load_food)
                                .fit()
                                .into(hinhAnh);
                    }

                    // Mo ta:
                    TextView moTa = (TextView)alertDialogView.findViewById(R.id.textview_mota_monan);
                    moTa.setText(monAn.getMoTa());

                    // Nang luong, Protein:
                    TextView tv_nangLuong = (TextView)alertDialogView.findViewById(R.id.textview_nangluong);
                    TextView tv_protein = (TextView)alertDialogView.findViewById(R.id.textview_protein);
                    tv_nangLuong.setText("Năng lượng: " + String.valueOf(monAn.getNangLuong()) + " Kcal");
                    tv_protein.setText("Protein: " + String.valueOf(monAn.getkL_Dam()) + " g");

                    //Click 2 button tren dialog:
                    final TextView tv_SoLuongDat_In_Dialog = (TextView)alertDialogView.findViewById(R.id.textview_soluongdat_in_dialog);
                    ImageButton btn_minus_dialog = (ImageButton)alertDialogView.findViewById(R.id.btn_minus_in_dialog);
                    btn_minus_dialog.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String itemIdSelected = monAn.getId();
                            Cart.removeFromCart(itemIdSelected);

                            Double tongTien = Cart.getItemCountById(monAn.getId()) * monAn.getGiaKhuyenMai();
                            textViewTongTien.setText(String.valueOf(decimalFormat.format(tongTien)) + " đ");

                            String count = String.valueOf(Cart.getItemCountById(itemIdSelected));
                            tv_SoLuongDat_In_Dialog.setText(count);
                            //textView_SoLuongDat.setText(count);
                            update_SoLuongDat(count);
                            MainActivity.setupBadge(Cart.getAllItemCount());
                            Activity_MonAn_Of_NhaHang.setupBadge(Cart.getAllItemCount());

                        }
                    });
                    ImageButton btn_plus_dialog = (ImageButton)alertDialogView.findViewById(R.id.btn_plus_in_dialog);
                    btn_plus_dialog.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Check
                            if(Cart.getCartContent().size() != 0 && maNhaHangNotExist(maNhaHang))
                                Toast.makeText(MyApplication.getCurrentContext(), "Bạn phải chọn các món ăn trong cùng một nhà hàng!", Toast.LENGTH_SHORT).show();
                            else {
                                String itemIdSelected = monAn.getId();
                                Cart.addToCart(monAn);

                                Double tongTien = Cart.getItemCountById(monAn.getId()) * monAn.getGiaKhuyenMai();
                                textViewTongTien.setText(String.valueOf(decimalFormat.format(tongTien)) + " đ");

                                String count = String.valueOf(Cart.getItemCountById(itemIdSelected));
                                tv_SoLuongDat_In_Dialog.setText(count);
                                //textView_SoLuongDat.setText(count);
                                update_SoLuongDat(count);
                                MainActivity.setupBadge(Cart.getAllItemCount());
                                Activity_MonAn_Of_NhaHang.setupBadge(Cart.getAllItemCount());
                            }

                        }
                    });

                    // TextView Danh gia:
                    TextView tv_danhGia = (TextView)alertDialogView.findViewById(R.id.textview_danhgia_in_dialog);
                    tv_danhGia.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // If user not logged in:
//                            if(!LoginActivity.isAuthenticated()){
//                                Intent intent = new Intent(MyApplication.getCurrentContext(), LoginActivity.class);
//                                MyApplication.getCurrentContext().startActivity(intent);
//                            }

                            View alertDialogView_rating = li.inflate(R.layout.alert_dialog_rating_monan, null);
                            AlertDialog.Builder alertDialog_rating = new AlertDialog.Builder(MyApplication.getCurrentContext());
                            alertDialog_rating.setView(alertDialogView_rating);

                            RatingBar ratingBar_all_User = (RatingBar)alertDialogView_rating.findViewById(R.id.ratingBar_of_allUser);
                            final RatingBar ratingBar_currentUser = (RatingBar)alertDialogView_rating.findViewById(R.id.ratingBar_of_currentUser);
                            ratingBar_all_User.setRating(monAn.getSoDiem());
                            ratingBar_currentUser.setRating(monAn.getSoDiem());

                            alertDialog_rating.setCancelable(false)
                                    .setTitle("Đánh giá món ăn")
                                    .setNegativeButton("Hủy", null)
                                    .setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // Call API Rating
                                            APIService apiService = ApiUtils.getAPIService();
                                            apiService.danhgiaMonAn(ratingBar_currentUser.getRating(), monAn.getId()).enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    try {
                                                        Log.d("rating onResponse", "Rating success!");
                                                    }
                                                    catch (Exception e){
                                                        Log.d("rating onResponse", e.getMessage());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Log.d("rating onFailure", t.getMessage());
                                                }
                                            });
                                            //Toast.makeText(MyApplication.getCurrentContext(), "Rating Mon An", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog dialog_toShow_rating = alertDialog_rating.create();
                            dialog_toShow_rating.show();

                        }
                    });

                    alertDialog.setCancelable(true)
                            .setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //get date here...
                                    textView_SoLuongDat.setText(String.valueOf(Cart.getItemCountById(monAn.getId())));
                                }
                            });
                    //Create a dialog:
                    AlertDialog dialogToShow = alertDialog.create();
                    dialogToShow.show();
                }
            });
            //Su kien 2 Buttons tren item:
            btn_Plus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MonAn itemSelected = listData.get(getLayoutPosition());
                    String maNhaHang = itemSelected.getMaNhaHang();
                    //Check
                    if(Cart.getCartContent().size() != 0 && maNhaHangNotExist(maNhaHang)){
                        Toast.makeText(MyApplication.getCurrentContext(), "Bạn phải chọn các món ăn trong cùng một nhà hàng!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Cart.addToCart(itemSelected);
                        String count = String.valueOf(Cart.getItemCountById(itemSelected.getId()));
                        update_SoLuongDat(count);
                        MainActivity.setupBadge(Cart.getAllItemCount());
                        Activity_MonAn_Of_NhaHang.setupBadge(Cart.getAllItemCount());
                    }

                }
            });
            btn_Minus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "click - " + getLayoutPosition(), Toast.LENGTH_LONG).show();
                    String itemIdSelected = listData.get(getLayoutPosition()).getId();
                    Cart.removeFromCart(itemIdSelected);
                    String count = String.valueOf(Cart.getItemCountById(itemIdSelected));
                    update_SoLuongDat(count);
                    MainActivity.setupBadge(Cart.getAllItemCount());
                    Activity_MonAn_Of_NhaHang.setupBadge(Cart.getAllItemCount());
                    notifyDataSetChanged();
                    //Toast.makeText(context, "Item in Cart: " + Cart.getAllItemCount(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void update_SoLuongDat(String count){
            textView_SoLuongDat.setText(count);
        }

        @Override
        public void onClick(View v){

        }

        public boolean maNhaHangNotExist(String maNhaHang){
            String maNhaHang_in_cart = Cart.getCartContent().get(0).getMaNhaHang();
            if(maNhaHang_in_cart == null)
                return false;
            if(maNhaHang_in_cart.equalsIgnoreCase(maNhaHang))
                return false; //exist
            return true; //not exist
        }
    }
}
