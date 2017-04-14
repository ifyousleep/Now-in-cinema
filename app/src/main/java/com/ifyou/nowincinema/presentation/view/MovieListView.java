package com.ifyou.nowincinema.presentation.view;


import com.ifyou.nowincinema.model.dto.movies.ResultsItem;

import java.util.List;

public interface MovieListView extends BaseListView {

    void showResultsItemList(List<ResultsItem> resultsItemList);

}
