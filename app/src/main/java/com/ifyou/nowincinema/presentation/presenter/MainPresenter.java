package com.ifyou.nowincinema.presentation.presenter;

import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.presentation.view.MainView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ifyou.nowincinema.ui.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    Router router;

    public MainPresenter() {
        CinemaApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initMap();
    }

    public void showMenuPlace() {
        router.replaceScreen(Screens.PLACE_CONTAINER_SCREEN);
    }

    public void showMenuFilm() {
        router.replaceScreen(Screens.FILM_CONTAINER_SCREEN);
    }

    public void onBackPressed() {
        router.exit();
    }

    public void showDialog() {
        getViewState().showDialog();
    }

    public void updateCity(String city) {
        getViewState().restartApp(city);
    }

}
