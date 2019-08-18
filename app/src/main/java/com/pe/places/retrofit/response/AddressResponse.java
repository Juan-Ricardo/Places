package com.pe.places.retrofit.response;

public class AddressResponse {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoResponse geo;

    public AddressResponse() {

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public GeoResponse getGeo() {
        return geo;
    }

    public void setGeo(GeoResponse geo) {
        this.geo = geo;
    }
}
