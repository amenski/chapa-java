package com.github.amenski.client.provider;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retrofit.RetryCallAdapter;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 *
 * Provides retrofit client to make calls to chapa api. <br>
 *
 * This implementation will retry calls to chapa api upto {@link RETRY_COUNT (3)} times.<br>
 *
 * Retry will be done with an <a href="https://en.wikipedia.org/wiki/Exponential_backoff">ExponentialBackoff</a> strategy.<br>
 *
 * Timeouts are all set to 10_000 millis <br>
 *
 *
 * <pre>
 * Example usage:
 * public class CustomChapaClient implements IChapaClient {
 *
 *     private String baseUrl = "https://api.chapa.co/v1/";
 *     private ChapaClientApi chapaClientApi;
 *     .
 *     .
 *     private void buildApiClient() {
 *          if (isBlank(baseUrl)) throw new ChapaException("Unable to create a client. Api baseUrl can't be empty");
 *          chapaClientApi = new RetrierRetrofitClientProvider().buildClient(ChapaClientApi.class, baseUrl);
 *     }
 * }
 * </pre>
 */
public class RetrierRetrofitClientProvider implements RetrofitClientProvider {

    private static final int RETRY_COUNT = 3;

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
                .addCallAdapterFactory(RetryCallAdapter.of(retry(timeOutMillis)))
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);
    }

    private static Retry retry(long initialIntervalMillis) {
        RetryConfig retryCOnfig = RetryConfig.custom()
                .maxAttempts(RETRY_COUNT)
                .intervalFunction(IntervalFunction.ofExponentialBackoff(initialIntervalMillis, 2))
                .retryOnResult(new RetryPredicate())
                .failAfterMaxAttempts(true)
                .build();

        return Retry.of("chapa-retry", retryCOnfig);
    }

    private static class RetryPredicate implements Predicate<Object> {

        @Override
        public boolean test(Object value) {
            Response<Object> response = (Response<Object>) value;
            return !response.isSuccessful();
        }
    }
}
