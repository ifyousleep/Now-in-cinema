package com.ifyou.nowincinema.model;

import com.ifyou.nowincinema.app.CinemaApi;

import io.reactivex.Observable;

/**
 * Created by Baranov on 06.04.2017.
 **/

public class CinemaService {

    private CinemaApi mCinemaApi;

    public CinemaService( CinemaApi cinemaApi) {
        mCinemaApi = cinemaApi;
    }

    public Observable<Response> getMovieList(String actual, Integer page, String location) {
        return mCinemaApi.getMovies(actual, page, location);
    }

    public Observable<Movie> getAboutMovie(Integer id) {
        return mCinemaApi.getAboutMovie(id);
    }
}
