package com.ifyou.nowincinema.presentation.view;

import com.ifyou.nowincinema.presentation.vo.Showings;

import java.util.List;

public interface ShowingListView extends BaseListView {

    void showResultsItemList(List<Showings> resultsItemList);
}
