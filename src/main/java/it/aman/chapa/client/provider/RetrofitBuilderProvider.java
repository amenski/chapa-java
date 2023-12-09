package it.aman.chapa.client.provider;

import it.aman.chapa.client.ChapaClientApi;
import retrofit2.Retrofit;

public class RetrofitBuilderProvider {

    public static <T> T buildClient(final String baseUrl, IRetrofitBuilderProvider configProvider) {
        Retrofit.Builder builder = configProvider.provideRetrofitBuilder(baseUrl);
        return builder.build().create((Class<T>) ChapaClientApi.class);
    }

    private RetrofitBuilderProvider(){}
}
