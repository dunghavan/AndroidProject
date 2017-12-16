package com.example.dung.demo_recyclerview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Dung on 9/27/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonAn {
    private String tenMonAn;
    private Double donGia;
    private String maNhaHang;
    private String hinhAnh;
    private int soDiem;
    private int luotXem;
    private String nguyenLieu;
    private int protein;
    private int nangLuong;
    private String moTa;
    private String tenNhaHang;
    private String id;

    private String createDate;
    private String createdBy;
    private boolean active;
    private String updateBy;

    private Double giaKhuyenMai;
    private Double khuyenMai;

    //add for cart:
    private int itemCount;

    public MonAn(){

    }
    public MonAn(String id, String tenMonAn, Double donGia, String hinhAnh, int protein, int nangLuong, String moTa,
                 Double khuyenMai) {
        this.tenMonAn = tenMonAn;
        this.donGia = donGia;
        this.hinhAnh = hinhAnh;
        this.protein = protein;
        this.nangLuong = nangLuong;
        this.moTa = moTa;
        this.id = id;
        this.khuyenMai = khuyenMai;
        this.itemCount = 0;
    }
    public MonAn(String tenMonAn, Double donGia, String maNhaHang, String hinhAnh, int soDiem, int luotXem,
                 String nguyenLieu, int protein, int nangLuong, String moTa, String tenNhaHang, String id, String createDate,
                 String createdBy, boolean active, String updateBy, Double giaKhuyenMai, Double khuyenMai) {
        this.tenMonAn = tenMonAn;
        this.donGia = donGia;
        this.maNhaHang = maNhaHang;
        this.hinhAnh = hinhAnh;
        this.soDiem = soDiem;
        this.luotXem = luotXem;
        this.nguyenLieu = nguyenLieu;
        this.protein = protein;
        this.nangLuong = nangLuong;
        this.moTa = moTa;
        this.tenNhaHang = tenNhaHang;
        this.id = id;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.active = active;
        this.updateBy = updateBy;
        this.giaKhuyenMai = giaKhuyenMai;
        if(khuyenMai == null)
            this.khuyenMai = 0D;
        else
            this.khuyenMai = khuyenMai;

        this.itemCount = 0;
    }

    public int getItemCount() {
            return itemCount;
    }

    public Double getToTal_1_Item(){
        return this.itemCount * getGiaKhuyenMai();
    }

    public String getTenNhaHang() {
        return tenNhaHang;
    }

    public void setTenNhaHang(String tenNhaHang) {
        this.tenNhaHang = tenNhaHang;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public String getMaNhaHang() {
        return maNhaHang;
    }

    public void setMaNhaHang(String maNhaHang) {
        this.maNhaHang = maNhaHang;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoDiem() {
        return soDiem;
    }

    public void setSoDiem(int soDiem) {
        this.soDiem = soDiem;
    }

    public int getLuotXem() {
        return luotXem;
    }

    public void setLuotXem(int luotXem) {
        this.luotXem = luotXem;
    }

    public String getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(String nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getNangLuong() {
        return nangLuong;
    }

    public void setNangLuong(int nangLuong) {
        this.nangLuong = nangLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getKhuyenMai() {
        return khuyenMai == null ? 0 : khuyenMai;
    }

    public void setKhuyenMai(Double khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public Double getGiaKhuyenMai() {
        return this.donGia - (this.getKhuyenMai() / 100) * this.donGia;
    }
    public void setGiaKhuyenMai(Double giaKhuyenMai) {
        this.giaKhuyenMai = giaKhuyenMai;
    }
}
