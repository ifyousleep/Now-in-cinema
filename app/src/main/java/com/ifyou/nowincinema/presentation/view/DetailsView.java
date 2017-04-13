package com.ifyou.nowincinema.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.ifyou.nowincinema.model.film.Movie;

public interface DetailsView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    void showAbout(Movie movie);

    void hideProgressBar();

    void showPoster(String url);
}
