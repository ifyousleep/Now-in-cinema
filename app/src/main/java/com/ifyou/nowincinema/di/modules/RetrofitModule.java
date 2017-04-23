package com.ifyou.nowincinema.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ifyou.nowincinema.app.CinemaApp;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
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
    @Singleton
    public Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder
                .baseUrl("https://kudago.com")
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder(Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(converterFactory);
    }

    @Provides
    @Singleton
    public Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("HH:mm")
                .create();
    }

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new ResponseCacheInterceptor())
            .cache(new Cache(new File(CinemaApp.getContext().getCacheDir(),
                    "apiResponses"), 5 * 1024 * 1024))
            .build();

    private static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        }
    }
}
