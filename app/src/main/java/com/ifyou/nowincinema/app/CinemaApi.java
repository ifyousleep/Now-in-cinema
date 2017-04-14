package com.ifyou.nowincinema.app;

import com.ifyou.nowincinema.model.dto.details.DetailsMovie;
import com.ifyou.nowincinema.model.dto.movies.ListMovies;
import com.ifyou.nowincinema.model.dto.showings.ShowingsList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Baranov on 06.04.2017.
 **/

public interface CinemaApi {

    @GET("/public-api/v1.3/movies/")
    Observable<ListMovies> getMovies(@Query("actual_since") String actual_since,
                                     @Query("page") Integer page,
                                     @Query("location") String location);

    @GET("/public-api/v1.3/movies/{movie_id}/")
    Observable<DetailsMovie> getAboutMovie(@Path("movie_id") Integer movie_id);

    @GET("/public-api/v1.3/movie-showings/")
    Observable<ShowingsList> getShowings(@Query("actual_since") String actual_since,
                                         @Query("expand") String expand,
                                         @Query("page") Integer page,
                                         @Query("location") String location);

}
