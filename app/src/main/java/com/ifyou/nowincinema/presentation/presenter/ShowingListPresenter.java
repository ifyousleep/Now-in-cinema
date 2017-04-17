package com.ifyou.nowincinema.presentation.presenter;


import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.common.Utils;
import com.ifyou.nowincinema.model.CinemaService;
import com.ifyou.nowincinema.model.dto.showings.ResultsItem;
import com.ifyou.nowincinema.model.dto.showings.ShowingsList;
import com.ifyou.nowincinema.presentation.mappers.ShowingsMappers;
import com.ifyou.nowincinema.presentation.view.ShowingListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ifyou.nowincinema.presentation.vo.Showings;
import com.ifyou.nowincinema.presentation.vo.PlaceObject;
import com.ifyou.nowincinema.ui.Screens;
import com.ifyou.nowincinema.ui.fragment.TransitionObject;

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
public class ShowingListPresenter extends MvpPresenter<ShowingListView> {

    @Inject
    CinemaService mCinemaService;

    private Router router;
    private boolean mIsInLoading;
    private Disposable subscription = Disposables.empty();
    private Integer mPage = 1;
    private Integer mCountList = 0;
    private List<Showings> mResultsItems = new ArrayList<>();
    private String mCity;
    private String mTime;

    public ShowingListPresenter(String city, Router router) {
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

        final Observable<ShowingsList> observable = mCinemaService.getShowings(mTime, "movie,place", page, mCity);
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

    private void onLoadingFinish() {
        mIsInLoading = false;
        getViewState().hideProgressBar();
    }

    private void onLoadingSuccess(ShowingsList response) {
        List<ResultsItem> resultsItems = response.getResults();
        List<Showings> showingsList = ShowingsMappers.fromResultsItemToShowing(resultsItems);
        mResultsItems.addAll(showingsList);
        getViewState().showResultsItemList(showingsList);
        getViewState().activateLastItemViewListener();
    }

    private void onLoadingFailed(Throwable error) {
        getViewState().showError(error.toString());
        getViewState().removeLastItem();
    }

    private boolean isNotEndOfList() {
        return (mResultsItems == null || mResultsItems.size() < mCountList);
    }

    public void clickItem(TransitionObject transitionObject) {
        transitionObject.setUrl(mResultsItems.get(transitionObject.getInteger()).getPosterUrl());
        transitionObject.setInteger(mResultsItems.get(transitionObject.getInteger()).getMovieId());
        Timber.d("ID = " + transitionObject.getInteger());
        router.navigateTo(Screens.DETAILS_SCREEN, transitionObject);
    }

    public void clickPlace(Integer pos) {
        String name = mResultsItems.get(pos).getPlaceTitle();
        PlaceObject placeObject = new PlaceObject(name);
        router.navigateTo(Screens.PLACE_SCREEN, placeObject);
    }

    public void onBackPressed() {
        router.exit();
    }
}
