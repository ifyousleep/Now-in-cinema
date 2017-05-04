package com.ifyou.nowincinema.presentation.vo;

/**
 * Created by Baranov on 17.04.2017.
 **/

public class Details {

    private final String mAbout;
    private final String mPosterUrl;
    private final String mTitle;
    private final Integer mYear;
    private final Double mImdb;
    private final String mDirector;
    private final String mStars;
    private final String mCountry;

    public Details(String about, String posterUrl, String title, Integer year,
                   Double imdb, String director, String stars, String country) {
        mAbout = about;
        mPosterUrl = posterUrl;
        mTitle = title;
        mYear = year;
        mImdb = imdb;
        mDirector = director;
        mStars = stars;
        mCountry = country;
    }

    public String getAbout() {
        return mAbout;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public Integer getYear() {
        return mYear;
    }

    public Double getImdb() {
        return mImdb;
    }

    public String getDirector() {
        return mDirector;
    }

    public String getStars() {
        return mStars;
    }

    public String getCountry() {
        return mCountry;
    }
}
