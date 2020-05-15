package com.example.bluejackkoslab.model;

public class Item {
    public String name,facilities,image,address;
    public int id, price;
    public double LAT,LNG;

    public Item() {

    }

    public Item(int id, String name, String image, String facilities, int price, String address, long LAT, long LNG) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.facilities = facilities;
        this.price = price;
        this.address = address;
        this.LAT = LAT;
        this.LNG = LNG;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLNG() {
        return LNG;
    }

    public void setLNG(double LNG) {
        this.LNG = LNG;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
