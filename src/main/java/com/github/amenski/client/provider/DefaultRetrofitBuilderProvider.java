package com.github.amenski.client.provider;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class DefaultRetrofitBuilderProvider implements IRetrofitBuilderProvider {

    @Override
    public Retrofit.Builder provideRetrofitBuilder(String baseUrl) {
        long timeOutMillis = 10000;
        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .connectTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
                .readTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
                .writeTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
    }
}
