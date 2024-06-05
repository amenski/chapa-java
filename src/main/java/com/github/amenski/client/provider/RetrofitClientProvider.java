package com.github.amenski.client.provider;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

public interface RetrofitClientProvider {

    Builder provideRetrofitBuilder(String baseUrl);

    default <T> T buildClient(Class<T> clazz, String baseUrl) {
        Retrofit.Builder builder = provideRetrofitBuilder(baseUrl);
        return builder.build().create(clazz);
    }
}
