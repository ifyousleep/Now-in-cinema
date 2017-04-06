package com.ifyou.nowincinema.di.modules;

import com.ifyou.nowincinema.app.CinemaApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Baranov on 06.04.2017.
 **/

@Module(includes = {RetrofitModule.class})
public class ApiModule {

    @Provides
    @Singleton
    public CinemaApi provideApi(Retrofit retrofit) {
        return retrofit.create(CinemaApi.class);
    }
}
