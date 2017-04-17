package com.ifyou.nowincinema.ui.fragment.container;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.ui.Screens;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

/**
 * Created by Baranov on 12.04.2017.
 **/

public class FilmContainerFragment extends ContainerFragment {

    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(getContainerName());
    }

    public static FilmContainerFragment getNewInstance(String name) {
        FilmContainerFragment fragment = new FilmContainerFragment();
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getChildFragmentManager().findFragmentById(R.id.ftc_container) == null) {
            getCicerone().getRouter().replaceScreen(Screens.LIST_SCREEN, 0);
        }
    }

}

/*public class FilmContainerFragment extends Fragment implements RouterProvider, BackButtonListener {

    private static final String EXTRA_NAME = "fcf_extra_name";

    private Navigator navigator;

    @Inject
    LocalCiceroneHolder ciceroneHolder;

    @Inject
    SharedPreferences mSharedPrefs;

    public static FilmContainerFragment getNewInstance(String name) {
        FilmContainerFragment fragment = new FilmContainerFragment();
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);
        return fragment;
    }

    private String getContainerName() {
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

        if (getChildFragmentManager().findFragmentById(R.id.ftc_container) == null) {
            getCicerone().getRouter().replaceScreen(Screens.LIST_SCREEN, 0);
        }
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
                        intent.putExtra("URL", (String) data);
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
                        case Screens.DETAILS_SCREEN:
                            TransitionObject transitionObject = (TransitionObject) data;
                            return DetailsFragment.newInstance(transitionObject.getUrl(), transitionObject.getInteger());
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
                        if (((Forward) command).getScreenKey().equals(Screens.DETAILS_SCREEN)) {
                            Forward forward = (Forward) command;
                            Fragment fragment = createFragment(forward.getScreenKey(), forward.getTransitionData());
                            if (fragment == null) {
                                unknownScreen(command);
                                return;
                            }
                            TransitionObject transitionObject = (TransitionObject) forward.getTransitionData();
                            getChildFragmentManager()
                                    .beginTransaction()
                                    .addSharedElement(transitionObject.getView(), "image")
                                    .replace(R.id.ftc_container, fragment)
                                    .addToBackStack(forward.getScreenKey())
                                    .commit();
                        } else
                            super.applyCommand(command);
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
}*/
