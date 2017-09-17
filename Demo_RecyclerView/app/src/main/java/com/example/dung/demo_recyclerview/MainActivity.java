package com.example.dung.demo_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    CustomRecyclerAdapter customRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText inputText;
    List<String> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect to views:
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        inputText = (EditText)findViewById(R.id.inputText);
        //Set fixed size for ReclyclerView:
        recyclerView.setHasFixedSize(true);
        //Setting the LayoutManager:
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //
        data = new ArrayList<String>();
        data.add("Ha Van Dung");
        data.add("Phan Nhat Dang");
        data.add("Pham Quang Trung");
        data.add("Vu Anh Thu");
        data.add("Le Dinh Nhat");
        data.add("Le Dinh Tiep");
        data.add("Phan Anh Khoa");
        data.add("Tran Ngoc Tu");

        customRecyclerAdapter = new CustomRecyclerAdapter(data);
        recyclerView.setAdapter(customRecyclerAdapter);

        ((Button)findViewById(R.id.button_Add)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        addItem();
    }

    public void addItem(){
        String newData = inputText.getText().toString();
        //data.add(newData);
        customRecyclerAdapter.addItem(data.size(), newData);
    }
}
