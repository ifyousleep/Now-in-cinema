package com.ifyou.nowincinema.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.ifyou.nowincinema.BuildConfig;
import com.ifyou.nowincinema.di.AppComponent;
import com.ifyou.nowincinema.di.DaggerAppComponent;
import com.ifyou.nowincinema.di.modules.ContextModule;

import timber.log.Timber;

/**
 * Created by Baranov on 06.04.2017.
 **/

public class CinemaApp extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }


    @VisibleForTesting
    public static void setAppComponent(@NonNull AppComponent appComponent) {
        sAppComponent = appComponent;
    }
}
