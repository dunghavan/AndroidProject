package com.example.dung.demo_recyclerview.model;

/**
 * Created by Dung on 9/27/2017.
 */

public class MonAn {
    private String id;
    private String tenMonAn;
    private Double gia;
    private Double giaKhuyenMai;
    private Double khuyenMai;
    private String imgUrl;

    public MonAn(String id, String tenMonAn, Double gia, String imgUrl) {
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.gia = gia;
        this.khuyenMai = 0D;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
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

    public Double getGiaKhuyenMai() {
        return this.gia - (this.khuyenMai / 100) * this.gia;
    }

    public void setGiaKhuyenMai(Double giaKhuyenMai) {
        this.giaKhuyenMai = giaKhuyenMai;
    }

    public Double getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(Double khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
}
