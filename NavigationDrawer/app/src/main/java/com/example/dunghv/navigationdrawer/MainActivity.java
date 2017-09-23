package com.example.dunghv.navigationdrawer;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo toolbar mới thay cho ActionBar:
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Khởi tạo và bắt sự kiện cho floatingActionButton:
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Khởi tạo NavigationDrawer:
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar
                                    , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Bắt sự kiện cho button Back:
    @Override
    public void onBackPressed(){
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    //Gán menu vào action bar:
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    //Bắt sự kiện click vào mỗi item trong NavigationView:
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
