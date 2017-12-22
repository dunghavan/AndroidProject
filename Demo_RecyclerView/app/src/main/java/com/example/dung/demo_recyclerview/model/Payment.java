package com.example.dung.demo_recyclerview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Dung on 12/22/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {
    String maKhachHang;
    String ngayThang;
    String ngayGioGiao;
    String diaChiGiao;
    String soDienThoai;
    String hinhThucThanhToan;
    String payID;
    Double tongTien;
    String ctDonDatHang;
    String id;

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public String getNgayGioGiao() {
        return ngayGioGiao;
    }

    public void setNgayGioGiao(String ngayGioGiao) {
        this.ngayGioGiao = ngayGioGiao;
    }

    public String getDiaChiGiao() {
        return diaChiGiao;
    }

    public void setDiaChiGiao(String diaChiGiao) {
        this.diaChiGiao = diaChiGiao;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public String getCtDonDatHang() {
        return ctDonDatHang;
    }

    public void setCtDonDatHang(String ctDonDatHang) {
        this.ctDonDatHang = ctDonDatHang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
