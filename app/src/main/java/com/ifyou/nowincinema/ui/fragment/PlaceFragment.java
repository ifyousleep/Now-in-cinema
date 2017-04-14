package com.ifyou.nowincinema.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.presentation.view.PlaceView;
import com.ifyou.nowincinema.presentation.presenter.PlacePresenter;

import com.arellomobile.mvp.MvpAppCompatFragment;

import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

public class PlaceFragment extends MvpAppCompatFragment implements PlaceView, BackButtonListener {

    @InjectPresenter
    PlacePresenter mPlacePresenter;

    @ProvidePresenter
    PlacePresenter providePlacePresenter() {
        return new PlacePresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static PlaceFragment newInstance() {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.app_place);
    }

    @Override
    public boolean onBackPressed() {
        mPlacePresenter.onBackPressed();
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_city);
        item.setVisible(false);
    }
}
