package com.ifyou.nowincinema.presentation.vo;

/**
 * Created by Baranov on 15.04.2017.
 **/

public class Movies {

    private String mTitle;
    private String mPosterUrl;
    private Integer mId;

    public Movies(String title, String posterUrl, Integer id) {
        mTitle = title;
        mPosterUrl = posterUrl;
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public Integer getId() {
        return mId;
    }

}
