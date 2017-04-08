package com.ifyou.nowincinema.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ifyou.nowincinema.model.Movie;
import com.ifyou.nowincinema.presentation.view.DetailsView;
import com.ifyou.nowincinema.presentation.presenter.DetailsPresenter;

import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsFragment extends MvpAppCompatFragment implements DetailsView {

    @InjectPresenter
    DetailsPresenter mDetailsPresenter;

    @BindView(R.id.textAbout)
    TextView textAbout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.image)
    ImageView poster;
    @BindView(R.id.textName)
    TextView textTitle;
    @BindView(R.id.textYear)
    TextView textYear;
    @BindView(R.id.textImdb)
    TextView textImdb;
    @BindView(R.id.textDirector)
    TextView textDirector;
    @BindView(R.id.textStars)
    TextView textStars;

    private Unbinder mUnbinder;

    public static DetailsFragment newInstance(int id) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_details, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.app_detail);
        mDetailsPresenter.loadData(getArguments().getInt("id"));
    }

    @Override
    public void showAbout(Movie movie) {
        textAbout.setText(fromHtml(movie.getBodyText()));
        textTitle.setText(movie.getTitle());
        textYear.setText(String.format(getString(R.string.country_year), movie.getCountry(), String.valueOf(movie.getYear())));
        textImdb.setText(String.format(getString(R.string.imdb), String.valueOf(movie.getImdb_rating())));
        textDirector.setText(String.format(getString(R.string.director), movie.getDirector()));
        textStars.setText(String.format(getString(R.string.stars), movie.getStars()));
        Glide.with(getContext())
                .load(movie.getPoster().getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);
    }

    @SuppressWarnings("deprecation")
    private Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
