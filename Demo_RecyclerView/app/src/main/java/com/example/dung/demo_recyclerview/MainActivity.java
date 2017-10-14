package com.example.dung.demo_recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.fragment.Fragment_MonAn_6_Tabs;
import com.example.dung.demo_recyclerview.fragment.Fragment_NhaHang;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private static Context context;

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
        Toast.makeText(this, "onResume method!", Toast.LENGTH_SHORT).show();
        super.onResume();
    }
    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause method!", Toast.LENGTH_SHORT).show();
        super.onPause();
    }
}
