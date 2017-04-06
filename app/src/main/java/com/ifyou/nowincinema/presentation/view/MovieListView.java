package com.ifyou.nowincinema.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.ifyou.nowincinema.model.ResultsItem;

import java.util.List;

public interface MovieListView extends MvpView {

    void showError(String error);

    void hideProgressBar();

    void showResultsItemList(List<ResultsItem> resultsItemList);

    void activateLastItemViewListener();

    void disableLastItemViewListener();

    void removeLastItem();
}
