package com.tickets.rzd.dto;

import java.util.List;

public class ServiceCategoriesDTO {
    private String typeCarLoc;
    private float freeSeats;
    private String price;
    private String priceMax;
    private float typeCarNumCode;
    private String typeCarCharCode;
    private String catLabelLoc;
    private boolean compoundCategory;
    private List<String> serviceClasses;

    public List<String> getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(List<String> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }

// Getter Methods

    public String getTypeCarLoc() {
        return typeCarLoc;
    }

    public float getFreeSeats() {
        return freeSeats;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public float getTypeCarNumCode() {
        return typeCarNumCode;
    }

    public String getTypeCarCharCode() {
        return typeCarCharCode;
    }

    public String getCatLabelLoc() {
        return catLabelLoc;
    }

    public boolean getCompoundCategory() {
        return compoundCategory;
    }

    // Setter Methods

    public void setTypeCarLoc(String typeCarLoc) {
        this.typeCarLoc = typeCarLoc;
    }

    public void setFreeSeats(float freeSeats) {
        this.freeSeats = freeSeats;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public void setTypeCarNumCode(float typeCarNumCode) {
        this.typeCarNumCode = typeCarNumCode;
    }

    public void setTypeCarCharCode(String typeCarCharCode) {
        this.typeCarCharCode = typeCarCharCode;
    }

    public void setCatLabelLoc(String catLabelLoc) {
        this.catLabelLoc = catLabelLoc;
    }

    public void setCompoundCategory(boolean compoundCategory) {
        this.compoundCategory = compoundCategory;
    }
}
