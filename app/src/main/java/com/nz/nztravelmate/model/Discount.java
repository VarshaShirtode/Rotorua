package com.nz.nztravelmate.model;

import java.io.Serializable;

public class Discount implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    String name="";
String discount="";
}
