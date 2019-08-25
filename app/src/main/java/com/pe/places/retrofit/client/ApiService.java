package com.pe.places.retrofit.client;

import com.pe.places.retrofit.response.typicode.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/users")
    Call<List<UserResponse>> getAllUsers();

    @GET("/photos")
    Call<List<UserResponse>> getAllPhotos();
}
