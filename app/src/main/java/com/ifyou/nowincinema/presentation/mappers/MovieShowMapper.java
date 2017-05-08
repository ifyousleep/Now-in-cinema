package com.ifyou.nowincinema.presentation.mappers;

import com.ifyou.nowincinema.model.dto.movieshow.ResultsItem;
import com.ifyou.nowincinema.presentation.vo.MovieShow;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Baranov on 05.05.2017 to Now-in-cinema.
 **/

public class MovieShowMapper {

    public static List<MovieShow> fromResultsItemToMovieShow(List<ResultsItem> getResultsItems) {
        return Observable.fromIterable(getResultsItems)
                .map(showDTO -> new MovieShow(
                        showDTO.getId(),
                        showDTO.getPlace().getTitle(),
                        showDTO.getPlace().getId(),
                        showDTO.getDatetime(),
                        showDTO.getPlace().getCoords().getLat(),
                        showDTO.getPlace().getCoords().getLon(),
                        showDTO.getPlace().getAddress()))
                .toList()
                .toObservable()
                .blockingFirst();
    }
}
