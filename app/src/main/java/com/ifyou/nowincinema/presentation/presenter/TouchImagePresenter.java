package com.ifyou.nowincinema.presentation.presenter;


import com.ifyou.nowincinema.presentation.view.TouchImageView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class TouchImagePresenter extends MvpPresenter<TouchImageView> {

    public void setImage(String url) {
        getViewState().setImage(url);
    }

    public void hideProgressBar() {
        getViewState().hideProgressBar();
    }
}
