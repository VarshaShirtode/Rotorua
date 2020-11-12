package com.nz.nztravelmate.model;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private String error = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status = "";

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


    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    private List<Category> category = new ArrayList<>();

    public List<Data> getData() {
        return Data;
    }

    public void setData(List<Data> data) {
        Data = data;
    }

    private List<Data> Data = new ArrayList<>();
}
