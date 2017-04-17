package com.ifyou.nowincinema.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ifyou.nowincinema.presentation.view.PosterView;
import com.ifyou.nowincinema.presentation.presenter.PosterPresenter;

import com.arellomobile.mvp.MvpAppCompatActivity;

import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ifyou.nowincinema.ui.Extra;

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

        Intent intent = getIntent();
        String mUrl = intent.getStringExtra(Extra.EXTRA_URL);
        if (savedInstanceState == null)
            mPosterPresenter.showPoster(mUrl);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void showPoster(String url) {
        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(new BitmapImageViewTarget(mTouchImageView) {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        mPosterPresenter.hideProgressBar();
                        mTouchImageView.setImageBitmap(drawable);
                    }
                });
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
