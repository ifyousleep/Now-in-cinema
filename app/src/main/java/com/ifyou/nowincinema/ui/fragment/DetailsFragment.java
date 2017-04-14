package com.ifyou.nowincinema.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.model.dto.details.DetailsMovie;
import com.ifyou.nowincinema.presentation.view.DetailsView;
import com.ifyou.nowincinema.presentation.presenter.DetailsPresenter;

import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsFragment extends MvpAppCompatFragment implements DetailsView, BackButtonListener {

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

    @ProvidePresenter
    DetailsPresenter provideDetailsPresenter() {
        return new DetailsPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    public static DetailsFragment newInstance(String url, Integer id) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("URL", url);
        args.putInt("ID", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
            setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
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
        if (savedInstanceState == null) {
            mDetailsPresenter.loadData(getArguments().getInt("ID"));
            mDetailsPresenter.showPoster(getArguments().getString("URL"));
        }
        poster.setOnClickListener(v -> mDetailsPresenter.showTouch(getArguments().getString("URL")));
    }

    @Override
    public void showPoster(String url) {
        Glide.with(getContext())
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(poster);
    }

    @Override
    public void showAbout(DetailsMovie movie) {
        textAbout.setText(fromHtml(movie.getBody_text()));
        textTitle.setText(movie.getTitle());
        textYear.setText(String.format(getString(R.string.country_year), movie.getCountry(), String.valueOf(movie.getYear())));
        textImdb.setText(String.format(getString(R.string.imdb), String.valueOf(movie.getImdb_rating())));
        textDirector.setText(String.format(getString(R.string.director), movie.getDirector()));
        textStars.setText(String.format(getString(R.string.stars), movie.getStars()));
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

    @Override
    public boolean onBackPressed() {
        mDetailsPresenter.onBackPressed();
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_city);
        item.setVisible(false);
    }
}
