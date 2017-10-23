package com.example.dung.demo_recyclerview;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dung on 10/15/2017.
 */

public class Cart {
    private static List<CartItem> cartContent = new ArrayList<>();

    public static void addToCart(String newItemId){
        boolean added = false;
        if(cartContent.size() == 0)
        {
            cartContent.add(new CartItem(newItemId, 1, ""));
            added = true;
        }
        else
            for (CartItem item: cartContent) {
                if (item.getItemId().equals(newItemId)) {
                    Log.d("Add to cart:", "Found itemId = " + item.getItemId() + ", count = " + item.getItemCount());
                    item.setItemCount(item.getItemCount() + 1);
                    Log.d("Increase itemCount: ", Integer.toString(item.getItemCount()));
                    added = true;
                    break;
                }
            }
            if(added == false)
            {
                CartItem newItem = new CartItem(newItemId, 1, "");
                cartContent.add(newItem);
                Log.d("Add new item: ", "id = " + newItemId);
            }

    }

    public static void removeFromCart(String newItemId){
        CartItem item;
        for(int i = 0; i < cartContent.size(); i++){
            item = cartContent.get(i);
            if(item.getItemId().equals(newItemId)){
                if(item.getItemCount() > 1){
                    item.setItemCount(item.getItemCount() - 1);
                    Log.d("Decrease itemCount: ", "count = " + item.getItemCount() + ", id = " + item.getItemId());
                }
                else{
                    cartContent.remove(i);
                    Log.d("Remove itemId = " + item.getItemId(), "");
                }
            }
            else {
                Log.d("Not found itemId = " + newItemId, "Do nothing!");
            }
        }
    }

    public static int getAllItemCount(){
        return cartContent.size();
    }

    public static int getItemCountById(String id){
        for (CartItem item: cartContent) {
            if (item.getItemId().equals(id)) {
                return item.getItemCount();
            }
        }
        return 0;
    }

}

