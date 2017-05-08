package com.ifyou.nowincinema.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.presentation.presenter.PlacePresenter;
import com.ifyou.nowincinema.presentation.view.PlaceView;
import com.ifyou.nowincinema.presentation.vo.PlaceObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlaceFragment extends MvpAppCompatFragment implements PlaceView, BackButtonListener,
        OnMapReadyCallback {

    private final OnMapReadyCallback mInitialOnMapReadyCallback =
            (GoogleMap googleMap) -> {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                googleMap.getUiSettings().setMapToolbarEnabled(false);
            };
    @InjectPresenter
    PlacePresenter mPlacePresenter;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.textAddress)
    TextView textAddress;
    private Unbinder mUnbinder;
    private GoogleMap googleMap;
    private Double mLat;
    private Double mLon;

    public static PlaceFragment newInstance(PlaceObject placeObject) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putParcelable("PLACE", placeObject);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    PlacePresenter providePlacePresenter() {
        return new PlacePresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_place, container, false);
        mUnbinder = ButterKnife.bind(this, v);

        mMapView.setVisibility(View.INVISIBLE);
        initializeMapView(mMapView);
        return v;
    }

    private void initializeMapView(MapView mapView) {
        mapView.onCreate(null);
        mapView.getMapAsync(mInitialOnMapReadyCallback);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.app_place);

        PlaceObject placeObject = getArguments().getParcelable("PLACE");
        if (placeObject != null) {
            String name = placeObject.getName();
            String address = placeObject.getAddress();
            mLat = placeObject.getLat();
            mLon = placeObject.getLon();
            textName.setText(name.toUpperCase());
            textAddress.setText(address);
        }

        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        showMap();
    }

    private void showMap() {
        if (googleMap == null) {
            return;
        }
        LatLng cord = new LatLng(mLat, mLon);
        googleMap.addMarker(new MarkerOptions()
                .position(cord)
                .title(textName.getText().toString()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cord, 14f));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMapView.setVisibility(View.VISIBLE);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
    }

    @Override
    public boolean onBackPressed() {
        mPlacePresenter.onBackPressed();
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_city);
        item.setVisible(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
        mUnbinder.unbind();
    }
}
