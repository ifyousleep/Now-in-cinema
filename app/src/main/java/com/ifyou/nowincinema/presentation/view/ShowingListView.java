package com.ifyou.nowincinema.presentation.view;

import com.ifyou.nowincinema.model.place.ResultsItem;

import java.util.List;

public interface ShowingListView extends BaseListView {

    void showResultsItemList(List<ResultsItem> resultsItemList);
}
