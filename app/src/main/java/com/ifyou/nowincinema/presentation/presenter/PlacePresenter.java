package com.ifyou.nowincinema.presentation.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ifyou.nowincinema.presentation.view.PlaceView;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class PlacePresenter extends MvpPresenter<PlaceView> {

    private Router router;

    public PlacePresenter(Router router) {
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onBackPressed() {
        router.exit();
    }
}
