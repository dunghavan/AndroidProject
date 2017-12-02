package com.example.dung.demo_recyclerview.retrofit;

import com.example.dung.demo_recyclerview.model.MonAn;
import com.example.dung.demo_recyclerview.model.NhaHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Call<NhaHang> getTenNhaHang(@Path("Id") String id);

    @GET("/api/MonAn/GetByTimKiem/{Key}")
    Call<List<MonAn>> searchMonAn(@Path("Key") String keyword);

    @GET("/api/NhaHang/GetByTimKiem/{Key}")
    Call<List<NhaHang>> searchNhaHang(@Path("Key") String keyword);

    @GET("/api/NhaHang/Get")
    Call<List<NhaHang>> getAllNhaHang();
}
