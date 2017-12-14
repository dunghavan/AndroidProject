package com.example.dung.demo_recyclerview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;

/**
 * Created by Dung on 10/6/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class NhaHang {
    String tenNhaHang;
    String diaChi;
    String hinhAnh;
    String gioMoCua;
    String gioDongCua;
    String SDT;
    int luotXem;
    int soDiem;
    String maKhuVuc;
    Double kinhDo;
    Double viDo;
    String id;
    String createDate;
    String createdBy;
    boolean active;
    String updateBy;

    public NhaHang(){

    }
    public NhaHang(String tenNhaHang, String diaChi, String urlHinhAnh) {
        this.tenNhaHang = tenNhaHang;
        this.diaChi = diaChi;
        this.hinhAnh = urlHinhAnh;
    }

    public NhaHang(String tenNhaHang, String diaChi, String hinhAnh, String gioMoCua, String gioDongCua, String SDT, int luotXem, int soDiem, String maKhuVuc, Double kinhDo, Double viDo, String id, String createDate, String createdBy, boolean active, String updateBy) {
        this.tenNhaHang = tenNhaHang;
        this.diaChi = diaChi;
        this.hinhAnh = hinhAnh;
        this.gioMoCua = gioMoCua;
        this.gioDongCua = gioDongCua;
        this.SDT = SDT;
        this.luotXem = luotXem;
        this.soDiem = soDiem;
        this.maKhuVuc = maKhuVuc;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
        this.id = id;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.active = active;
        this.updateBy = updateBy;
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

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String urlHinhAnh) {
        this.hinhAnh = urlHinhAnh;
    }

    public String getGioMoCua() {
        return gioMoCua;
    }

    public void setGioMoCua(String gioMoCua) {
        this.gioMoCua = gioMoCua;
    }

    public String getGioDongCua() {
        return gioDongCua;
    }

    public void setGioDongCua(String gioDongCua) {
        this.gioDongCua = gioDongCua;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getLuotXem() {
        return luotXem;
    }

    public void setLuotXem(int luotXem) {
        this.luotXem = luotXem;
    }

    public int getSoDiem() {
        return soDiem;
    }

    public void setSoDiem(int soDiem) {
        this.soDiem = soDiem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Double getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(Double kinhDo) {
        this.kinhDo = kinhDo;
    }

    public Double getViDo() {
        return viDo;
    }

    public void setViDo(Double viDo) {
        this.viDo = viDo;
    }

    @Override
    public String toString() {
        return "Tên: " + getTenNhaHang()
                + "Địa chỉ: " + getDiaChi()
                + "Url hình ảnh: " + hinhAnh;
    }
}
