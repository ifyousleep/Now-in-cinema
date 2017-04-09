package com.ifyou.nowincinema.presentation.presenter;


import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.common.Utils;
import com.ifyou.nowincinema.model.CinemaService;
import com.ifyou.nowincinema.model.Movie;
import com.ifyou.nowincinema.presentation.view.DetailsView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ifyou.nowincinema.ui.fragment.TransitionObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    @Inject
    CinemaService mCinemaService;

    private boolean mIsInLoading;
    private Disposable subscription = Disposables.empty();

    public DetailsPresenter() {
        CinemaApp.getAppComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    public void loadData(TransitionObject transitionObject) {
        if (mIsInLoading) {
            return;
        }
        mIsInLoading = true;

        final Observable<Movie> observable = mCinemaService.getAboutMovie(transitionObject.getInteger());
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
        subscription = observable
                .compose(Utils.applySchedulers())
                .subscribe(resp -> {
                    onLoadingFinish();
                    onLoadingSuccess(resp);
                }, error -> {
                    onLoadingFinish();
                    onLoadingFailed(error);
                });
    }

    private void onLoadingSuccess(Movie response) {
        getViewState().showAbout(response);
    }

    private void onLoadingFailed(Throwable error) {
        getViewState().showError(error.toString());
    }

    private void onLoadingFinish() {
        mIsInLoading = false;
        getViewState().hideProgressBar();
    }
}
