package com.example.dung.demo_recyclerview;

import android.util.Log;

import com.example.dung.demo_recyclerview.model.MonAn;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

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

    public static void printString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            for(MonAn item: cartContent)
                Log.d("Cart to string", objectMapper.writeValueAsString(item));
        }
        catch (JsonProcessingException e){
            Log.d("JsonProcessingException", e.getMessage());
        }

    }

}

