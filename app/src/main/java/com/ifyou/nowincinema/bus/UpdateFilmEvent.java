package com.ifyou.nowincinema.bus;

/**
 * Created by Baranov on 26.04.2017.
 **/

public class UpdateFilmEvent {

    private String mCity;

    public UpdateFilmEvent(String city) {
        mCity = city;
    }

    public String getCity() {
        return mCity;
    }
}
