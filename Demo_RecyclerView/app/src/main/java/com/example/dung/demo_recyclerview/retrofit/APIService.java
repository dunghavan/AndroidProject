package com.example.dung.demo_recyclerview.retrofit;

import com.example.dung.demo_recyclerview.model.HinhThucThanhToan;
import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.model.Payment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Dung on 12/2/2017.
 */

public interface APIService {
    @POST("/posts")
    @FormUrlEncoded
    Call<MonAn> savePost(@Field("title") String title,
                         @Field("body") String body,
                         @Field("userId") long userId);

    @GET("/api/NhaHang/GetID/{Id}")
    Call<NhaHang> getNhaHangById(@Path("Id") String id);

    @GET("/api/MonAn/GetByTimKiem/{Key}")
    Call<List<MonAn>> searchMonAn(@Path("Key") String keyword);

    @GET("/api/NhaHang/GetByTimKiem/{Key}")
    Call<List<NhaHang>> searchNhaHang(@Path("Key") String keyword);

    @GET("/api/NhaHang/Get")
    Call<List<NhaHang>> getAllNhaHang();

    @FormUrlEncoded
    @POST("/api/DonDatHang/Post")
    Call<Void> submitOrder(@Field("MaKhachHang")String MaKhachHang,
                     @Field("NgayThang") String NgayThang,
                     @Field("NgayGioGiao")String NgayGioGiao,
                     @Field("DiaChiGiao")String DiaChiGiao,
                     @Field("SoDienThoai")String SoDienThoai,
                     @Field("HinhThucThanhToan") String HinhThucThanhToan,
                           @Field("PayID") String PayID,
                     @Field("TongTien") int TongTien,
                     @Field("CTDonDatHang")String ChiTietDonHang,
                     @Field("DaXacNhan")String DaXacNhan,
                     @Field("DaGiaoDich")String DaGiaoDich,
                        @Field("MaNhaHang")String MaNhaHang);


    @GET("/api/PhieuGiamGia/GetChietKhau/{idMonAn}")
    Call<Integer> getChietKhauByMa(@Path("idMonAn")String id);

    @GET("/api/MonAn/GetByGiamGia")
    Call<List<MonAn>> getByGiamGia();

    @GET("api/MonAn/GetByMaLoai/{maLoai}")
    Call<List<MonAn>> getAllMonAnByMaLoai(@Path("maLoai") Integer maLoai); //

    @GET("api/MonAn/GetByMaLoaiDatNhieu/{maLoai}")
    Call<List<MonAn>> getMonAnDatNhieuByMaLoai(@Path("maLoai") Integer maLoai);

    @GET("api/DonDatHang/GetAllOrderKH/{maKhachHang}")
    Call<List<Payment>> getAllPayment(@Path("maKhachHang")String maKhachHang);

    @FormUrlEncoded
    @POST("api/NutritionConsulting/Post")
    Call<List<MonAn>> getMonAnDeXuat(@Field("GioiTinh")int GioiTinh,
                                     @Field("CanNang")int CanNang,
                                     @Field("NamSinh")int NamSinh,
                                     @Field("CheDo")int CheDo,
                                     @Field("NhuCau")int NhuCau,
                                     @Field("CheDoLaoDong")int CheDoLaoDong,
                                     @Field("Bua")int Bua);

    @FormUrlEncoded
    @POST("api/TuVan/Post/")
//    Call<Void> tuVanTrucTuyen(@Header("Authorization")String amxString,
//                              @Field("HoTen")String hoTen,
//                                     @Field("SoDienThoai")String soDienThoai,
//                                     @Field("Email")String email,
//                                     @Field("TinNhan")String message);

    Call<Void> tuVanTrucTuyen(@Field("HoTen")String hoTen,
                                     @Field("SoDT")String soDienThoai,
                                     @Field("Email")String email,
                                     @Field("TinNhan")String message);

    @POST("api/MonAn/DanhGia/{point}/{maMonAn}")
    Call<Void> danhgiaMonAn(@Path("point")Float point, @Path("maMonAn")String maMonAn);

    @GET("api/MonAn/GetByNhaHang/{MaNhaHang}")
    Call<List<MonAn>> getAllMonAnByNhaHang(@Path("MaNhaHang")String MaNhaHang);

    @GET("api/MonAn/GetByNhaHangDatNhieu/{MaNhaHang}")
    Call<List<MonAn>> getAllMonAnDatNhieuByNhaHang(@Path("MaNhaHang")String MaNhaHang);

    @FormUrlEncoded
    @POST("api/KhachHang/Post")
    Call<Void> guiThongTinKhachHang(@Field("HoTen")String hoTen,
                                    @Field("Email")String email,
                                    @Field("Id")String id);

    @GET("api/HinhThucThanhToan/Get")
    Call<List<HinhThucThanhToan>> getHinhThucThanhToan(@Header("Authorization")String amxString);

    @GET("api/MonAn/GetId/{id}")
    Call<MonAn> getMonAnById(@Header("Authorization")String amxString
                                                , @Path("id") String id);
}
