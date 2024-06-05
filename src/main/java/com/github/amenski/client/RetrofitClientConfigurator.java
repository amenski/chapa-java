package com.github.amenski.client;


import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

class RetrofitClientConfigurator {

    private static final long timeOutMillis = 10000;

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .connectTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
            .readTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
            .writeTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    static <T> T buildClient(final String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create((Class<T>) ChapaClientApi.class);
    }
}
