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
        data.add("Hà Văn Dũng");
        data.add("Phan Nhật Đăng");
        data.add("Phạm Quang Trung");
        data.add("Vũ Anh Thư");
        data.add("Lê Đình Nhật");
        data.add("Lê Đình Tiệp");
        data.add("Phan Anh Khoa");
        data.add("Trần Ngọc Tú");
        data.add("Hà Văn Dũng");
        data.add("Phan Nhật Đăng");
        data.add("Phạm Quang Trung");
        data.add("Vũ Anh Thư");
        data.add("Lê Đình Nhật");
        data.add("Lê Đình Tiệp");
        data.add("Phan Anh Khoa");
        data.add("Trần Ngọc Tú");
        data.add("Hà Văn Dũng");
        data.add("Phan Nhật Đăng");
        data.add("Phạm Quang Trung");
        data.add("Vũ Anh Thư");
        data.add("Lê Đình Nhật");
        data.add("Lê Đình Tiệp");
        data.add("Phan Anh Khoa");
        data.add("Trần Ngọc Tú");
        data.add("Hà Văn Dũng");
        data.add("Phan Nhật Đăng");
        data.add("Phạm Quang Trung");
        data.add("Vũ Anh Thư");
        data.add("Lê Đình Nhật");
        data.add("Lê Đình Tiệp");

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
        // Empty the TextBox
        inputText.setText("");
        inputText.requestFocus();
    }
}
