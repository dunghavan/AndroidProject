package com.example.dung.demo_recyclerview;

/**
 * Created by Dung on 10/15/2017.
 */

public class CartItem {
    private String itemId;
    private int itemCount;
    private String itemNote;

    public CartItem(String itemId, int itemCount, String itemNote) {
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.itemNote = itemNote;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
    }
}
