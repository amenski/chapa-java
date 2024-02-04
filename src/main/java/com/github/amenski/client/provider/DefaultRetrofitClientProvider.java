package com.github.amenski.client.provider;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 *
 * Provides retrofit client to make calls to chapa api.
 *
 * This implementation will not retry on failures.
 *
 */
public class DefaultRetrofitClientProvider implements RetrofitClientProvider {

    @Override
    public Retrofit.Builder provideRetrofitBuilder(String baseUrl) {
        long timeOutMillis = 10000;
        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .connectTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
                .readTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
                .writeTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
    }
}
