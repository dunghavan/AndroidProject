package com.example.dung.demo_recyclerview.model;

/**
 * Created by Dung on 12/11/2017.
 */

public class CTDonDatHang {
    private String MaDonDatHang;
    private String MaMonAn;
    private Double DonGia;
    private int SoLuong;
    private Double ThanhTien;

    public String getMaDonDatHang() {
        return MaDonDatHang;
    }

    public void setMaDonDatHang(String maDonDatHang) {
        MaDonDatHang = maDonDatHang;
    }

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

    public String itemToString(){
        return "\"MaDonDatHang\": " + MaDonDatHang
                + ", " + "\"MaMonAn\": " + MaMonAn
                + ", " + "\"DonGia\": " + DonGia
                + ", " + "\"SoLuong\": " + SoLuong
                + ", " + "\"ThanhTien\": " + ThanhTien;
    }
}
