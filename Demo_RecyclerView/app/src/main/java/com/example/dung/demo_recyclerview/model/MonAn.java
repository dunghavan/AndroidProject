package com.example.dung.demo_recyclerview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Dung on 9/27/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonAn {
    private String tenMonAn;
    private String maLoai;
    private Double donGia;
    private String maNhaHang;
    private String hinhAnh;
    private Float soDiem;
    private int luotXem;
    private String nguyenLieu;
    private int nangLuong;
    private Double kL_Dam;
    private Double kL_Beo;
    private Double kL_Duong;
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

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
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

    public Float getSoDiem() {
        return soDiem;
    }

    public void setSoDiem(Float soDiem) {
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

    public Double getkL_Dam() {
        return kL_Dam;
    }

    public void setkL_Dam(Double kL_Dam) {
        this.kL_Dam = kL_Dam;
    }

    public Double getkL_Beo() {
        return kL_Beo;
    }

    public void setkL_Beo(Double kL_Beo) {
        this.kL_Beo = kL_Beo;
    }

    public Double getkL_Duong() {
        return kL_Duong;
    }

    public void setkL_Duong(Double kL_Duong) {
        this.kL_Duong = kL_Duong;
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
