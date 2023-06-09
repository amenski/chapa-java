package com.yaphet.chapa;

import com.yaphet.chapa.client.ChapaClientApi;
import retrofit2.Call;

import java.util.Map;

public class ChapaClient implements ChapaClientApi {
    @Override
    public Call<String> initialize(String authorizationHeader, Map<String, Object> body) {
        return null;
    }

    @Override
    public Call<String> verify(String authorizationHeader, String transactionReference) {
        return null;
    }

    @Override
    public Call<String> banks(String authorizationHeader) {
        return null;
    }

    @Override
    public Call<String> createSubAccount(String authorizationHeader, Map<String, Object> body) {
        return null;
    }
}
