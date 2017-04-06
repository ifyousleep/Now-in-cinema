package com.ifyou.nowincinema.di.modules;

import com.ifyou.nowincinema.app.CinemaApi;
import com.ifyou.nowincinema.model.CinemaService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Baranov on 06.04.2017.
 **/

@Module(includes = {ApiModule.class})
public class CinemaModule {

    @Provides
    @Singleton
    public CinemaService provideCinemaService(CinemaApi api) {
        return new CinemaService(api);
    }
}
