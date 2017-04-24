package com.ifyou.nowincinema.ui.fragment.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.presentation.view.ShowingListView;
import com.ifyou.nowincinema.presentation.presenter.ShowingListPresenter;
import com.ifyou.nowincinema.presentation.vo.Showings;
import com.ifyou.nowincinema.ui.adapter.base.ItemClickSupport;
import com.ifyou.nowincinema.ui.adapter.ShowingListAdapter;
import com.ifyou.nowincinema.ui.fragment.TransitionObject;
import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import timber.log.Timber;

public class ShowingListFragment extends ListFragment implements ShowingListView, BackButtonListener, ItemClickSupport.OnRowClickListener {

    @InjectPresenter
    ShowingListPresenter mShowingListPresenter;

    private ShowingListAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

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
    protected Integer setId() {
        return R.layout.fragment_showing_list;
    }

    @Override
    protected void initList() {
        mAdapter = new ShowingListAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                (recyclerView, pos, v) ->
                        mShowingListPresenter.clickPlace(pos)
        );
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
    public void showResultsItemList(List<Showings> showingsList) {
        mAdapter.addAll(showingsList);
        if (mAdapter.getFootersCount() == 0)
            mAdapter.addFooter(mFooter);
    }

    @Override
    public boolean onBackPressed() {
        mShowingListPresenter.onBackPressed();
        return true;
    }

    @Override
    protected void onScrolled() {
        int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition() + 1;
        int modelsCount = mAdapter.getItemCount();

        if (lastVisibleItemPosition == modelsCount) {
            mShowingListPresenter.onLastItemViewed();
            Timber.d("onScrolled");
        }
    }
}
