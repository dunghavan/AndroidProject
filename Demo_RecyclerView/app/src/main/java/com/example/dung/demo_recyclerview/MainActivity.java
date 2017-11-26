package com.example.dung.demo_recyclerview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.fragment.Fragment_MonAn_FindResult;
import com.example.dung.demo_recyclerview.fragment.Fragment_MonAn_6_Tabs;
import com.example.dung.demo_recyclerview.fragment.Fragment_NangCao_2_Tabs;
import com.example.dung.demo_recyclerview.fragment.Fragment_NhaHang;
import com.example.dung.demo_recyclerview.fragment.Fragment_NhaHang_FindResult;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private static Context context;
    public static String currentTab = "";
    private Menu mOptionsMenu;
    FragmentTransaction transaction;

    private static TextView textCartItemCount;

    public static Context getMainActivityContext(){
        return context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        getSupportActionBar().setTitle("Order App");
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
                        fragment = new Fragment_NhaHang();
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        getSupportActionBar().setTitle("Nhà Hàng");
                        currentTab = MyConstant.TAB_NHAHANG;

                        if (mOptionsMenu != null) {
                            mOptionsMenu.findItem(R.id.action_search).setVisible(true);
                            onPrepareOptionsMenu(mOptionsMenu);
                        }
                        break;
                    case R.id.action_food:
                        fragment = new Fragment_MonAn_6_Tabs();
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        getSupportActionBar().setTitle("Món Ăn");
                        currentTab = MyConstant.TAB_MONAN;

                        if (mOptionsMenu != null) {
                            mOptionsMenu.findItem(R.id.action_search).setVisible(true);
                            onPrepareOptionsMenu(mOptionsMenu);
                        }
                        break;
                    case R.id.action_advanced:
                        fragment = new Fragment_NangCao_2_Tabs();
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        getSupportActionBar().setTitle("Khuyến mại");
                        currentTab = MyConstant.TAB_NANGCAO;

                        if (mOptionsMenu != null) {
                            mOptionsMenu.findItem(R.id.action_search).setVisible(false);
                            mOptionsMenu.findItem(R.id.action_search).collapseActionView();
                            onPrepareOptionsMenu(mOptionsMenu);
                        }
                        break;
                    case R.id.action_history:
                        break;
                    case R.id.action_user:
                        break;
                }
                return true;
            }
        });

        bottomNavigation.getMenu().findItem(R.id.action_food).setChecked(true);
        bottomNavigation.setSelectedItemId(R.id.action_food);

    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume method!", Toast.LENGTH_SHORT).show();
        MyApplication.setCurrentContext(this);
    }
    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause method!", Toast.LENGTH_SHORT).show();
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
                    fragment = new Fragment_MonAn_FindResult();
                    transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();

                }
//                if(currentTab == MyConstant.TAB_NHAHANG){
//                    fragment = new Fragment_NhaHang_FindResult();
//                    transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
//
//                }
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
//                if(currentTab == MyConstant.TAB_NHAHANG){
//                    transaction = fragmentManager.beginTransaction();
//                    fragment = new Fragment_NhaHang();
//                    transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
//                }

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
}
