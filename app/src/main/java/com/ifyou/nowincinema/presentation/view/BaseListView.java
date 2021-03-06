package com.ifyou.nowincinema.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Baranov on 13.04.2017.
 **/

public interface BaseListView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideProgressBar();

    void activateLastItemViewListener();

    void disableLastItemViewListener();

    void removeLastItem();
}