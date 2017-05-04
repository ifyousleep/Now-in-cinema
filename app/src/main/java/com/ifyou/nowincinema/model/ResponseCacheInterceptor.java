package com.ifyou.nowincinema.model;

import java.io.IOException;

import okhttp3.Interceptor;

/**
 * Created by Baranov on 04.05.2017.
 **/

public class ResponseCacheInterceptor implements Interceptor {

    public ResponseCacheInterceptor() {

    }

    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60)
                .build();
    }
}