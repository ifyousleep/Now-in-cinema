package com.ifyou.nowincinema.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface PosterView extends MvpView {

    void hideProgressBar();

    void showPoster(String url);
}
