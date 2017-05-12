package com.ifyou.nowincinema.app;

import android.app.Application;

import com.ifyou.nowincinema.BuildConfig;
import com.ifyou.nowincinema.di.AppComponent;
import com.ifyou.nowincinema.di.DaggerAppComponent;

import timber.log.Timber;

/**
 * Created by Baranov on 06.04.2017.
 **/

public class CinemaApp extends Application {

    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
