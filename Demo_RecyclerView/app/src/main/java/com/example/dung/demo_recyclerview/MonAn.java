package com.example.dung.demo_recyclerview;

/**
 * Created by Dung on 9/27/2017.
 */

public class MonAn {
    private String tenMonAn;
    private Double gia;
    private String imgUrl;

    public MonAn(String tenMonAn, Double gia, String imgUrl) {
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.imgUrl = imgUrl;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
