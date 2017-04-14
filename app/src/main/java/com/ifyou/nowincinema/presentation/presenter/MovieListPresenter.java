package com.ifyou.nowincinema.presentation.presenter;

import com.ifyou.nowincinema.model.dto.movies.ListMovies;
import com.ifyou.nowincinema.model.dto.movies.ResultsItem;
import com.ifyou.nowincinema.ui.fragment.TransitionObject;
import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.common.Utils;
import com.ifyou.nowincinema.model.CinemaService;
import com.ifyou.nowincinema.presentation.view.MovieListView;
import com.ifyou.nowincinema.ui.Screens;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class MovieListPresenter extends MvpPresenter<MovieListView> {

    @Inject
    CinemaService mCinemaService;

    private Router router;
    private boolean mIsInLoading;
    private Disposable subscription = Disposables.empty();
    private Integer mPage = 1;
    private Integer mCountList = 0;
    private List<ResultsItem> mResultsItems = new ArrayList<>();
    private String mTime;
    private String mCity;

    public MovieListPresenter(String city, Router router) {
        CinemaApp.getAppComponent().inject(this);
        mCity = city;
        this.router = router;
    }

    @Override
    public void onDestroy() {
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mTime = Utils.getUnixTimestamp();
        loadData(mPage);
        Timber.d(mCity);
    }

    public void onLastItemViewed() {
        mPage += 1;
        getViewState().disableLastItemViewListener();
        if (isNotEndOfList()) {
            loadData(mPage);
        } else
            getViewState().removeLastItem();
    }

    private void loadData(int page) {
        if (mIsInLoading) {
            return;
        }
        mIsInLoading = true;

        final Observable<ListMovies> observable = mCinemaService.getMovieList(mTime, page, mCity);
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
        subscription = observable
                .compose(Utils.applySchedulers())
                .retryWhen(errors ->
                        errors
                                .zipWith(
                                        Observable.range(1, 3), (n, i) -> i)
                                .flatMap(
                                        retryCount -> Observable.timer(retryCount,
                                                TimeUnit.SECONDS)))
                .subscribe(resp -> {
                    mCountList = resp.getCount() - 1;
                    onLoadingFinish();
                    onLoadingSuccess(resp);
                }, error -> {
                    onLoadingFinish();
                    onLoadingFailed(error);
                });
    }

    public void clickItem(TransitionObject transitionObject) {
        transitionObject.setUrl(mResultsItems.get(transitionObject.getInteger()).getPoster().getImage());
        transitionObject.setInteger(mResultsItems.get(transitionObject.getInteger()).getId());
        Timber.d("ID = " + transitionObject.getInteger());
        router.navigateTo(Screens.DETAILS_SCREEN, transitionObject);
    }

    private void onLoadingFinish() {
        mIsInLoading = false;
        getViewState().hideProgressBar();
    }

    private void onLoadingSuccess(ListMovies listMovies) {
        List<ResultsItem> resultsItems;
        resultsItems = listMovies.getResults();
        mResultsItems.addAll(resultsItems);
        getViewState().showResultsItemList(resultsItems);
        getViewState().activateLastItemViewListener();
    }

    private void onLoadingFailed(Throwable error) {
        getViewState().showError(error.toString());
        getViewState().removeLastItem();
    }

    private boolean isNotEndOfList() {
        return (mResultsItems == null || mResultsItems.size() < mCountList);
    }

    public void onBackPressed() {
        router.exit();
    }
}
