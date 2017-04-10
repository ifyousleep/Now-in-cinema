package com.ifyou.nowincinema.presentation.presenter;

import com.ifyou.nowincinema.ui.fragment.TransitionObject;
import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.common.Utils;
import com.ifyou.nowincinema.model.CinemaService;
import com.ifyou.nowincinema.model.Response;
import com.ifyou.nowincinema.model.ResultsItem;
import com.ifyou.nowincinema.presentation.view.MovieListView;
import com.ifyou.nowincinema.ui.Screens;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

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

    @Inject
    Router router;

    private boolean mIsInLoading;
    private Disposable subscription = Disposables.empty();
    private Integer mPage = 1;
    private Integer mCountList = 0;
    private List<ResultsItem> mResultsItems = new ArrayList<>();
    private Long mTime;

    private String mCity;

    public MovieListPresenter(String city) {
        CinemaApp.getAppComponent().inject(this);
        mCity = city;
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
        mTime = getUnixTimestamp();
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

        final Observable<Response> observable = mCinemaService.getMovieList(String.valueOf(mTime), page, mCity);
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
        subscription = observable
                .compose(Utils.applySchedulers())
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

    public void clickItem(TransitionObject transitionObject) {
        transitionObject.setUrl(mResultsItems.get(transitionObject.getInteger()).getPoster().getImage());
        transitionObject.setInteger(mResultsItems.get(transitionObject.getInteger()).getId());
        Timber.d("ID = " + transitionObject.getInteger());
        router.navigateTo(Screens.DETAILS_SCREEN, transitionObject);
    }

    private void onLoadingSuccess(Response response) {
        List<ResultsItem> resultsItems;
        resultsItems = response.getResults();
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

    private Long getUnixTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }
}
