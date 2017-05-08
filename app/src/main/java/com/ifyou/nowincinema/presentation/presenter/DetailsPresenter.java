package com.ifyou.nowincinema.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.common.Utils;
import com.ifyou.nowincinema.model.CinemaService;
import com.ifyou.nowincinema.model.dto.details.DetailsMovie;
import com.ifyou.nowincinema.presentation.mappers.DetailsMapper;
import com.ifyou.nowincinema.presentation.view.DetailsView;
import com.ifyou.nowincinema.presentation.vo.Details;
import com.ifyou.nowincinema.ui.Screens;
import com.ifyou.nowincinema.ui.fragment.TransitionObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    @Inject
    CinemaService mCinemaService;
    @Inject
    DetailsMapper mDetailsMapper;

    private Router router;
    private boolean mIsInLoading;
    private Integer mId;
    private Disposable subscription = Disposables.empty();

    public DetailsPresenter(Router router, Integer id) {
        CinemaApp.getAppComponent().inject(this);
        this.router = router;
        mId = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        showPoster();
        loadData(mId);
    }

    public void showTouch(TransitionObject transitionObject) {
        router.navigateTo(Screens.POSTER_SCREEN, transitionObject);
    }

    public void showMovie(TransitionObject transitionObject) {
        router.navigateTo(Screens.MOVIE_SHOWING, transitionObject);
    }

    @Override
    public void onDestroy() {
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    private void loadData(Integer id) {
        if (mIsInLoading) {
            return;
        }
        mIsInLoading = true;
        final Observable<DetailsMovie> observable = mCinemaService.getAboutMovie(id);
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
        subscription = observable
                .compose(Utils.applySchedulers())
                .map(mDetailsMapper)
                .subscribe(resp -> {
                    onLoadingFinish();
                    onLoadingSuccess(resp);
                }, error -> {
                    onLoadingFinish();
                    onLoadingFailed(error);
                });
    }

    private void onLoadingSuccess(Details response) {
        getViewState().showAbout(response);
    }

    private void onLoadingFailed(Throwable error) {
        getViewState().showError(error.toString());
    }

    private void onLoadingFinish() {
        mIsInLoading = false;
        getViewState().hideProgressBar();
    }

    private void showPoster() {
        getViewState().showPoster();
    }

    public void onBackPressed() {
        router.exit();
    }
}
