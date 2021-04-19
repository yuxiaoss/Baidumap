package com.imooc.baidumap0924.bean;

import java.io.Serializable;

/**
 * Created by hyman for imooc.com.
 */

public class AddressInfo implements Serializable {

    private double latitude;
    private double longitude ;
    private int imgId;
    private String name;
    private String distance;

    public AddressInfo(){

    }

    public AddressInfo(double latitude, double longitude, int imgId, String name, String distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
