package com.nz.nztravelmate.model;

import java.io.Serializable;

public class City implements Serializable {
    public String getMap() {
        return map;
    }

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

}
