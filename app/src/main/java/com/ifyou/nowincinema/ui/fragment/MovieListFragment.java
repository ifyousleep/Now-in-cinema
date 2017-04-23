package com.ifyou.nowincinema.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.presentation.view.MovieListView;
import com.ifyou.nowincinema.presentation.presenter.MovieListPresenter;
import com.ifyou.nowincinema.presentation.vo.Movies;
import com.ifyou.nowincinema.ui.adapter.base.GridSpacingItemDecoration;
import com.ifyou.nowincinema.ui.adapter.base.ItemClickSupport;
import com.ifyou.nowincinema.ui.adapter.MovieListAdapter;
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
    private View mFooter;

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
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new MovieListAdapter();
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(getContext(), R.dimen.item_offset));

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        mFooter = getLayoutInflater(savedInstanceState).inflate(R.layout.item_loading, recyclerView, false);

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
