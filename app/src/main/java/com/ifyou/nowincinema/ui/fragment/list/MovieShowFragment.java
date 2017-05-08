package com.ifyou.nowincinema.ui.fragment.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.presentation.presenter.MovieShowPresenter;
import com.ifyou.nowincinema.presentation.view.MovieShowView;
import com.ifyou.nowincinema.presentation.vo.MovieShow;
import com.ifyou.nowincinema.ui.adapter.MovieShowAdapter;

import java.util.List;

import timber.log.Timber;

public class MovieShowFragment extends ListFragment implements MovieShowView, BackButtonListener {

    @InjectPresenter
    MovieShowPresenter mMovieShowPresenter;

    private MovieShowAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public static MovieShowFragment newInstance(String city, Integer id) {
        MovieShowFragment fragment = new MovieShowFragment();
        Bundle args = new Bundle();
        args.putString(sCity, city);
        args.putInt("ID", id);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    MovieShowPresenter provideMovieShowPresenter() {
        return new MovieShowPresenter(((RouterProvider) getParentFragment()).getRouter(),
                getArguments().getString(sCity),
                getArguments().getInt("ID"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.movie_show);
    }

    @Override
    protected Integer setId() {
        return R.layout.fragment_movie_show;
    }

    @Override
    protected void initList() {
        mAdapter = new MovieShowAdapter();
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void removeLastItem() {
        new Handler().postDelayed(() ->
                mAdapter.removeFooter(mFooter), 50);
    }

    @Override
    public void showResultsItemList(List<MovieShow> showingsList) {
        mAdapter.addAll(showingsList);
        if (mAdapter.getFootersCount() == 0)
            mAdapter.addFooter(mFooter);
    }

    @Override
    public boolean onBackPressed() {
        mMovieShowPresenter.onBackPressed();
        return true;
    }

    @Override
    protected void onScrolled() {
        int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition() + 1;
        int modelsCount = mAdapter.getItemCount();

        if (lastVisibleItemPosition == modelsCount) {
            mMovieShowPresenter.onLastItemViewed();
            Timber.d("onScrolled");
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_city);
        item.setVisible(false);
    }
}
