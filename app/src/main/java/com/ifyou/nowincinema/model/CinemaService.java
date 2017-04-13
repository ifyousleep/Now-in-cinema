package com.ifyou.nowincinema.model;

import com.ifyou.nowincinema.app.CinemaApi;
import com.ifyou.nowincinema.model.film.Movie;
import com.ifyou.nowincinema.model.film.Response;
import com.ifyou.nowincinema.model.place.Showing;

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

    public Observable<Showing> getShowings(String actual, String expand, Integer page, String location) {
        return mCinemaApi.getShowings(actual, expand, page, location);
    }
}
