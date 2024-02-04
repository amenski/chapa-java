package it.aman.chapa.client.provider;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retrofit.RetryCallAdapter;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class RetrierRetrofitBuilderProvider implements IRetrofitBuilderProvider {

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
                .addCallAdapterFactory(RetryCallAdapter.of(retry(timeOutMillis)))
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
    }

    private static Retry retry(long initialIntervalMillis) {
        RetryConfig retryCOnfig = RetryConfig.custom().maxAttempts(3)
                .intervalFunction(IntervalFunction.ofExponentialBackoff(initialIntervalMillis, 2))
                .retryOnResult(new RetryPredicate())
                .failAfterMaxAttempts(true)
                .build();

        return Retry.of("chapa-retry", retryCOnfig);
    }

    private static class RetryPredicate implements Predicate<Object> {

        @Override
        public boolean test(Object value) {
            Response<String> response = (Response<String>) value;
            return response.isSuccessful();
        }
    }
}
