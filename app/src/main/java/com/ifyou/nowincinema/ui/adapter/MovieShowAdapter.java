package com.ifyou.nowincinema.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.presentation.vo.MovieShow;
import com.ifyou.nowincinema.ui.adapter.base.RecyclerBindableAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Baranov on 05.05.2017 to Now-in-cinema.
 **/


public class MovieShowAdapter extends RecyclerBindableAdapter<MovieShow, MovieShowAdapter.LinearViewHolder> {

    private final OnMapReadyCallback mInitialOnMapReadyCallback =
            (GoogleMap googleMap) -> {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                googleMap.getUiSettings().setMapToolbarEnabled(false);
            };

    private void initializeMapView(MapView mapView) {
        mapView.onCreate(null);
        mapView.getMapAsync(mInitialOnMapReadyCallback);
    }

    @Override
    protected int layoutId(int type) {
        return R.layout.item_movie_show;
    }

    @Override
    protected LinearViewHolder viewHolder(View view, int type) {
        LinearViewHolder holder = new LinearViewHolder(view);
        initializeMapView(holder.mMapView);
        return holder;
    }

    @Override
    protected void onRecycledItemViewHolder(LinearViewHolder vh) {
        vh.mMapView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onBindItemViewHolder(LinearViewHolder viewHolder, final int position, int type) {
        viewHolder.bindView(getItem(position));
    }

    class LinearViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        @BindView(R.id.textTitle)
        TextView mTitle;
        @BindView(R.id.textAddress)
        TextView mAddress;
        @BindView(R.id.textDate)
        TextView mDate;
        @BindView(R.id.mapView)
        MapView mMapView;

        GoogleMap mGoogleMap;
        private Double mLat;
        private Double mLon;
        private String mName;

        LinearViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(MovieShow item) {
            mAddress.setText(item.getAddress());
            mTitle.setText(item.getPlaceTitle().toUpperCase());

            long unixSeconds = item.getTime();
            Date date = new Date(unixSeconds * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            String formattedDate = sdf.format(date);
            mDate.setText(formattedDate);

            mLat = item.getLat();
            mLon = item.getLon();
            mName = item.getPlaceTitle();

            mMapView.getMapAsync(this);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;
            if (mGoogleMap == null) {
                return;
            }
            updateMapContents();
        }

        private void updateMapContents() {
            LatLng cord = new LatLng(mLat, mLon);
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(cord)
                    .title(mName));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cord, 14f));
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMapView.setVisibility(View.VISIBLE);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        }
    }
}
