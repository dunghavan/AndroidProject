package com.example.dung.demo_recyclerview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    CustomRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText inputText;
    List<MonAn> data;

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
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

    }
    @Override
    public void onClick(View v){

    }

    public void runDemoRecyclerView(){

        //Connect to views:
        //recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //
        data = initialData();

        customRecyclerAdapter = new CustomRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);
    }

    private List<MonAn> initialData(){
        data = new ArrayList<>();

        data.add(new MonAn("Gỏi Cuốn", 200000.0, "http://giadinh.vcmedia.vn/k:2016/photo-0-1472785146823/nhungmonanngonchogiadinhdip29.jpg"));
        data.add(new MonAn("Gà Luộc", 510000.0, "http://alohal.com/wp-content/uploads/2015/12/1437195423-thit-ga-mia.jpg"));
        data.add(new MonAn("Cơm Gà", 250000.0, "http://www.huynhthinga.com/wp-content/uploads/2014/11/2vv1.jpg"));
        data.add(new MonAn("Gà Lên Mâm", 300000.0, "https://i.ytimg.com/vi/NHlI_FsGtow/maxresdefault.jpg"));
        data.add(new MonAn("Chả Ram", 200000.0, "http://media.vietq.vn/files/ctvhanh/mon-ngon2.jpg"));
        data.add(new MonAn("Bún Bò", 450000.0, "http://anh.24h.com.vn/upload/1-2014/images/2014-03-19/1395195457-mon-an-nhat-sanuki-udon-.jpg"));

        return data;
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
