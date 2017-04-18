package com.ifyou.nowincinema.presentation.vo;

import java.io.Serializable;

/**
 * Created by Baranov on 14.04.2017.
 **/

public class PlaceObject implements Serializable {

    private String mName;
    private Double mLat;
    private Double mLon;
    private String mAddress;

    public PlaceObject(String name, Double lat, Double lon, String address) {
        mName = name;
        mLat = lat;
        mLon = lon;
        mAddress = address;
    }

    public String getAddress() {
        return mAddress;
    }

    public Double getLat() {
        return mLat;
    }

    public Double getLon() {
        return mLon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


}
