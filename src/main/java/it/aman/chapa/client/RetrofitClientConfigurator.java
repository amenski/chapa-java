package it.aman.chapa.client;


import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

class RetrofitClientConfigurator {

    private static final long timeOutMillis = 10000;

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .protocols(Arrays.asList(Protocol.HTTP_1_1))
            .connectTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
            .readTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
            .writeTimeout(timeOutMillis, TimeUnit.MILLISECONDS)
            .build();

    static <T> T buildClient(Class<T> clazz, final String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(clazz);
    }
}
