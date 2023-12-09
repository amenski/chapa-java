package it.aman.chapa.client.provider;

import retrofit2.Retrofit.Builder;

public interface IRetrofitBuilderProvider {

    Builder provideRetrofitBuilder(String baseUrl);

}
