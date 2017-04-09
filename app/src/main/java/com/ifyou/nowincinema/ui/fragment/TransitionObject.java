package com.ifyou.nowincinema.ui.fragment;

import android.view.View;

import java.io.Serializable;

/**
 * Created by Baranov on 08.04.2017.
 **/

public class TransitionObject implements Serializable {

    private View view;
    private Integer integer;
    private String url;

    TransitionObject(View view, Integer integer, String url) {
        this.view = view;
        this.integer = integer;
        this.url = url;

    }

    public View getView() {
        return view;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
