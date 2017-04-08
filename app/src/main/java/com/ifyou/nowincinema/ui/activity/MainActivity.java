package com.ifyou.nowincinema.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.presentation.view.MainView;
import com.ifyou.nowincinema.presentation.presenter.MainPresenter;
import com.ifyou.nowincinema.ui.Screens;
import com.ifyou.nowincinema.ui.fragment.DetailsFragment;
import com.ifyou.nowincinema.ui.fragment.MovieListFragment;
import com.ifyou.nowincinema.R;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.commands.Replace;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mMainPresenter;

    @BindView(R.id.activity_main_toolbar)
    Toolbar mToolbar;

    @Inject
    NavigatorHolder navigatorHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CinemaApp.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            navigator.applyCommand(new Replace(Screens.LIST_SCREEN, 1));
        }
        Timber.d("Activity Created");
    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),
            R.id.activity_main_container) {
        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey) {
                case Screens.LIST_SCREEN:
                    return MovieListFragment.newInstance();
                case Screens.DETAILS_SCREEN:
                    return DetailsFragment.newInstance((int) data);
                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
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
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.activity_main_container);
        if (fragment != null
                && fragment instanceof DetailsFragment) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
