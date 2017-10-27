package com.example.dung.demo_recyclerview;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.fragment.Fragment_MonAn_6_Tabs;
import com.example.dung.demo_recyclerview.fragment.Fragment_NhaHang;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private static Context context;

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
                int id = item.getItemId();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (id){
                    case R.id.action_home:
                        fragment = new Fragment_NhaHang();
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        getSupportActionBar().setTitle("Nhà Hàng");
                        break;
                    case R.id.action_food:
                        fragment = new Fragment_MonAn_6_Tabs();
                        transaction.replace(R.id.frame_layout_in_main_activity, fragment).commit();
                        getSupportActionBar().setTitle("Món Ăn");
                        break;
                    case R.id.action_sale:
                        break;
                    case R.id.action_history:
                        break;
                    case R.id.action_user:
                        break;
                }
                return true;
            }
        });

        bottomNavigation.getMenu().findItem(R.id.action_home).setChecked(true);
        bottomNavigation.setSelectedItemId(R.id.action_home);

    }
    @Override
    public void onClick(View v){

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.counter);

        setupBadge(0);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
