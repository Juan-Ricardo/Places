package com.pe.places.retrofit.client;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private Retrofit retrofit;
    private static ApiClient INSTANCE;
    public ApiClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
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
