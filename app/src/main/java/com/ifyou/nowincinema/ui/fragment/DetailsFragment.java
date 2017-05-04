package com.ifyou.nowincinema.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
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

import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.presentation.view.DetailsView;
import com.ifyou.nowincinema.presentation.presenter.DetailsPresenter;
import com.ifyou.nowincinema.presentation.vo.Details;
import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import jp.wasabeef.picasso.transformations.BlurTransformation;

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
        ViewCompat.setTransitionName(poster, "image");
        TransitionObject transitionObject = new TransitionObject(poster, 0, getArguments().getString("URL"));
        poster.setOnClickListener(v -> mDetailsPresenter.showTouch(transitionObject));
    }

    @Override
    public void showPoster(String url) {
        Picasso.with(getContext())
                .load(url)
                .transform(new BlurTransformation(getActivity()))
                .noFade()
                .into(poster, new Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        startPostponedEnterTransition();
                    }
                });
    }

    @Override
    public void showAbout(Details movie) {
        String stringBuilder = "";
        stringBuilder += String.format(getString(R.string.country_year), movie.getCountry(), String.valueOf(movie.getYear()));
        stringBuilder += "\n\n";
        stringBuilder += fromHtml(movie.getAbout());
        stringBuilder += String.format(getString(R.string.director), movie.getDirector());
        stringBuilder += "\n";
        stringBuilder += String.format(getString(R.string.stars), movie.getStars());
        stringBuilder += "\n";
        stringBuilder += String.format(getString(R.string.imdb), String.valueOf(movie.getImdb()));
        textTitle.setText(movie.getTitle());
        textAbout.setText(stringBuilder);
    }

    @SuppressWarnings("deprecation")
    private Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
