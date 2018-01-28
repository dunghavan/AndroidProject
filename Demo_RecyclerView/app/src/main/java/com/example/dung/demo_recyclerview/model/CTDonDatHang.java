package com.example.dung.demo_recyclerview.model;

/**
 * Created by Dung on 12/11/2017.
 */

public class CTDonDatHang {
    private String MaMonAn;
    private Double DonGia;
    private int SoLuong;
    private Double ThanhTien;
    private String TenMonAn;
    private String MaLoai;
    private String MaNhaHang;
    private String MaKhachHang;

    public String getMaMonAn() {
        return MaMonAn;
    }

    public void setMaMonAn(String maMonAn) {
        MaMonAn = maMonAn;
    }

    public Double getDonGia() {
        return DonGia;
    }

    public void setDonGia(Double donGia) {
        DonGia = donGia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public Double getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(Double thanhTien) {
        ThanhTien = thanhTien;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(String maLoai) {
        MaLoai = maLoai;
    }


    public String getMaNhaHang() {
        return MaNhaHang;
    }

    public void setMaNhaHang(String maNhaHang) {
        MaNhaHang = maNhaHang;
    }

    public String itemToString(){
        return "\" " + "\"MaMonAn\": " + MaMonAn
                + ", " + "\"DonGia\": " + DonGia
                + ", " + "\"SoLuong\": " + SoLuong
                + ", " + "\"ThanhTien\": " + ThanhTien;
    }

    public String getMaKhachHang() {
        return MaKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        MaKhachHang = maKhachHang;
    }
}
