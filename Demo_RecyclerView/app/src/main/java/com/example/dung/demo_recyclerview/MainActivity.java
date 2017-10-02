package com.example.dung.demo_recyclerview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.Fragment.AndroidFragment;
import com.example.dung.demo_recyclerview.Fragment.IOSFragment;
import com.example.dung.demo_recyclerview.Fragment.PHPFragment;

import java.util.ArrayList;
import java.util.List;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        boolean b = checkInternetConnection();

        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (id){
                    case R.id.action_one:
                        fragment = new AndroidFragment();
                        transaction.replace(R.id.frame_layout, fragment).commit();
                        break;
                    case R.id.action_two:
                        fragment = new IOSFragment();
                        transaction.replace(R.id.frame_layout, fragment).commit();
                        break;
                    case R.id.action_three:
                        fragment = new PHPFragment();
                        transaction.replace(R.id.frame_layout, fragment).commit();
                        //showList_MonAn();
                        break;
                }
                return true;
            }
        });

        bottomNavigation.getMenu().findItem(R.id.action_three).setChecked(true);
        bottomNavigation.setSelectedItemId(R.id.action_three);

    }
    @Override
    public void onClick(View v){

    }

    public boolean checkInternetConnection() {

        ConnectivityManager connManager =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(context, "No default network is currently active", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(context, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(context, "Network not available", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(context, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

}
