package com.example.dung.demo_recyclerview.model;

/**
 * Created by Dung on 10/6/2017.
 */

public class NhaHang {
    String tenNhaHang;
    String diaChi;
    int khuyenMai;
    String urlHinhAnh;

    public NhaHang(String tenNhaHang, String diaChi, int khuyenMai, String urlHinhAnh) {
        this.tenNhaHang = tenNhaHang;
        this.diaChi = diaChi;
        this.khuyenMai = khuyenMai;
        this.urlHinhAnh = urlHinhAnh;
    }

    public String getTenNhaHang() {
        return tenNhaHang;
    }

    public void setTenNhaHang(String tenNhaHang) {
        this.tenNhaHang = tenNhaHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public String getUrlHinhAnh() {
        return urlHinhAnh;
    }

    public void setUrlHinhAnh(String urlHinhAnh) {
        this.urlHinhAnh = urlHinhAnh;
    }
}
