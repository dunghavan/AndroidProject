package com.example.dung.demo_recyclerview;

import android.util.Log;

import com.example.dung.demo_recyclerview.model.CTDonDatHang;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dung on 10/15/2017.
 */

public class Cart {
    private static List<MonAn> cartContent = new ArrayList<>();

    public static List<MonAn> getCartContent(){
        return cartContent;
    }

    public static void addToCart(MonAn newMonAn){
        boolean added = false;
        if(cartContent.size() == 0) //Cart is empty:
        {
            newMonAn.setItemCount(1);
            cartContent.add(newMonAn);
            added = true;
        }
        else
            for (MonAn item: cartContent) {
                if (item.getId().equals(newMonAn.getId())) { //MonAn already exist in cartContent:
                    Log.d("Add to cart:", "Found itemId = " + item.getId() + ", count = " + item.getItemCount());
                    item.setItemCount(item.getItemCount() + 1);
                    Log.d("Increase itemCount: ", Integer.toString(item.getItemCount()));
                    added = true;
                    break;
                }
            }
            if(added == false) //MonAn not exist in cartContent:
            {
                newMonAn.setItemCount(1);
                cartContent.add(newMonAn);
                Log.d("Add new item: ", "id = " + newMonAn.getId());
            }

            printString();

    }

    public static void removeFromCart(String newItemId){
        MonAn item;
        for(int i = 0; i < cartContent.size(); i++){
            item = cartContent.get(i);
            if(item.getId().equals(newItemId)){
                if(item.getItemCount() > 1){
                    item.setItemCount(item.getItemCount() - 1);
                    Log.d("Decrease itemCount: ", "count = " + item.getItemCount() + ", id = " + item.getId());
                }
                else{
                    cartContent.remove(i);
                    Log.d("Remove itemId = " + item.getId(), "");
                }
            }
            else {
                Log.d("Not found itemId = " + newItemId, "Do nothing!");
            }
        }
        printString();
    }

    public static int getAllItemCount(){
        return cartContent.size();
    }

    public static int getItemCountById(String id){
        for (MonAn item: cartContent) {
            if (item.getId().equals(id)) {
                return item.getItemCount();
            }
        }
        return 0;
    }

    public static double getGiaKhuyenMaiById(String id){
        for (MonAn item: cartContent) {
            if (item.getId().equals(id)) {
                return item.getGiaKhuyenMai();
            }
        }
        return 0;
    }
//
//    public Double getToTalById(String _id){
//        return getItemCountById(_id) * getDonGiaById(_id);
//    }
//
//    public Double getDonGiaById(String _id){
//        for (MonAn item: cartContent) {
//            if (item.getId().equals(_id)) {
//                return item.getGiaKhuyenMai();
//            }
//        }
//        return 0.0;
//    }

    public static Double getTotal(){
        Double total = 0D;
        for(MonAn item: cartContent){
            total += item.getItemCount() * item.getGiaKhuyenMai();
        }
        return total;
    }


    public static void printString() {
        Log.d("Cart to string", contentToString());
    }

    public static String contentToString(){
        StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            for(MonAn item: cartContent)
                sb.append(objectMapper.writeValueAsString(item));
        }
        catch (JsonProcessingException e){
            Log.d("JsonProcessingException", e.getMessage());
        }
        return sb.toString();
    }

    public static String convertTo_CTDDH(){
        JsonArray jsonArray = new JsonArray();

        for (MonAn item: cartContent){
            CTDonDatHang ct = new CTDonDatHang();
            ct.setMaMonAn(item.getId());
            ct.setSoLuong(item.getItemCount());
            ct.setDonGia(item.getGiaKhuyenMai());
            ct.setThanhTien(item.getToTal_1_Item());
            ct.setTenMonAn(item.getTenMonAn());
            ct.setMaLoai(item.getMaLoai());
            ct.setMaNhaHang(item.getMaNhaHang());
            ct.setMaKhachHang(LoginActivity.ID);

            ObjectMapper objectMapper = new ObjectMapper();
            try{
                //String json = ct.itemToString();
                String json = objectMapper.writeValueAsString(ct);
                jsonArray.add(json);
            }
            catch (Exception e){
                Log.d("Convert CTDDH", e.getMessage());
            }
        }
        String result = jsonArray.toString().replaceAll("\\\\", ""); // Remove all \ digit
        String result1 = result.replaceAll("\"", "'"); // Replace all " digit to '
        String result2 = result1.replaceAll("\'\\{", "{"); // Replace all " digit to '
        String result3 = result2.replaceAll("\\}\'", "}"); // Replace all " digit to '
        return result3;
    }
    //          '{   {            }'        }

//    public static NhaHang result;
//    public static NhaHang getNhaHangFromServer(){
//        if(cartContent.size() != 0){
//            String maNhaHang = cartContent.get(0).getMaNhaHang();
//            APIService apiService = ApiUtils.getAPIService();
//            apiService.getNhaHangById(maNhaHang).enqueue(new Callback<NhaHang>() {
//                @Override
//                public void onResponse(Call<NhaHang> call, Response<NhaHang> response) {
//                    try {
//                        result = response.body();
//                    }
//                    catch (Exception e){
//                        Log.d("Err get NhaHang", e.getMessage());
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<NhaHang> call, Throwable t) {
//
//                }
//            });
//
//        }
//        return null;
//    }


}

