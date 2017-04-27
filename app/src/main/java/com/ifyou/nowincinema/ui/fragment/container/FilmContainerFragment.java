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

    public static FilmContainerFragment getNewInstance(Integer name) {
        FilmContainerFragment fragment = new FilmContainerFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(EXTRA_NAME, name);
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
