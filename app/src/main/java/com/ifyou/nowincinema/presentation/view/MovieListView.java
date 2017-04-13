package com.ifyou.nowincinema.presentation.view;

import com.ifyou.nowincinema.model.film.ResultsItem;

import java.util.List;

public interface MovieListView extends BaseListView {

    void showResultsItemList(List<ResultsItem> resultsItemList);

}
