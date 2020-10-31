package com.nz.nztravelmate.model;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private String error = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    private String message = "";
    private List<City> city = new ArrayList<>();
}
