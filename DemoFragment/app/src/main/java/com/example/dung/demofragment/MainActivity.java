package com.example.dung.demofragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showText(String t1, String t2){
        BottomFragment bottomFragment = (BottomFragment)this.getSupportFragmentManager().findFragmentById(R.id.bottom_fragment);
        bottomFragment.showTextInImage(t1, t2);
    }
}
