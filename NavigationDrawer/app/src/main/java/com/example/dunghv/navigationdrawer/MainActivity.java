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
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo toolbar mới thay cho ActionBar:
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            Toast.makeText(this, "This is Camera", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "This is Gallery", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "This is Slideshow", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "This is Nav Manager", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "This is Nav Share", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "This is Nav Send", Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
