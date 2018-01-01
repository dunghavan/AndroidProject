package com.example.dung.demo_recyclerview;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dung.demo_recyclerview.model.CTDonDatHang;
import com.example.dung.demo_recyclerview.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Activity_Payment_Details extends AppCompatActivity {

    final DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
    TextView tv_tongSoMon, tv_tongSoTien, tv_ngayLap, tv_ngayGiaoHang, tv_diaChiGiao, tv_SDT
            , tv_hinhThucThanhToan, tv_payID, tv_textViewToAppend;
    TextView actionBarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        tv_tongSoMon = findViewById(R.id.textview_tongSoMon_in_payment_detail);
        tv_tongSoTien = findViewById(R.id.textview_tongSoTien_in_payment_detail);
        tv_ngayLap = findViewById(R.id.textview_ngayLap_in_payment_detail);
        tv_ngayGiaoHang = findViewById(R.id.textview_ngayGiaoHang_in_payment_detail);
        tv_diaChiGiao = findViewById(R.id.textview_diaChiGiaoHang_in_payment_detail);
        tv_SDT = findViewById(R.id.textview_SDT_in_payment_detail);
        tv_hinhThucThanhToan = findViewById(R.id.textview_hinhThucThanhToan_in_payment_detail);
        tv_payID = findViewById(R.id.textview_PaypalID_in_payment_detail);
        tv_textViewToAppend = findViewById(R.id.textView_to_append_text);

        Payment payment = (Payment)getIntent().getSerializableExtra("PaymentObject");
        // bind data to screen:
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        actionBarTitle = (TextView)findViewById(R.id.action_bar_title_text);
        actionBarTitle.setText("Chi tiết đơn hàng " + payment.getId());
        //getSupportActionBar().setTitle("Chi tiết đơn hàng " + payment.getId());

        tv_tongSoTien.setText("Tổng thanh toán: " + String.format("%s", decimalFormat.format(payment.getTongTien())) + "đ");
        tv_ngayLap.setText("Ngày lập: " + payment.getNgayThang());
        tv_ngayGiaoHang.setText("Thời gian giao hàng: " + payment.getNgayGioGiao());
        if(payment.getDiaChiGiao() != null)
            tv_diaChiGiao.setText("Địa chỉ giao hàng: " + payment.getDiaChiGiao());
        tv_SDT.setText("Số điện thoại: " + payment.getSoDienThoai());
        if(payment.getHinhThucThanhToan().equalsIgnoreCase("HinhThucThanhToan2")){
            tv_hinhThucThanhToan.setText("Hình thức: Thanh toán qua cổng PayPal");
            tv_payID.setText("PayID: " + payment.getPayID());
        }
        else {
            tv_hinhThucThanhToan.setText("Hình thức: Thanh toán khi nhận hàng");
            tv_payID.setVisibility(View.GONE);
        }
        String paymentString = "";
        ObjectMapper mapper = new ObjectMapper();
        try{
            paymentString = mapper.writeValueAsString(payment);
        }
        catch (Exception e){
            Log.d("Err payment", "Activity_Payment_Details: line 60");
        }

        parsePaymentDetail(paymentString);

        //tv_tongSoMon.setText("Tổng số món: " + payment.get);
    }

    private void parsePaymentDetail(String paymentString){
        JSONArray jsonArray = getCTDonDatHang(paymentString);
        List<CTDonDatHang> dsMonAn = new ArrayList<>();
        try{
            CTDonDatHang ctDonDatHang;
            for(int n = 0; n < jsonArray.length(); n++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(n);
                ObjectMapper objectMapper = new ObjectMapper();
                ctDonDatHang = objectMapper.readValue(jsonObject.toString(), CTDonDatHang.class);
                dsMonAn.add(ctDonDatHang);

                // do some stuff....
            }
            tv_tongSoMon.setText("Tổng số món: " + dsMonAn.size());
            tv_textViewToAppend.setText("");
            for(CTDonDatHang item: dsMonAn){
                tv_textViewToAppend.append(item.getMaMonAn() + ": " + item.getDonGia() + "đ x " + item.getSoLuong() + " = " + item.getThanhTien() + "đ\n");
            }
        }
        catch (Exception e){
            Log.d("Err parse item", e.getMessage());
        }
    }

    private JSONArray getCTDonDatHang(String paymentString) {
        String doubleQuotationMarks = "\"";
        String singleQuotationMark = "'";
        String backSlash = "\\\\";

        String removedAllBackSlash = paymentString.replaceAll(backSlash, "");
        String replaceQuotes = removedAllBackSlash.replaceAll(singleQuotationMark, doubleQuotationMarks);
        String r = replaceQuotes.replaceAll("\"\\[", "\\[");
        String result = r.replaceAll("\\]\"", "\\]");

        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("ctDonDatHang");
        } catch (Exception e) {
            Log.d("Err parse payment", e.getMessage());
        }
        return jsonArray;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        MyApplication.setCurrentContext(this);
    }

}
