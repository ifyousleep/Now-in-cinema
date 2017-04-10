package com.ifyou.nowincinema.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ifyou.nowincinema.presentation.view.TouchImageView;
import com.ifyou.nowincinema.presentation.presenter.TouchImagePresenter;

import com.arellomobile.mvp.MvpAppCompatFragment;

import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class TouchImageFragment extends MvpAppCompatFragment implements TouchImageView {

    @InjectPresenter
    TouchImagePresenter mTouchImagePresenter;

    @BindView(R.id.touch_image)
    ImageView mTouchImageView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder mUnbinder;

    public static TouchImageFragment newInstance(TransitionObject transitionObject) {
        TouchImageFragment fragment = new TouchImageFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", transitionObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_touch_image, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.app_poster);
        TransitionObject transitionObject = (TransitionObject) getArguments().getSerializable("data");
        if (transitionObject != null) {
            mTouchImagePresenter.setImage(transitionObject.getUrl());
            Timber.d(transitionObject.getUrl());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setImage(String url) {
        Glide.with(getContext())
                .load(url)
                .asBitmap()
                .into(new BitmapImageViewTarget(mTouchImageView) {
                    @Override
                    public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        mTouchImagePresenter.hideProgressBar();
                        mTouchImageView.setImageBitmap(drawable);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
