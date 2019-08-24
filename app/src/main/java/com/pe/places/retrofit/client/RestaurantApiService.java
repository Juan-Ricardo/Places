package com.pe.places.retrofit.client;

import com.pe.places.retrofit.request.restaurant.CustomerRequest;
import com.pe.places.retrofit.response.restaurant.CustomerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestaurantApiService {

    @POST("/clientes")
    Call<CustomerRequest> customerCreate(@Body CustomerRequest customerRequest);

    @GET("/clientes")
    Call<CustomerResponse> getAllCustomers();


}
