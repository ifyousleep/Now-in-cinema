package com.ifyou.nowincinema.ui.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.presentation.vo.Showings;
import com.ifyou.nowincinema.ui.adapter.base.ItemClickSupport;
import com.ifyou.nowincinema.ui.adapter.base.RecyclerBindableAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Baranov on 13.04.2017.
 **/

public class ShowingListAdapter extends RecyclerBindableAdapter<Showings, ShowingListAdapter.LinearViewHolder> {

    private ItemClickSupport.OnRowClickListener listener;

    public ShowingListAdapter(ItemClickSupport.OnRowClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int layoutId(int type) {
        return R.layout.item_showing_list;
    }

    @Override
    protected LinearViewHolder viewHolder(View view, int type) {
        return new LinearViewHolder(view, listener);
    }

    @Override
    protected void onBindItemViewHolder(LinearViewHolder viewHolder, final int position, int type) {
        viewHolder.bindView(getItem(position));
    }


    class LinearViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textTitle)
        TextView textTitle;
        @BindView(R.id.textPlace)
        TextView textPlace;
        @BindView(R.id.textTime)
        TextView textTime;
        @BindView(R.id.image)
        ImageView imageView;

        LinearViewHolder(View itemView, ItemClickSupport.OnRowClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageView.setOnClickListener(v -> {
                if (listener != null)
                    listener.onRowClicked(getAdapterPosition(), imageView);
            });
        }

        void bindView(Showings item) {
            textTitle.setText(item.getMovieTitle());
            textPlace.setText(item.getPlaceTitle().toUpperCase());

            long unixSeconds = item.getTime();
            Date date = new Date(unixSeconds * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String formattedDate = sdf.format(date);
            textTime.setText(formattedDate);

            Glide.with(imageView.getContext())
                    .load(item.getPosterUrl())
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            ViewCompat.setTransitionName(imageView, item.getId() + "_image");
        }
    }
}

