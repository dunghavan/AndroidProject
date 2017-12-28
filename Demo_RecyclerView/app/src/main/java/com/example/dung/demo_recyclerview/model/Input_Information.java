package com.example.dung.demo_recyclerview.model;

import java.io.Serializable;

/**
 * Created by dunghv on 12/27/17.
 */

public class Input_Information implements Serializable{
    private int gioiTinh;
    private int namSinh;
    private int canNang;
    private int cheDo;
    private int nhuCau;
    private int cheDoLaoDong;
    private int buaAn;

    public int getGioiTinh() {
        return gioiTinh;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public int getCanNang() {
        return canNang;
    }

    public void setCanNang(int canNang) {
        this.canNang = canNang;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getCheDo() {
        return cheDo;
    }

    public void setCheDo(int cheDo) {
        this.cheDo = cheDo;
    }

    public int getNhuCau() {
        return nhuCau;
    }

    public void setNhuCau(int nhuCau) {
        this.nhuCau = nhuCau;
    }

    public int getCheDoLaoDong() {
        return cheDoLaoDong;
    }

    public void setCheDoLaoDong(int cheDoLaoDong) {
        this.cheDoLaoDong = cheDoLaoDong;
    }

    public int getBuaAn() {
        return buaAn;
    }

    public void setBuaAn(int buaAn) {
        this.buaAn = buaAn;
    }
}
