package com.ujjani.gamingpanda;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FileUploadService {

    @POST("intern_test")
    Call<ListResponse> ursend(
            @Body RequestBody url

    );



}



