package com.nz.nztravelmate.model;

import java.io.Serializable;

public class Food implements Serializable {
    String logo="";
    String Name="";

    public String getWeekTime() {
        return weekTime;
    }

    public void setWeekTime(String weekTime) {
        this.weekTime = weekTime;
    }

    public String getWeekendTime() {
        return weekendTime;
    }

    public void setWeekendTime(String weekendTime) {
        this.weekendTime = weekendTime;
    }

    String weekTime="";
    String weekendTime="";

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    String address="";
    String distance="";
    String open="";
    String close="";
    String service="";
}
