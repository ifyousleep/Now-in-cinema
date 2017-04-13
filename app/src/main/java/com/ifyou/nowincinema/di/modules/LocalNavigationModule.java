package com.ifyou.nowincinema.di.modules;

import com.ifyou.nowincinema.di.LocalCiceroneHolder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Baranov on 12.04.2017.
 **/

@Module
public class LocalNavigationModule {

    @Provides
    @Singleton
    LocalCiceroneHolder provideLocalNavigationHolder() {
        return new LocalCiceroneHolder();
    }
}
