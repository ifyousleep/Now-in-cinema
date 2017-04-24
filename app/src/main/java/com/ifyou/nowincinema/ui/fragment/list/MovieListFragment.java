package com.ifyou.nowincinema.ui.fragment.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.presentation.view.MovieListView;
import com.ifyou.nowincinema.presentation.presenter.MovieListPresenter;
import com.ifyou.nowincinema.presentation.vo.Movies;
import com.ifyou.nowincinema.ui.adapter.base.GridSpacingItemDecoration;
import com.ifyou.nowincinema.ui.adapter.base.ItemClickSupport;
import com.ifyou.nowincinema.ui.adapter.MovieListAdapter;
import com.ifyou.nowincinema.ui.fragment.TransitionObject;
import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import timber.log.Timber;

public class MovieListFragment extends ListFragment implements MovieListView, BackButtonListener {

    @InjectPresenter
    MovieListPresenter mMovieListPresenter;

    private MovieListAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;

    @ProvidePresenter
    MovieListPresenter provideMovieListPresenter() {
        String mCity = getArguments().getString(sCity);
        return new MovieListPresenter(mCity, ((RouterProvider) getParentFragment()).getRouter());
    }

    public static MovieListFragment newInstance(String city, String myCity) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putString(sCity, city);
        args.putString(sMyCity, myCity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Integer setId() {
        return R.layout.fragment_movie_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initList() {
        mAdapter = new MovieListAdapter();
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(getContext(), R.dimen.item_offset));

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                (recyclerView, pos, v) ->
                        mMovieListPresenter.clickItem(new TransitionObject(v.findViewById(R.id.image), pos, ""))
        );
    }

    @Override
    public void removeLastItem() {
        new Handler().postDelayed(() ->
                mAdapter.removeFooter(mFooter), 50);
    }

    @Override
    public void showResultsItemList(List<Movies> moviesList) {
        mAdapter.addAll(moviesList);
        if (mAdapter.getFootersCount() == 0)
            mAdapter.addFooter(mFooter);
    }

    @Override
    protected void onScrolled() {
        int lastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition() + 1;
        int modelsCount = mAdapter.getItemCount();

        if (lastVisibleItemPosition == modelsCount) {
            mMovieListPresenter.onLastItemViewed();
            Timber.d("onScrolled");
        }
    }

    @Override
    public boolean onBackPressed() {
        mMovieListPresenter.onBackPressed();
        return true;
    }
}
