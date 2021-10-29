package com.example.ownit;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface JsonPlaceholderAPI {
    @GET("advertisements")
    Call<List<Advertisement>> getAdvertisements();

    @POST("register")
    Call<User> register(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);

    @Multipart
    @POST("advertisement/create")
    Call<Advertisement> createAdv(@Part("userid") RequestBody userid,
                                  @Part("title") RequestBody title,
                                  @Part("description") RequestBody description,
                                  @Part("category") RequestBody category,
                                  @Part MultipartBody.Part photo,
                                  @Part("price") RequestBody price
                                  );


 }
