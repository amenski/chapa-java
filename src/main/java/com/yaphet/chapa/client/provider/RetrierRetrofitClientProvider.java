package com.yaphet.chapa.client.provider;

import com.yaphet.chapa.utility.StringUtils;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retrofit.RetryCallAdapter;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides retrofit client to make calls to chapa api. <br>
 * <p>
 * This implementation will retry calls to chapa api upto 3 (default) times. You can set the number of retries too.<br>
 * <p>
 * Retry will be done with an <a href="https://en.wikipedia.org/wiki/Exponential_backoff">ExponentialBackoff</a> strategy.<br>
 * <p>
 * Timeouts are all set to 10_000 millis <br>
 *
 *
 * <pre>
 * Example usage:
 * public class CustomChapaClient implements IChapaClient {
 *
 *     private ChapaClientApi chapaClientApi;
 *     .
 *     .
 *     private void buildApiClient() {
 *          if (isBlank(baseUrl)) throw new ChapaException("Unable to create a client. Api baseUrl can't be empty");
 *          chapaClientApi = new RetrierRetrofitClientProvider.Builder().timeout(10000).retryCount(3).baseUrl("https://chapa.example.com").build().create();
 *     }
 * }
 * </pre>
 */
public class RetrierRetrofitClientProvider extends BaseRetrofitClientProvider {

    private static final long DEFAULT_TIMEOUT = 10000;
    private static final int DEFAULT_RETRY_COUNT = 2;
    private static final double BACKOFF_MULTIPLIER = 1.5;

    private final String baseUrl;
    private final RetryConfig retryConfig;

    private RetrierRetrofitClientProvider(Builder builder) {
        super(builder.timeoutMillis);
        super.setClient(builder.client);
        super.setDebug(builder.debug);
        this.baseUrl = builder.baseUrl;
        this.retryConfig = createRetryConfig(builder.timeoutMillis, builder.maxRetries);
    }

    private static RetryConfig createRetryConfig(long initialInterval, int maxAttempts) {
        return RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .intervalFunction(IntervalFunction.ofExponentialBackoff(initialInterval, BACKOFF_MULTIPLIER))
                .retryOnResult(response -> response instanceof Response<?> && !((Response<?>) response).isSuccessful())
                .failAfterMaxAttempts(true)
                .build();
    }

    @Override
    public Retrofit provideRetrofitBuilder() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RetryCallAdapter.of(Retry.of("chapa-retry", retryConfig)))
                .baseUrl(this.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    public static class Builder {
        private String baseUrl = "";
        private long timeoutMillis = DEFAULT_TIMEOUT;
        private int maxRetries = DEFAULT_RETRY_COUNT;
        private boolean debug;
        private OkHttpClient client;

        public Builder timeout(long timeoutMillis) {
            this.timeoutMillis = timeoutMillis;
            return this;
        }

        public Builder maxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            if (StringUtils.isBlank(baseUrl)) {
                throw new IllegalArgumentException("Api baseUrl can't be null");
            }
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public RetrierRetrofitClientProvider build() {
            return new RetrierRetrofitClientProvider(this);
        }
    }
}
