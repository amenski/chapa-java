package com.github.amenski.client.provider;

import com.github.amenski.client.ChapaClientApi;
import retrofit2.Retrofit;

public class RetrofitBuilderProvider {

    public static <T> T buildClient(final String baseUrl, IRetrofitBuilderProvider configProvider) {
        Retrofit.Builder builder = configProvider.provideRetrofitBuilder(baseUrl);
        return builder.build().create((Class<T>) ChapaClientApi.class);
    }

    private RetrofitBuilderProvider(){}
}
