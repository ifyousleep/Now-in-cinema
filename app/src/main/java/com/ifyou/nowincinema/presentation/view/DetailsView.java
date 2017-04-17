package com.ifyou.nowincinema.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.ifyou.nowincinema.presentation.vo.Details;

public interface DetailsView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    void showAbout(Details detailsMovie);

    void hideProgressBar();

    void showPoster(String url);
}
