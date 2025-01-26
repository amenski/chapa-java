package com.yaphet.chapa.client.provider;

import com.yaphet.chapa.utility.StringUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Provides retrofit client to make calls to chapa api.
 * <p>
 * This implementation will not retry on failures.
 *<pre>
 *  * Example usage:
 *  * public class CustomChapaClient implements IChapaClient {
 *  *
 *  *     private ChapaClientApi chapaClientApi;
 *  *     .
 *  *     .
 *  *     private void buildApiClient() {
 *  *          if (isBlank(baseUrl)) throw new ChapaException("Unable to create a client. Api baseUrl can't be empty");
 *  *          chapaClientApi = new DefaultRetrofitClientProvider.Builder().timeout(10000).baseUrl("https://chapa.example.com").build().createChapaClient();
 *  *     }
 *  * }
 *  * </pre>
 */
public class DefaultRetrofitClientProvider extends BaseRetrofitClientProvider {

    private static final long DEFAULT_TIMEOUT = 10000;

    private final String baseUrl;

    private DefaultRetrofitClientProvider(Builder builder) {
        super(builder.timeoutMillis);
        if (StringUtils.isBlank(builder.baseUrl)) {
            throw new IllegalArgumentException("Api baseUrl can't be null");
        }
        this.baseUrl = builder.baseUrl;
    }

    @Override
    public Retrofit provideRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    public static class Builder {
        private String baseUrl = "";
        private long timeoutMillis = DEFAULT_TIMEOUT;

        public Builder timeout(long timeoutMillis) {
            this.timeoutMillis = timeoutMillis;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public DefaultRetrofitClientProvider build() {
            return new DefaultRetrofitClientProvider(this);
        }
    }
}
