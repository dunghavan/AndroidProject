package com.example.dung.demo_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.R;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.viewpager_adapter.VP_Adapter_MonAn_2Tabs_For_NhaHang;
import com.example.dung.demo_recyclerview.viewpager_adapter.ViewPagerAdapter_MonAn_2_Tabs;
import com.squareup.picasso.Picasso;

public class Activity_MonAn_Of_NhaHang extends AppCompatActivity {
    ImageView imageView_hinhAnh;
    TextView tv_tenNhaHang, tv_diaChi, tv_gioMoCua;
    TabLayout tabLayout;
    ViewPager viewPager;
    private static Context context;
    TextView actionBarTitle;
    public Activity_MonAn_Of_NhaHang(){
    }

    public static Context getContext(){
        return context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monan_of_nhahang);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);

        imageView_hinhAnh = (ImageView)findViewById(R.id.imageView_nhahang_in_nhahang_detail);
        tv_tenNhaHang = (TextView)findViewById(R.id.tv_tennhahang_in_nhahang_detail);
        tv_diaChi = (TextView)findViewById(R.id.tv_giomocua_in_nhahang_detail);
        tv_gioMoCua = (TextView)findViewById(R.id.tv_diachi_in_nhahang_detail);

        Intent intent = getIntent();
        NhaHang nhaHang = (NhaHang)intent.getSerializableExtra("NHA_HANG");
        //getSupportActionBar().setTitle("Nhà hàng " + nhaHang.getTenNhaHang());
        actionBarTitle.setText("Nhà hàng " + nhaHang.getTenNhaHang());

        tabLayout = (TabLayout)findViewById(R.id.tabLayout_monan_of_nhahang);
        viewPager = (ViewPager)findViewById(R.id.view_pager_monan_of_nhahang);

        tv_tenNhaHang.setText(nhaHang.getTenNhaHang());
        tv_diaChi.setText("Địa chỉ: " + nhaHang.getDiaChi());
        tv_gioMoCua.setText("Giờ mở cửa: " + nhaHang.getGioMoCua() + "h - " + nhaHang.getGioDongCua() + "h");

        String imageUrl = nhaHang.getHinhAnh();
        //if (imageUrl != null && !imageUrl.isEmpty())
        {
            Picasso.with(MyApplication.getCurrentContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.failed_load_restaurant)
                    .fit()
                    .into(imageView_hinhAnh);
        }

        FragmentManager fm = getSupportFragmentManager();
        VP_Adapter_MonAn_2Tabs_For_NhaHang viewPagerAdapter_monAn_of_nhaHang
                = new VP_Adapter_MonAn_2Tabs_For_NhaHang(nhaHang.getId(), fm);

        viewPager.setAdapter(viewPagerAdapter_monAn_of_nhaHang);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
    }

    // Menu hien thi gio hang:
    static TextView textCartItemCount;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        final MenuItem cartItem = menu.findItem(R.id.cart);

        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.counter);

        // /Open CartActivity when click
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cartItem);
            }
        });
        setupBadge(Cart.getAllItemCount());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent = new Intent(this, CartActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void setupBadge(int mCartItemCount){
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
