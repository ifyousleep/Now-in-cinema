package com.ifyou.nowincinema.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface MainView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showDialog();

    @StateStrategyType(SkipStrategy.class)
    void restartApp(String city);

    @StateStrategyType(SkipStrategy.class)
    void initMap();
}
