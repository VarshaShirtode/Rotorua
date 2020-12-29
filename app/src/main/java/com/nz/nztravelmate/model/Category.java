package com.nz.nztravelmate.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    public String getBussiness_id() {
        return bussiness_id;
    }

    public void setBussiness_id(String bussiness_id) {
        this.bussiness_id = bussiness_id;
    }

    String bussiness_id="";
    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    String banner="";
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

    public ArrayList<CategoryDetails> getCategory_details() {
        return category_details;
    }

    public void setCategory_details(ArrayList<CategoryDetails> category_details) {
        this.category_details = category_details;
    }

    ArrayList<CategoryDetails> category_details=new ArrayList();

}
