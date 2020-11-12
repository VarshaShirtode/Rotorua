package com.nz.nztravelmate.model;

import java.io.Serializable;

public class Params implements Serializable {
    String city_id="";

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    String category_id="";
}
