package com.ifyou.nowincinema.presentation.mappers;

import com.ifyou.nowincinema.model.dto.details.DetailsMovie;
import com.ifyou.nowincinema.presentation.vo.Details;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Baranov on 17.04.2017.
 **/

public class DetailsMapper implements Function<DetailsMovie, Details> {

    @Inject
    public DetailsMapper() {

    }

    @Override
    public Details apply(DetailsMovie detailsMovie) {
        return Observable.just(detailsMovie)
                .map(dMovie -> new Details(dMovie.getBody_text(), dMovie.getPoster().getImage(),
                        dMovie.getTitle(), dMovie.getYear(), dMovie.getImdb_rating(),
                        dMovie.getDirector(), dMovie.getStars(), dMovie.getCountry()))
                .blockingFirst();
    }
}