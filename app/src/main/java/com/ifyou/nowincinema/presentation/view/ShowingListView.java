package com.ifyou.nowincinema.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.ifyou.nowincinema.model.place.ResultsItem;

import java.util.List;

public interface ShowingListView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    void hideProgressBar();

    void showResultsItemList(List<ResultsItem> resultsItemList);

    void activateLastItemViewListener();

    void disableLastItemViewListener();

    void removeLastItem();
}
