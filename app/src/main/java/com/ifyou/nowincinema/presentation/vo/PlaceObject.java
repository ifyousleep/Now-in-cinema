package com.ifyou.nowincinema.presentation.vo;

import java.io.Serializable;

/**
 * Created by Baranov on 14.04.2017.
 **/

public class PlaceObject implements Serializable {

    private String mName;

    public PlaceObject(String name){
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
