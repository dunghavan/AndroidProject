package com.example.dung.demo_recyclerview;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.model.Input_Information;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Input_Activity extends AppCompatActivity {

    RadioButton radioButton_Nam, radioButton_Nu;
    Spinner spinner_NamSinh, spinner_CanNang, spinner_CheDo, spinner_NhuCau, spinner_CheDoLaoDong, spinner_BuaAn;
    Button btn_Submit;
    Input_Information input_information;
    TextView actionBarTitle;
    CheckBox checkBox_anChay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);
        actionBarTitle.setText("Tư vấn món ăn");

        // Connect Views:
        radioButton_Nam = (RadioButton)findViewById(R.id.radio_nam_input_activity);
        radioButton_Nu = (RadioButton)findViewById(R.id.radio_nu_input_activity);
        spinner_NamSinh = (Spinner)findViewById(R.id.spinner_namsinh_input_activity);
        spinner_CanNang = (Spinner)findViewById(R.id.spinner_cannang_input_activity);
        spinner_CheDo = (Spinner)findViewById(R.id.spinner_chedo_input_activity);
        spinner_NhuCau = (Spinner)findViewById(R.id.spinner_nhucau_input_activity);
        spinner_CheDoLaoDong = (Spinner)findViewById(R.id.spinner_chedolaodong_input_activity);
        spinner_BuaAn = (Spinner)findViewById(R.id.spinner_buaan_input_activity);
        btn_Submit = (Button)findViewById(R.id.btn_hien_thi_mon_an_input_activity);
        checkBox_anChay = (CheckBox) findViewById(R.id.checkbox_anchay_in_input_activity);

        setUpSpinners(true);

        radioButton_Nam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setUpSpinners(b);
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput_Information();
            }
        });

    }

    private void getInput_Information(){
        input_information = new Input_Information();

        //Gioi tinh
        if(radioButton_Nam.isChecked()){
            input_information.setGioiTinh(1);
        }
        else {
            input_information.setGioiTinh(2);
        }

        // Nam sinh
        if(spinner_NamSinh.getSelectedItem() != null){
            try{
                input_information.setNamSinh(Integer.parseInt(spinner_NamSinh.getSelectedItem().toString()));
            }
            catch (Exception e){
                Log.d("InputActivity", "Err parse NamSinh");
            }
        }

        // Can nang
        if(spinner_CanNang.getSelectedItem() != null){
            try{
                input_information.setCanNang(Integer.parseInt(spinner_CanNang.getSelectedItem().toString()));
            }
            catch (Exception e){
                Log.d("InputActivity", "Err parse CanNang");
            }
        }

        // Che do
        if(spinner_CheDo.getSelectedItem() != null){
            String cheDo = spinner_CheDo.getSelectedItem().toString();
            switch (cheDo){
                case "Bình thường": input_information.setCheDo(1);
                    break;
                case "Bệnh tim mạch": input_information.setCheDo(2);
                    break;
                case "Bệnh béo phì": input_information.setCheDo(3);
                    break;
                case "Bệnh dạ dày": input_information.setCheDo(4);
                    break;
                case "Bệnh Gout": input_information.setCheDo(5);
                    break;
                case "Bệnh tiểu đường": input_information.setCheDo(6);
                    break;
                case "Mang thai": input_information.setCheDo(7);
                    break;
                case "Cho con bú": input_information.setCheDo(8);
                    break;
                    default: input_information.setCheDo(1);
            }
        }

        // Nhu cau:
        if(spinner_NhuCau.getSelectedItem() != null){
            String mucDich = spinner_NhuCau.getSelectedItem().toString();
            switch (mucDich){
                case "Giữ cân": input_information.setNhuCau(0);
                    break;
                case "Giảm cân": input_information.setNhuCau(1);
                    break;
                case "Tăng cân": input_information.setNhuCau(2);
                    break;
                case "Mang thai": input_information.setNhuCau(3);
                    break;
                case "Cho con bú": input_information.setNhuCau(4);
                    break;
                default: input_information.setNhuCau(1);
            }
        }

        // Che do lao dong:
        if(spinner_CheDoLaoDong.getSelectedItem() != null){
            String mucDich = spinner_CheDoLaoDong.getSelectedItem().toString();
            switch (mucDich){
                case "Lao động nhẹ": input_information.setCheDoLaoDong(1);
                    break;
                case "Lao động vừa": input_information.setCheDoLaoDong(2);
                    break;
                case "Lao động nặng": input_information.setCheDoLaoDong(3);
                    break;
                default: input_information.setCheDoLaoDong(1);
            }
        }

        // Bua an:
        if(spinner_BuaAn.getSelectedItem() != null){
            String mucDich = spinner_BuaAn.getSelectedItem().toString();
            switch (mucDich){
                case "Sáng": input_information.setBuaAn(1);
                    break;
                case "Trưa": input_information.setBuaAn(2);
                    break;
                case "Tối": input_information.setBuaAn(3);
                    break;
                default: input_information.setBuaAn(1);
            }
        }

        // An chay:
        if(checkBox_anChay.isChecked()){
            input_information.setAnChay(true);
        }
        else {
            input_information.setAnChay(false);
        }

        // Send information to Activity_MonAnDeXuat
        Intent intent = new Intent(this, Activity_MonAnDeXuat.class);
        intent.putExtra("Input Information", input_information);
        this.startActivity(intent);

    }

    private void setUpSpinners(Boolean _gioiTinh){
        // Nam Sinh
        List<Integer> data_NamSinh = new ArrayList<>();
        for(int i = 1950; i < 2017; i++){
            data_NamSinh.add(i);
        }
        ArrayAdapter<Integer> adapter_NamSinh = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, data_NamSinh);
        adapter_NamSinh.setDropDownViewResource(R.layout.simple_list_item_1);
        spinner_NamSinh.setAdapter(adapter_NamSinh);
        spinner_NamSinh.setSelection(45);

        // Can Nang
        List<Integer> data_CanNang = new ArrayList<>();
        for(int i = 20; i < 120; i++){
            data_CanNang.add(i);
        }
        ArrayAdapter<Integer> adapter_CanNang = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, data_CanNang);
        adapter_CanNang.setDropDownViewResource(R.layout.simple_list_item_1);
        spinner_CanNang.setAdapter(adapter_CanNang);
        spinner_CanNang.setSelection(30);

        //Che Do
        String[] arr_chedo = {"Bình thường", "Bệnh tim mạch", "Bệnh béo phì", "Bệnh dạ dày", "Bệnh Gout", "Bệnh tiểu đường"};
        List<String> data_CheDo = new ArrayList<>();
        data_CheDo.addAll(Arrays.asList(arr_chedo));
        if(!_gioiTinh){
            data_CheDo.add("Mang thai");
            data_CheDo.add("Cho con bú");
        }

        ArrayAdapter<String> adapter_CheDo = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data_CheDo);
        adapter_CheDo.setDropDownViewResource(R.layout.simple_list_item_1);
        spinner_CheDo.setAdapter(adapter_CheDo);

        // Nhu Cau
        List<String> data_NhuCau = new ArrayList<>();
        data_NhuCau.add("Giữ cân");
        data_NhuCau.add("Giảm cân");
        data_NhuCau.add("Tăng cân");
        if(!_gioiTinh){
            data_NhuCau.add("Mang thai");
            data_NhuCau.add("Cho con bú");
        }
        ArrayAdapter<String> adapter_NhuCau = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data_NhuCau);
        adapter_NhuCau.setDropDownViewResource(R.layout.simple_list_item_1);
        spinner_NhuCau.setAdapter(adapter_NhuCau);

        // Che Do Lao Dong
        String[] data_CheDoLaoDong = {"Lao động nhẹ", "Lao động vừa", "Lao động nặng"};
        ArrayAdapter<String> adapter_CheDoLaoDong = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data_CheDoLaoDong);
        adapter_CheDoLaoDong.setDropDownViewResource(R.layout.simple_list_item_1);
        spinner_CheDoLaoDong.setAdapter(adapter_CheDoLaoDong);

        // Bua An
        String[] data_BuaAn = {"Sáng", "Trưa", "Tối"};
        ArrayAdapter<String> adapter_BuaAn = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data_BuaAn);
        adapter_BuaAn.setDropDownViewResource(R.layout.simple_list_item_1);
        spinner_BuaAn.setAdapter(adapter_BuaAn);
    }

    @Override
    public void onResume(){
        super.onResume();
        MyApplication.setCurrentContext(this);
    }
}
