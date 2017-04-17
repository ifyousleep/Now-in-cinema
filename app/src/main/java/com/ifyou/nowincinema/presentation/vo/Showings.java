package com.ifyou.nowincinema.presentation.vo;

/**
 * Created by Baranov on 17.04.2017.
 **/

public class Showings {

    private Integer mId;
    private String mMovieTitle;
    private String mPlaceTitle;
    private String mPosterUrl;
    private Integer mMovieId;
    private Integer mPlaceId;
    private Integer mTime;

    public Showings(Integer id, String movieTitle, String placeTitle, String posterUrl, Integer movieId, Integer placeId, Integer time) {
        mId = id;
        mMovieTitle = movieTitle;
        mPlaceTitle = placeTitle;
        mPosterUrl = posterUrl;
        mMovieId = movieId;
        mPlaceId = placeId;
        mTime = time;
    }

    public Integer getId() {
        return mId;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public String getPlaceTitle() {
        return mPlaceTitle;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public Integer getMovieId() {
        return mMovieId;
    }

    public Integer getPlaceId() {
        return mPlaceId;
    }

    public Integer getTime() {
        return mTime;
    }
}
