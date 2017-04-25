package com.ifyou.nowincinema.ui.fragment.container;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.common.BackButtonListener;
import com.ifyou.nowincinema.common.RouterProvider;
import com.ifyou.nowincinema.di.LocalCiceroneHolder;
import com.ifyou.nowincinema.ui.Extra;
import com.ifyou.nowincinema.presentation.vo.PlaceObject;
import com.ifyou.nowincinema.ui.Screens;
import com.ifyou.nowincinema.ui.activity.PosterActivity;
import com.ifyou.nowincinema.ui.fragment.DetailsFragment;
import com.ifyou.nowincinema.ui.fragment.list.MovieListFragment;
import com.ifyou.nowincinema.ui.fragment.PlaceFragment;
import com.ifyou.nowincinema.ui.fragment.list.ShowingListFragment;
import com.ifyou.nowincinema.ui.fragment.TransitionObject;

import javax.inject.Inject;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;

/**
 * Created by Baranov on 13.04.2017.
 **/

public class ContainerFragment extends Fragment implements RouterProvider, BackButtonListener {

    @Inject
    protected LocalCiceroneHolder ciceroneHolder;

    @Inject
    protected SharedPreferences mSharedPrefs;

    public static final String EXTRA_NAME = "extra_name";
    private Navigator navigator;

    public String getContainerName() {
        return getArguments().getString(EXTRA_NAME);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        CinemaApp.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(getContainerName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_container, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        getCicerone().getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportAppNavigator(getActivity(), getChildFragmentManager(), R.id.ftc_container) {

                @Override
                protected Intent createActivityIntent(String screenKey, Object data) {
                    if (screenKey.equals(Screens.POSTER_SCREEN)) {
                        Intent intent = new Intent(getActivity(), PosterActivity.class);
                        intent.putExtra(Extra.EXTRA_URL, ((TransitionObject) data).getUrl());
                        return intent;
                    }
                    return null;
                }

                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    String myCity = mSharedPrefs.getString("my_city", "");
                    switch (screenKey) {
                        case Screens.LIST_SCREEN:
                            return MovieListFragment.newInstance(mSharedPrefs.getString("city", ""), myCity);
                        case Screens.SHOWING_SCREEN:
                            return ShowingListFragment.newInstance(mSharedPrefs.getString("city", ""), myCity);
                        case Screens.DETAILS_SCREEN:
                            TransitionObject transitionObject = (TransitionObject) data;
                            return DetailsFragment.newInstance(transitionObject.getUrl(), transitionObject.getInteger());
                        case Screens.PLACE_SCREEN:
                            return PlaceFragment.newInstance((PlaceObject) data);
                    }
                    return null;
                }

                @Override
                protected void exit() {
                    ((RouterProvider) getActivity()).getRouter().exit();
                }

                @Override
                public void applyCommand(Command command) {
                    if (command instanceof Forward) {
                        Forward forward = (Forward) command;
                        switch (((Forward) command).getScreenKey()) {
                            case Screens.DETAILS_SCREEN:
                                TransitionObject tObjectDetails = (TransitionObject) forward.getTransitionData();
                                Fragment fragment = createFragment(forward.getScreenKey(), forward.getTransitionData());
                                if (fragment == null) {
                                    unknownScreen(command);
                                    return;
                                }
                                getChildFragmentManager()
                                        .beginTransaction()
                                        .addSharedElement(tObjectDetails.getView(), "image")
                                        .replace(R.id.ftc_container, fragment)
                                        .addToBackStack(forward.getScreenKey())
                                        .commit();
                                break;
                            case Screens.POSTER_SCREEN:
                                TransitionObject tObjectPoster = (TransitionObject) forward.getTransitionData();
                                Intent activityIntent = createActivityIntent(forward.getScreenKey(), forward.getTransitionData());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    ActivityOptionsCompat options = ActivityOptionsCompat.
                                            makeSceneTransitionAnimation(getActivity(),
                                                    tObjectPoster.getView(),
                                                    ViewCompat.getTransitionName(tObjectPoster.getView()));
                                    if (activityIntent != null) {
                                        startActivity(activityIntent, options.toBundle());
                                    }
                                    return;
                                } else {
                                    if (activityIntent != null) {
                                        startActivity(activityIntent);
                                    }
                                }
                                break;
                            default:
                                super.applyCommand(command);
                                break;
                        }
                    } else
                        super.applyCommand(command);
                }
            };
        }
        return navigator;
    }

    @Override
    public Router getRouter() {
        return getCicerone().getRouter();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.ftc_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            ((RouterProvider) getActivity()).getRouter().exit();
            return true;
        }
    }
}
