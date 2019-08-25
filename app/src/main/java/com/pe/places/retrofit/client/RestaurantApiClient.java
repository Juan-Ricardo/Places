package com.pe.places.retrofit.client;

import android.content.Context;

import com.pe.places.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantApiClient {
    private Retrofit retrofit;
    private static RestaurantApiClient INSTANCE;

    public RestaurantApiClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RestaurantApiClient getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new RestaurantApiClient(context);
        return INSTANCE;
    }

    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
