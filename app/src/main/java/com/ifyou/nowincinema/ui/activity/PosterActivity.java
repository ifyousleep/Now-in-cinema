package com.ifyou.nowincinema.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.presentation.presenter.PosterPresenter;
import com.ifyou.nowincinema.presentation.view.PosterView;
import com.ifyou.nowincinema.ui.Extra;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PosterActivity extends MvpAppCompatActivity implements PosterView {

    @InjectPresenter
    PosterPresenter mPosterPresenter;

    @BindView(R.id.touch_image)
    ImageView mTouchImageView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTouchImageView.setTransitionName("image");
            /*Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);*/
        }
        supportPostponeEnterTransition();
        Intent intent = getIntent();
        String mUrl = intent.getStringExtra(Extra.EXTRA_URL);
        if (savedInstanceState == null)
            mPosterPresenter.showPoster(mUrl);
    }

    @Override
    public void showPoster(String url) {
        Picasso.with(this)
                .load(url)
                //.transform(new BlurTransformation(this))
                .into(mTouchImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mPosterPresenter.hideProgressBar();
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        mPosterPresenter.hideProgressBar();
                        supportStartPostponedEnterTransition();
                    }
                });
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
