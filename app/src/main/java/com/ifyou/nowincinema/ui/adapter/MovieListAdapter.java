package com.ifyou.nowincinema.ui.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.presentation.vo.Movies;
import com.ifyou.nowincinema.ui.adapter.base.RecyclerBindableAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Baranov on 06.04.2017.
 **/

public class MovieListAdapter extends RecyclerBindableAdapter<Movies, MovieListAdapter.LinearViewHolder> {

    @Override
    protected int layoutId(int type) {
        return R.layout.item_movie_list;
    }

    @Override
    protected LinearViewHolder viewHolder(View view, int type) {
        return new LinearViewHolder(view);
    }

    @Override
    protected void onRecycledItemViewHolder(LinearViewHolder vh) {

    }

    @Override
    protected void onBindItemViewHolder(LinearViewHolder viewHolder, final int position, int type) {
        viewHolder.bindView(getItem(position));
    }

    @Override
    protected int getGridSpan(int position) {
        if (isFooter(position)) {
            return getMaxGridSpan();
        }
        return (position % 3 == 0 ? 2 : 1);
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        TextView text;
        @BindView(R.id.image)
        ImageView imageView;

        LinearViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(Movies item) {
            text.setText(item.getTitle());
            Picasso.with(imageView.getContext())
                    .load(item.getPosterUrl())
                    //.transform(new BlurTransformation(imageView.getContext()))
                    .into(imageView);
            ViewCompat.setTransitionName(imageView, item.getId() + "_image");
        }
    }
}
