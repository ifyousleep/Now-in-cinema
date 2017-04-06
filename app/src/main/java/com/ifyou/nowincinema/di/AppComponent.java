package com.ifyou.nowincinema.di;

import android.content.Context;

import com.ifyou.nowincinema.di.modules.CinemaModule;
import com.ifyou.nowincinema.di.modules.ContextModule;
import com.ifyou.nowincinema.model.CinemaService;
import com.ifyou.nowincinema.presentation.presenter.MovieListPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Baranov on 06.04.2017.
 **/

@Singleton
@Component(modules = {ContextModule.class, CinemaModule.class})
public interface AppComponent {
    Context getContext();
    CinemaService getCinemaService();

    void inject(MovieListPresenter movieListPresenter);

    /*Bus getBus();

    void inject(SignInPresenter presenter);
    void inject(MovieListPresenter movieListPresenter);
    void inject(RepositoryLikesPresenter repositoryLikesPresenter);*/
}