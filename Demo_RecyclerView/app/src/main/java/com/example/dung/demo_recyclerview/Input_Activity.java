package com.example.dung.demo_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Input_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }


    @Override
    public void onResume(){
        super.onResume();
        MyApplication.setCurrentContext(this);
    }
}
