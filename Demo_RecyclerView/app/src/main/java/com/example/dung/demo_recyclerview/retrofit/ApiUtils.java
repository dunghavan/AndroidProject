package com.example.dung.demo_recyclerview.retrofit;

/**
 * Created by Dung on 12/2/2017.
 */

public class ApiUtils {

    private ApiUtils() {}
    public static final String BASE_URL = "http://orderfooduit.azurewebsites.net/";
    public static String getNhaHangById = BASE_URL + "api/NhaHang/GetID/";
    public static String searchMonAn = BASE_URL + "api/MonAn/GetByTimKiem/";
    public static String searchNhaHang = BASE_URL + "api/NhaHang/GetByTimKiem/";
    public static String getAllNhaHang = BASE_URL + "api/NhaHang/Get";
    public static String getChietKhauByMa = BASE_URL + "api/PhieuGiamGia/GetChietKhau/";
    public static String getByGiamGia = BASE_URL + "api/MonAn/GetByGiamGia";
    public static String getAllMonAnByMaLoai = BASE_URL + "api/MonAn/GetByMaLoai/";
    public static String getMonAnDatNhieuByMaLoai = BASE_URL + "api/MonAn/GetByMaLoaiDatNhieu/";
    public static String getAllPayment = BASE_URL + "api/DonDatHang/GetAllOrderKH/";
    public static String getAllMonAnByNhaHang = BASE_URL + "api/MonAn/GetByNhaHang/";
    public static String getAllMonAnDatNhieuByNhaHang = BASE_URL + "api/MonAn/GetByNhaHangDatNhieu/";
    public static String getHinhThucThanhToan = BASE_URL + "api/HinhThucThanhToan/Get";
    public static String getMonAnById = BASE_URL + "api/MonAn/GetId/";
    public static String tuVanTrucTuyen = BASE_URL + "api/TuVan/Post/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
