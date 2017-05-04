package com.ifyou.nowincinema.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ifyou.nowincinema.BuildConfig;
import com.ifyou.nowincinema.app.CinemaApp;
import com.ifyou.nowincinema.model.ResponseCacheInterceptor;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import io.reactivex.schedulers.Schedulers;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Baranov on 06.04.2017.
 **/

@Module
public class RetrofitModule {

    @Provides
    @Named("serverUrl")
    String provideServerUrl() {
        return BuildConfig.SERVER_URL ;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(@Named("serverUrl") String serverUrl, Retrofit.Builder builder, OkHttpClient okHttpClient) {
        return builder
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(converterFactory);
    }

    @Provides
    @Singleton
    Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("HH:mm")
                .create();
    }

    @Provides
    @Singleton
    Cache provideHttpCache() {
        return new Cache(new File(CinemaApp.getContext().getCacheDir(),
                "apiResponses"), 5 * 1024 * 1024);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(ResponseCacheInterceptor responseCacheInterceptor, Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
        .addNetworkInterceptor(responseCacheInterceptor)
        .cache(cache);
        return client.build();
    }

    @Provides
    @Singleton
    ResponseCacheInterceptor provideResponseCacheInterceptor() {
        return new ResponseCacheInterceptor();
    }
}
