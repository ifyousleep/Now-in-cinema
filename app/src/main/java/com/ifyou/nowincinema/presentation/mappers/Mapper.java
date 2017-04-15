package com.ifyou.nowincinema.presentation.mappers;

import com.ifyou.nowincinema.model.dto.movies.ResultsItem;
import com.ifyou.nowincinema.presentation.vo.Movies;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Baranov on 15.04.2017.
 **/

public class Mapper {

    public static List<Movies> fromResultsItemToMovies(List<ResultsItem> getResultsItems) {
        return Observable.fromIterable(getResultsItems)
                .map(movieDTO -> new Movies(movieDTO.getTitle(), movieDTO.getPoster().getImage(), movieDTO.getId()))
                .toList()
                .toObservable()
                .blockingFirst();
    }
}

/*
public class Mapper implements Function<List<ResultsItem>, List<Movies>> {

    @Inject
    public Mapper() {

    }

    @Override
    public List<Movies> apply (List<ResultsItem> moviesDTOs) {
        return Observable.fromIterable(moviesDTOs)
                .map(movieDTO -> new Movies(movieDTO.getTitle(), movieDTO.getPoster().getImage()))
                .toList()
                .toObservable()
                .blockingFirst();
    }
}
*/
