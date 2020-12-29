package com.nz.nztravelmate.model;

import java.io.Serializable;

public class Maps implements Serializable {
    String map="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name="";

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    String category_id="";
}
