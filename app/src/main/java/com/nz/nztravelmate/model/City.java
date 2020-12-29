package com.nz.nztravelmate.model;

import java.io.Serializable;
import java.util.ArrayList;

public class City implements Serializable {
    public String getMap() {
        return map;
    }

    public String getPanoramic_image() {
        return panoramic_image;
    }

    public void setPanoramic_image(String panoramic_image) {
        this.panoramic_image = panoramic_image;
    }

    String panoramic_image="";

    public void setMap(String map) {
        this.map = map;
    }

    String map="";
    String name="";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description="";

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

    public ArrayList<CityDetails> getCity_details() {
        return city_details;
    }

    public void setCity_details(ArrayList<CityDetails> city_details) {
        this.city_details = city_details;
    }

    ArrayList<CityDetails> city_details=new ArrayList<CityDetails>();
}
