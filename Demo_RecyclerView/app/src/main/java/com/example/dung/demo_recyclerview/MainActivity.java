package com.example.dung.demo_recyclerview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.fragment.Fragment_BeforeLogin;
import com.example.dung.demo_recyclerview.fragment.Fragment_History;
import com.example.dung.demo_recyclerview.fragment.Fragment_MonAn_FindResult;
import com.example.dung.demo_recyclerview.fragment.Fragment_MonAn_6_Tabs;
import com.example.dung.demo_recyclerview.fragment.Fragment_NangCao_2_Tabs;
import com.example.dung.demo_recyclerview.fragment.Fragment_NhaHang;
import com.example.dung.demo_recyclerview.fragment.Fragment_NhaHang_FindResult;
import com.example.dung.demo_recyclerview.fragment.Fragment_Profile;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private static Context context;
    public static String currentTab = "";
    private Menu mOptionsMenu;
    FragmentTransaction transaction;
    TextView actionBarTitle;

    private static TextView textCartItemCount;

    public static Context getMainActivityContext(){
        return context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);
        actionBarTitle.setText("OrderFood App UIT");
        //getSupportActionBar().setTitle("Order App");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean b = CheckInternetState.checkInternetConnection();

        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction  = fragmentManager.beginTransaction();
                int id = item.getItemId();
                switch (id){
                    case R.id.action_home:
                        showCartIcon(true);
                        fragment = new Fragment_NhaHang();
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        //getSupportActionBar().setTitle("Nhà Hàng");
                        actionBarTitle.setText("Nhà Hàng");
                        currentTab = MyConstant.TAB_NHAHANG;

                        if (mOptionsMenu != null) {
                            mOptionsMenu.findItem(R.id.action_search).setVisible(true);
                            onPrepareOptionsMenu(mOptionsMenu);
                        }
                        break;
                    case R.id.action_food:
                        showCartIcon(true);
                        fragment = new Fragment_MonAn_6_Tabs();
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        //getSupportActionBar().setTitle("Món Ăn");
                        actionBarTitle.setText("Món Ăn");
                        currentTab = MyConstant.TAB_MONAN;

                        if (mOptionsMenu != null) {
                            mOptionsMenu.findItem(R.id.action_search).setVisible(true);
                            onPrepareOptionsMenu(mOptionsMenu);
                        }
                        break;
                    case R.id.action_advanced:
                        showCartIcon(true);
                        fragment = new Fragment_NangCao_2_Tabs();
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        //getSupportActionBar().setTitle("Khuyến mại");
                        actionBarTitle.setText("Khuyến mại");
                        currentTab = MyConstant.TAB_NANGCAO;

                        if (mOptionsMenu != null) {
                            mOptionsMenu.findItem(R.id.action_search).setVisible(false);
                            mOptionsMenu.findItem(R.id.action_search).collapseActionView();
                            onPrepareOptionsMenu(mOptionsMenu);
                        }
                        break;
                    case R.id.action_history:
                        showCartIcon(false);
                        if(LoginActivity.isAuthenticated()){
                            fragment = new Fragment_History();
                        }
                        else {
                            fragment = new Fragment_BeforeLogin();
                        }
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        //getSupportActionBar().setTitle("Lịch sử giao dịch");
                        actionBarTitle.setText("Lịch sử giao dịch");
                        currentTab = MyConstant.TAB_HISTORY;

                        if (mOptionsMenu != null) {
                            mOptionsMenu.findItem(R.id.action_search).setVisible(false);
                            mOptionsMenu.findItem(R.id.action_search).collapseActionView();
                            onPrepareOptionsMenu(mOptionsMenu);
                        }
                        break;
                    case R.id.action_user:
                        showCartIcon(false);
                        if(LoginActivity.isAuthenticated()){
                            fragment = new Fragment_Profile();
                        }
                        else {
                            fragment = new Fragment_BeforeLogin();
                        }
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        //getSupportActionBar().setTitle("Cá nhân");
                        actionBarTitle.setText("Cá nhân");
                        currentTab = MyConstant.TAB_PROFILE;

                        if (mOptionsMenu != null) {
                            mOptionsMenu.findItem(R.id.action_search).setVisible(false);
                            mOptionsMenu.findItem(R.id.action_search).collapseActionView();
                            onPrepareOptionsMenu(mOptionsMenu);
                        }
                        break;
                }
                return true;
            }
        });

        bottomNavigation.getMenu().findItem(R.id.action_food).setChecked(true);
        bottomNavigation.setSelectedItemId(R.id.action_food);

    }

    private void showCartIcon(boolean isVisible){
        if(mOptionsMenu != null)
            mOptionsMenu.setGroupVisible(R.id.main_menu_group, isVisible);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
        // Validate Fragment Profile
        if(currentTab == MyConstant.TAB_PROFILE){
            if(LoginActivity.isAuthenticated()){
                fragment = new Fragment_Profile();
            }
            else {
                fragment = new Fragment_BeforeLogin();
            }
            transaction  = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
        }
        // Validate Fragment History
        if(currentTab == MyConstant.TAB_HISTORY){
            if(LoginActivity.isAuthenticated()){
                fragment = new Fragment_History();
            }
            else {
                fragment = new Fragment_BeforeLogin();
            }
            transaction  = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        final MenuItem cartItem = menu.findItem(R.id.cart);

        //Open CartActivity when click
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onOptionsItemSelected(cartItem);
            }
        });

        //Cart
        View actionView = MenuItemCompat.getActionView(cartItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.counter);

        setupBadge(0);

        //SearchView:
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        fragmentManager = getSupportFragmentManager();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                transaction = fragmentManager.beginTransaction();
                if(currentTab == MyConstant.TAB_MONAN){
                    Bundle param = new Bundle();
                    param.putString("MONAN_KEYWORD", query);
                    fragment = new Fragment_MonAn_FindResult();
                    fragment.setArguments(param);
                    transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();

                    searchView.clearFocus();
                }

                if(currentTab == MyConstant.TAB_NHAHANG){
                    Bundle param = new Bundle();
                    param.putString("NHAHANG_KEYWORD", query);
                    fragment = new Fragment_NhaHang_FindResult();
                    fragment.setArguments(param);
                    transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();

                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //Toast.makeText(context, "onMenuItemActionCollapse envent", Toast.LENGTH_SHORT).show();
                if(currentTab == MyConstant.TAB_MONAN){
                    transaction = fragmentManager.beginTransaction();
                    fragment = new Fragment_MonAn_6_Tabs();
                    transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                }
                if(currentTab == MyConstant.TAB_NHAHANG){
                    transaction = fragmentManager.beginTransaction();
                    fragment = new Fragment_NhaHang();
                    transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                }

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //SearchView:
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        if(currentTab == MyConstant.TAB_NHAHANG)
            searchView.setQueryHint("Nhập tên nhà hàng");
        else
            searchView.setQueryHint("Nhập tên món ăn");
        return super.onPrepareOptionsMenu(menu);
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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn \"Back\" thêm lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
