package com.example.dung.demo_recyclerview.retrofit;

/**
 * Created by Dung on 12/2/2017.
 */

public class ApiUtils {

    private ApiUtils() {}
    public static final String BASE_URL = "http://orderfooduit.azurewebsites.net/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
