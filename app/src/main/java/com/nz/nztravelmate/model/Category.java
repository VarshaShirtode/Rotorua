package com.nz.nztravelmate.model;

import java.io.Serializable;

public class Category implements Serializable {
    String name="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id="";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
