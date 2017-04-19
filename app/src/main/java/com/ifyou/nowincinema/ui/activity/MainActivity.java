package com.ifyou.nowincinema.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.MapView;
import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.ui.Extra;
import com.ifyou.nowincinema.ui.fragment.container.FilmContainerFragment;
import com.ifyou.nowincinema.ui.fragment.container.PlaceContainerFragment;
import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.presentation.view.MainView;
import com.ifyou.nowincinema.presentation.presenter.MainPresenter;
import com.ifyou.nowincinema.ui.Screens;
import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends MvpAppCompatActivity implements MainView, RouterProvider {

    @InjectPresenter
    MainPresenter mMainPresenter;

    @BindView(R.id.activity_main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Router router;

    @Inject
    SharedPreferences mSharedPrefs;

    private FilmContainerFragment mFilmContainerFragment;
    private PlaceContainerFragment mPlaceContainerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        CinemaApp.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        initContainers();
        setupBottomNavigationView();

        if (savedInstanceState == null) {
            mBottomNavigationView.setSelectedItemId(getIntent().getIntExtra(Extra.EXTRA_ID, R.id.menu_film));
        }
    }

    @Override
    public void initMap() {
        runOnUiThread(() -> {
            MapView mapView = new MapView(MainActivity.this);
            mapView.onCreate(null);
        });
    }

    private void initContainers() {
        FragmentManager fm = getSupportFragmentManager();
        mFilmContainerFragment = (FilmContainerFragment) fm.findFragmentByTag("FILM");
        if (mFilmContainerFragment == null) {
            mFilmContainerFragment = FilmContainerFragment.getNewInstance("FILM");
            fm.beginTransaction()
                    .add(R.id.activity_main_container, mFilmContainerFragment, "FILM")
                    .detach(mFilmContainerFragment).commitNow();
        }

        mPlaceContainerFragment = (PlaceContainerFragment) fm.findFragmentByTag("PLACE");
        if (mPlaceContainerFragment == null) {
            mPlaceContainerFragment = PlaceContainerFragment.getNewInstance("PLACE");
            fm.beginTransaction()
                    .add(R.id.activity_main_container, mPlaceContainerFragment, "PLACE")
                    .detach(mPlaceContainerFragment).commitNow();
        }
    }

    private Navigator navigator = new Navigator() {
        @Override
        public void applyCommand(Command command) {
            if (command instanceof Back) {
                finish();
            } else if (command instanceof Replace) {
                FragmentManager fm = getSupportFragmentManager();
                switch (((Replace) command).getScreenKey()) {
                    case Screens.FILM_CONTAINER_SCREEN:
                        fm.beginTransaction()
                                .detach(mPlaceContainerFragment)
                                .attach(mFilmContainerFragment)
                                .commitNow();
                        break;
                    case Screens.PLACE_CONTAINER_SCREEN:
                        fm.beginTransaction()
                                .detach(mFilmContainerFragment)
                                .attach(mPlaceContainerFragment)
                                .commitNow();
                        break;
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);

    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_city:
                mMainPresenter.showDialog();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void showDialog() {
        enableDialog();
    }

    private void enableDialog() {
        String[] mCityArray = getResources().getStringArray(R.array.city_id);
        String[] mCityNameArray = getResources().getStringArray(R.array.city_actions);
        new AlertDialog.Builder(this)
                .setTitle(R.string.city)
                .setItems(R.array.city_actions, (dialog, which) -> {
                    mSharedPrefs
                            .edit()
                            .putString("city", mCityArray[which])
                            .putString("my_city", mCityNameArray[which])
                            .apply();
                    dialog.dismiss();
                    mMainPresenter.updateCity();
                })
                .create()
                .show();
    }

    private void setupBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_film:
                    mMainPresenter.showMenuFilm();
                    break;
                case R.id.menu_place:
                    mMainPresenter.showMenuPlace();
                    break;
            }
            return true;
        });
    }

    @Override
    public Router getRouter() {
        return router;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_container);
        if (fragment != null
                && fragment instanceof BackButtonListener) {
            ((BackButtonListener) fragment).onBackPressed();
        } else {
            mMainPresenter.onBackPressed();
        }
    }

    @Override
    public void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Extra.EXTRA_ID, mBottomNavigationView.getSelectedItemId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
