package com.nz.nztravelmate.model;

import java.io.Serializable;

public class Payment implements Serializable {
    String id="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayment_method_name() {
        return payment_method_name;
    }

    public void setPayment_method_name(String payment_method_name) {
        this.payment_method_name = payment_method_name;
    }

    String payment_method_name="";
}
