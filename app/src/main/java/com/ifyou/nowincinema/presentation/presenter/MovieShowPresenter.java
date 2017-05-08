package com.ifyou.nowincinema.presentation.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.common.Utils;
import com.ifyou.nowincinema.model.CinemaService;
import com.ifyou.nowincinema.model.dto.movieshow.MovieShowList;
import com.ifyou.nowincinema.model.dto.movieshow.ResultsItem;
import com.ifyou.nowincinema.presentation.mappers.MovieShowMapper;
import com.ifyou.nowincinema.presentation.view.MovieShowView;
import com.ifyou.nowincinema.presentation.vo.MovieShow;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MovieShowPresenter extends MvpPresenter<MovieShowView> {

    private final String mCity;
    @Inject
    CinemaService mCinemaService;
    private Router router;
    private boolean mIsInLoading;
    private String mTime;
    private Integer mId;
    private Integer mPage = 1;
    private Integer mCountList = 0;
    private List<MovieShow> mResultsItems = new ArrayList<>();
    private Disposable subscription = Disposables.empty();

    public MovieShowPresenter(Router router, String city, Integer id) {
        CinemaApp.getAppComponent().inject(this);
        this.router = router;
        mCity = city;
        mId = id;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mTime = Utils.getUnixTimestamp();
        loadMovieShow(mId);
    }

    private boolean isNotEndOfList() {
        return (mResultsItems == null || mResultsItems.size() < mCountList);
    }

    public void onLastItemViewed() {
        mPage += 1;
        getViewState().disableLastItemViewListener();
        if (isNotEndOfList()) {
            loadMovieShow(mId);
        } else
            getViewState().removeLastItem();
    }

    private void loadMovieShow(Integer id) {
        if (mIsInLoading) {
            return;
        }
        mIsInLoading = true;
        final Observable<MovieShowList> observable = mCinemaService.getMovieShowings(id, mTime, "place", mPage, mCity);
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

    private void onLoadingSuccess(MovieShowList response) {
        List<ResultsItem> resultsItems = response.getResults();
        List<MovieShow> showingsList = MovieShowMapper.fromResultsItemToMovieShow(resultsItems);
        mResultsItems.addAll(showingsList);
        getViewState().showResultsItemList(showingsList);
        getViewState().activateLastItemViewListener();
    }

    private void onLoadingFailed(Throwable error) {
        getViewState().showError(error.toString());
        getViewState().removeLastItem();
    }

    @Override
    public void onDestroy() {
        if (!subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    public void onBackPressed() {
        router.exit();
    }
}
