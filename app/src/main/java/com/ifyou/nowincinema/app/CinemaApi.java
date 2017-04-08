package com.ifyou.nowincinema.app;

import com.ifyou.nowincinema.model.Movie;
import com.ifyou.nowincinema.model.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Baranov on 06.04.2017.
 **/

public interface CinemaApi {

    @GET("/public-api/v1.3/movies/")
    Observable<Response> getMovies(@Query("actual_since") String actual_since,
                                   @Query("page") Integer page);

    @GET("/public-api/v1.3/movies/{movie_id}/")
    Observable<Movie> getAboutMovie(@Path("movie_id") Integer movie_id);

}
