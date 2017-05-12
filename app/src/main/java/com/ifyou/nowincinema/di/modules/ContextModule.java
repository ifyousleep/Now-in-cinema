package com.ifyou.nowincinema.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ifyou.nowincinema.app.CinemaApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Baranov on 06.04.2017.
 **/

@Module
public class ContextModule {

    @Provides
    public Context provideContext(CinemaApp app) {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
