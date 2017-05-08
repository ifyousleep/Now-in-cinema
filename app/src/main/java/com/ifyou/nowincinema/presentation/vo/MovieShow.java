package com.ifyou.nowincinema.presentation.vo;

/**
 * Created by Baranov on 05.05.2017 to Now-in-cinema.
 **/

public class MovieShow {

    private final Integer mId;
    private final Integer mPlaceId;
    private final String mPlaceTitle;
    private final Integer mTime;
    private final Double mLat;
    private final Double mLon;
    private final String mAddress;

    public MovieShow(Integer id, String placeTitle, Integer placeId,
                     Integer time, Double lat, Double lon, String address) {
        mId = id;
        mPlaceTitle = placeTitle;
        mPlaceId = placeId;
        mTime = time;
        mLat = lat;
        mLon = lon;
        mAddress = address;
    }

    public Integer getPlaceId() {
        return mPlaceId;
    }

    public String getPlaceTitle() {
        return mPlaceTitle;
    }

    public Integer getTime() {
        return mTime;
    }

    public Double getLat() {
        return mLat;
    }

    public Double getLon() {
        return mLon;
    }

    public String getAddress() {
        return mAddress;
    }

    public Integer getId() {
        return mId;
    }
}
