package com.ifyou.nowincinema.di;

import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.di.modules.CinemaModule;
import com.ifyou.nowincinema.di.modules.ContextModule;
import com.ifyou.nowincinema.di.modules.LocalNavigationModule;
import com.ifyou.nowincinema.di.modules.NavigationModule;
import com.ifyou.nowincinema.presentation.presenter.DetailsPresenter;
import com.ifyou.nowincinema.presentation.presenter.MainPresenter;
import com.ifyou.nowincinema.presentation.presenter.MovieListPresenter;
import com.ifyou.nowincinema.presentation.presenter.MovieShowPresenter;
import com.ifyou.nowincinema.presentation.presenter.ShowingListPresenter;
import com.ifyou.nowincinema.ui.activity.MainActivity;
import com.ifyou.nowincinema.ui.fragment.container.ContainerFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Baranov on 06.04.2017.
 **/

@Singleton
@Component(modules = {
        ContextModule.class,
        CinemaModule.class,
        NavigationModule.class,
        LocalNavigationModule.class
})
public interface AppComponent {

    void inject(MovieListPresenter movieListPresenter);

    void inject (MainActivity mainActivity);

    void inject (DetailsPresenter detailsPresenter);

    void inject (MainPresenter mainPresenter);

    void inject (ShowingListPresenter showingListPresenter);

    void inject(MovieShowPresenter movieShowPresenter);

    void inject (ContainerFragment containerFragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(CinemaApp application);
        AppComponent build();
    }

}