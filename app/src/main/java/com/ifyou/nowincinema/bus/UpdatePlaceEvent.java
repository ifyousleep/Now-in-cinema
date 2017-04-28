package com.ifyou.nowincinema.bus;

/**
 * Created by Baranov on 27.04.2017.
 */

public class UpdatePlaceEvent {

    private String mCity;

    public UpdatePlaceEvent(String city) {
        mCity = city;
    }

    public String getCity() {
        return mCity;
    }
}
