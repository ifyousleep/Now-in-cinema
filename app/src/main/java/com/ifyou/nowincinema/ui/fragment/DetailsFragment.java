package com.ifyou.nowincinema.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.ifyou.nowincinema.presentation.view.DetailsView;
import com.ifyou.nowincinema.presentation.presenter.DetailsPresenter;

import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

public class DetailsFragment extends MvpAppCompatFragment implements DetailsView {
    public static final String TAG = "DetailsFragment";
    @InjectPresenter
    DetailsPresenter mDetailsPresenter;


    public static DetailsFragment newInstance(int id) {
        DetailsFragment fragment = new DetailsFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
