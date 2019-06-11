package com.carlisting.carlist.models;

import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Listing {

    @Id
    private String id = UUID.randomUUID().toString();

    private String dealer;
    private String code;
    private String kW;
    private String year;
    @Nullable
    private String color;
    private String price;
    private String make;
    private String model;

    public Listing() {
    }

    public Listing(String dealer, String code, String kW, String year, @Nullable String color, String price, String make, String model) {
        this.dealer = dealer;
        this.code = code;
        this.kW = kW;
        this.year = year;
        this.color = color;
        this.price = price;
        this.make = make;
        this.model = model;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getkW() {
        return kW;
    }

    public void setkW(String kW) {
        this.kW = kW;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
