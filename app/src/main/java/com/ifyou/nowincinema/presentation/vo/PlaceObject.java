package com.ifyou.nowincinema.presentation.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Baranov on 14.04.2017.
 **/

public class PlaceObject implements Parcelable {

    private String mName;
    private final Double mLat;
    private final Double mLon;
    private final String mAddress;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mAddress);
        dest.writeDouble(this.mLat);
        dest.writeDouble(this.mLon);
    }

    protected PlaceObject(Parcel in) {
        this.mName = in.readString();
        this.mAddress = in.readString();
        this.mLat = in.readDouble();
        this.mLon = in.readDouble();
    }

    public static final Parcelable.Creator<PlaceObject> CREATOR = new Parcelable.Creator<PlaceObject>() {
        @Override
        public PlaceObject createFromParcel(Parcel source) {
            return new PlaceObject(source);
        }

        @Override
        public PlaceObject[] newArray(int size) {
            return new PlaceObject[size];
        }
    };
}
