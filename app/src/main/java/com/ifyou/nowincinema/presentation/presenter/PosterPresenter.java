package com.ifyou.nowincinema.presentation.presenter;


import com.ifyou.nowincinema.presentation.view.PosterView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class PosterPresenter extends MvpPresenter<PosterView> {

    public void hideProgressBar() {
        getViewState().hideProgressBar();
    }

    public void showPoster(String url) {
        getViewState().showPoster(url);
    }

}
