package com.ifyou.nowincinema.presentation.mappers;

import com.ifyou.nowincinema.model.dto.showings.ResultsItem;
import com.ifyou.nowincinema.presentation.vo.Showings;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Baranov on 17.04.2017.
 **/

public class ShowingsMappers {

    public static List<Showings> fromResultsItemToShowing(List<ResultsItem> getResultsItems) {
        return Observable.fromIterable(getResultsItems)
                .map(showDTO -> new Showings(
                        showDTO.getId(),
                        showDTO.getMovie().getTitle(),
                        showDTO.getPlace().getTitle(),
                        showDTO.getMovie().getPoster().getImage(),
                        showDTO.getMovie().getId(),
                        showDTO.getPlace().getId(),
                        showDTO.getDatetime()))
                .toList()
                .toObservable()
                .blockingFirst();
    }
}
