package com.nz.nztravelmate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {
    String id="";
    String name="";
    String phone="";
    String email="";
    String business_name_english="";
    String business_name_mandarin="";
    String address="";
    String city_id="";
    String category_id="";

    String avg_time="";
    String avg_doller="";
    String business_hrs="";
    String wifi="";
    String map="";
    private List<Payment> payment_id = new ArrayList<>();

    public List<Payment> getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(List<Payment> payment_id) {
        this.payment_id = payment_id;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    String website="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusiness_name_english() {
        return business_name_english;
    }

    public void setBusiness_name_english(String business_name_english) {
        this.business_name_english = business_name_english;
    }

    public String getBusiness_name_mandarin() {
        return business_name_mandarin;
    }

    public void setBusiness_name_mandarin(String business_name_mandarin) {
        this.business_name_mandarin = business_name_mandarin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getAvg_time() {
        return avg_time;
    }

    public void setAvg_time(String avg_time) {
        this.avg_time = avg_time;
    }

    public String getAvg_doller() {
        return avg_doller;
    }

    public void setAvg_doller(String avg_doller) {
        this.avg_doller = avg_doller;
    }

    public String getBusiness_hrs() {
        return business_hrs;
    }

    public void setBusiness_hrs(String business_hrs) {
        this.business_hrs = business_hrs;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getMandarin_speaking_staff() {
        return mandarin_speaking_staff;
    }

    public void setMandarin_speaking_staff(String mandarin_speaking_staff) {
        this.mandarin_speaking_staff = mandarin_speaking_staff;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    String parking="";

    String mandarin_speaking_staff="";
    String payment="";
    String logo="";
    String images="";
    String description="";
    String created_at="";
    String updated_at="";

}
