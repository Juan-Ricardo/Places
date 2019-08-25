package com.pe.places.retrofit.response.restaurant;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CustomerResponse implements Serializable {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<DataResponse> data;

    public CustomerResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataResponse> getData() {
        return data;
    }

    public void setData(List<DataResponse> data) {
        this.data = data;
    }
}
