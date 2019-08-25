package com.pe.places.retrofit.client;

import android.content.Context;

import com.pe.places.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//https://drive.google.com/file/d/1-Qg3Av0RG4O3oen-62YwDDaoHI-9TkaQ/view?usp=sharing
public class ApiClient {
    private Retrofit retrofit;
    private static ApiClient INSTANCE;
    public ApiClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
               .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static ApiClient getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new ApiClient(context);
        return INSTANCE;
    }

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
