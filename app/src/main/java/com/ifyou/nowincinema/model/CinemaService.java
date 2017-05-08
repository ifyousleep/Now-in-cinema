package com.ifyou.nowincinema.model;

import com.ifyou.nowincinema.app.CinemaApi;
import com.ifyou.nowincinema.model.dto.details.DetailsMovie;
import com.ifyou.nowincinema.model.dto.movies.ListMovies;
import com.ifyou.nowincinema.model.dto.movieshow.MovieShowList;
import com.ifyou.nowincinema.model.dto.showings.ShowingsList;

import io.reactivex.Observable;

/**
 * Created by Baranov on 06.04.2017.
 **/

public class CinemaService {

    private CinemaApi mCinemaApi;

    public CinemaService( CinemaApi cinemaApi) {
        mCinemaApi = cinemaApi;
    }

    public Observable<ListMovies> getMovieList(String actual, Integer page, String location) {
        return mCinemaApi.getMovies(actual, page, location);
    }

    public Observable<DetailsMovie> getAboutMovie(Integer id) {
        return mCinemaApi.getAboutMovie(id);
    }

    public Observable<ShowingsList> getShowings(String actual, String expand, Integer page, String location) {
        return mCinemaApi.getShowings(actual, expand, page, location);
    }

    public Observable<MovieShowList> getMovieShowings(Integer id, String actual, String expand, Integer page, String location) {
        return mCinemaApi.getMovieShowings(id, actual, expand, page, location);
    }
}
