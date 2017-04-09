package com.ifyou.nowincinema.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ifyou.nowincinema.model.ResultsItem;
import com.ifyou.nowincinema.presentation.view.MovieListView;
import com.ifyou.nowincinema.presentation.presenter.MovieListPresenter;

import com.arellomobile.mvp.MvpAppCompatFragment;

import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ifyou.nowincinema.ui.adapter.GridSpacingItemDecoration;
import com.ifyou.nowincinema.ui.adapter.ItemClickSupport;
import com.ifyou.nowincinema.ui.adapter.NewMovieListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class MovieListFragment extends MvpAppCompatFragment implements MovieListView {

    @InjectPresenter
    MovieListPresenter mMovieListPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder mUnbinder;
    private NewMovieListAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;
    private View mFooter;

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.app_name);
        mAdapter = new NewMovieListAdapter();
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(getContext(), R.dimen.item_offset));

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
    public void showResultsItemList(List<ResultsItem> resultsItemList) {
        mAdapter.addAll(resultsItemList);
        if (mAdapter.getFootersCount() == 0)
            mAdapter.addFooter(mFooter);
    }

    @Override
    public void activateLastItemViewListener() {
        enableSearchOnFinish();
    }

    @Override
    public void disableLastItemViewListener() {
        disableSearchOnFinish();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void enableSearchOnFinish() {
        recyclerView.addOnScrollListener(new FinishScrollListener());
    }

    private void disableSearchOnFinish() {
        recyclerView.clearOnScrollListeners();
    }

    private class FinishScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int lastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition() + 1;
            int modelsCount = mAdapter.getItemCount();

            if (lastVisibleItemPosition == modelsCount) {
                mMovieListPresenter.onLastItemViewed();
                Timber.d("onScrolled");
            }
        }
    }
}
