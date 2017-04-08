package com.ifyou.nowincinema.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.widget.Toast;

import com.ifyou.nowincinema.ui.fragment.DataObject;
import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.presentation.view.MainView;
import com.ifyou.nowincinema.presentation.presenter.MainPresenter;
import com.ifyou.nowincinema.ui.Screens;
import com.ifyou.nowincinema.ui.fragment.DetailsFragment;
import com.ifyou.nowincinema.ui.fragment.DetailsTransition;
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
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
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
                    DetailsFragment detailsFragment = DetailsFragment.newInstance(((DataObject) data));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        detailsFragment.setEnterTransition(new Fade());
                        detailsFragment.setSharedElementEnterTransition(new DetailsTransition());
                        detailsFragment.setSharedElementReturnTransition(new DetailsTransition());
                    }
                    return detailsFragment;
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

        @Override
        public void applyCommand(Command command) {
            if (command instanceof Forward) {
                Forward forward = (Forward) command;
                Fragment fragment = createFragment(forward.getScreenKey(), forward.getTransitionData());
                if (fragment == null) {
                    unknownScreen(command);
                    return;
                }
                DataObject dataObject = (DataObject) forward.getTransitionData();
                getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement(dataObject.getView(), "image")
                        .replace(R.id.activity_main_container, fragment)
                        .addToBackStack(forward.getScreenKey())
                        .commit();
            } else
                super.applyCommand(command);
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
}
