package com.ifyou.nowincinema.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.model.place.ResultsItem;
import com.ifyou.nowincinema.presentation.view.ShowingListView;
import com.ifyou.nowincinema.presentation.presenter.ShowingListPresenter;

import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ifyou.nowincinema.ui.adapter.ItemClickSupport;
import com.ifyou.nowincinema.ui.adapter.ShowingListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class ShowingListFragment extends MvpAppCompatFragment implements ShowingListView, BackButtonListener, ItemClickSupport.OnRowClickListener {

    @InjectPresenter
    ShowingListPresenter mShowingListPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder mUnbinder;
    private ShowingListAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private View mFooter;

    private static String sCity = "city";
    private static String sMyCity = "my_city";

    @ProvidePresenter
    ShowingListPresenter provideShowingListPresenter() {
        String mCity = getArguments().getString(sCity);
        return new ShowingListPresenter(mCity, ((RouterProvider) getParentFragment()).getRouter());
    }

    public static ShowingListFragment newInstance(String city, String myCity) {
        ShowingListFragment fragment = new ShowingListFragment();
        Bundle args = new Bundle();
        args.putString(sCity, city);
        args.putString(sMyCity, myCity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_showing_list, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String myCity = getArguments().getString(sMyCity, "");
        if (myCity.length() > 0)
            getActivity().setTitle(myCity);
        else
            getActivity().setTitle(R.string.app_name);
        mAdapter = new ShowingListAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                (recyclerView, pos, v) ->
                    showError("recycler item")
        );

        mFooter = getLayoutInflater(savedInstanceState).inflate(R.layout.item_loading, recyclerView, false);
    }

    @Override
    public void onRowClicked(int pos, View v) {
        mShowingListPresenter.clickItem(new TransitionObject(v, pos, ""));
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
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onBackPressed() {
        mShowingListPresenter.onBackPressed();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void activateLastItemViewListener() {
        enableSearchOnFinish();
    }

    @Override
    public void disableLastItemViewListener() {
        disableSearchOnFinish();
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
            int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition() + 1;
            int modelsCount = mAdapter.getItemCount();

            if (lastVisibleItemPosition == modelsCount) {
                mShowingListPresenter.onLastItemViewed();
                Timber.d("onScrolled");
            }
        }
    }
}
