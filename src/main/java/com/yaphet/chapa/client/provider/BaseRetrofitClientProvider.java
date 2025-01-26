package com.yaphet.chapa.client.provider;

import com.yaphet.chapa.client.ChapaClientApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

public abstract class BaseRetrofitClientProvider implements RetrofitClientProvider {

    private boolean debug;
    private HttpLoggingInterceptor loggingInterceptor;

    protected OkHttpClient httpClient;

    protected BaseRetrofitClientProvider(long timeoutMillis) {
        this.httpClient = createHttpClient(timeoutMillis);
    }

    @Override
    public RetrofitClientProvider setDebug(boolean debug) {
        if (this.debug != debug) {
            if (debug) {
                if (loggingInterceptor == null) {
                    loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                }
                httpClient = httpClient.newBuilder().addInterceptor(loggingInterceptor).build();
            } else {
                httpClient.newBuilder().interceptors().remove(loggingInterceptor);
                loggingInterceptor = null;
            }
        }
        this.debug = debug;
        return this;
    }

    @Override
    public RetrofitClientProvider setClient(OkHttpClient client) {
        if (client != null) {
            this.httpClient = client;
        }
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public ChapaClientApi create() {
        return provideRetrofitBuilder().create(ChapaClientApi.class);
    }

    private static OkHttpClient createHttpClient(long timeoutMillis) {
        return new OkHttpClient.Builder()
                .connectTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
                .readTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
                .writeTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
                .build();
    }
}
