package com.yaphet.chapa.client.provider;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public interface RetrofitClientProvider {

    Retrofit provideRetrofitBuilder();

    RetrofitClientProvider setDebug(boolean debug);

    RetrofitClientProvider setClient(OkHttpClient client);
}
