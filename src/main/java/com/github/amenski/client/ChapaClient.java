package com.github.amenski.client;

import com.github.amenski.model.InitializeResponseData;
import com.github.amenski.model.ResponseBanks;
import com.github.amenski.model.SubAccountResponseData;
import com.github.amenski.model.VerifyResponseData;
import com.github.amenski.utility.StringUtils;
import com.github.amenski.utility.Util;
import com.google.gson.Gson;
import com.github.amenski.exception.ChapaException;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.github.amenski.utility.StringUtils.isNotBlank;
import static com.github.amenski.utility.Util.jsonToMap;

/**
 * Chapa default client implementation
 */
public class ChapaClient implements IChapaClient {

    private String baseUrl = "https://api.chapa.co/v1/";
    private ChapaClientApi chapaClientApi;

    public ChapaClient() {
        //
    }

    public ChapaClient(final String url) {
        this.baseUrl = url;
    }

    @Override
    public InitializeResponseData initialize(final String secretKey, Map<String, Object> fields) throws ChapaException {
        try {
            Response<InitializeResponseData> response = getClient().initialize("Bearer " + secretKey, fields).execute();
            if (!response.isSuccessful()) {
                String message = extractErrorBody(response.errorBody(), "Unable to Initialize transaction.");
                throw new ChapaException(message);
            }
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException("Unable to Initialize transaction.");
        }
    }

    @Override
    public InitializeResponseData initialize(final String secretKey, final String body) throws ChapaException {
        return this.initialize(secretKey, jsonToMap(body));
    }

    @Override
    public VerifyResponseData verify(final String secretKey, final String transactionReference) throws ChapaException {
        try {
            Response<VerifyResponseData> response = getClient().verify("Bearer " + secretKey, transactionReference).execute();
            if (!response.isSuccessful()) {
                String message = extractErrorBody(response.errorBody(), "Unable to verify transaction.");
                throw new ChapaException(message);
            }
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException("Unable to verify transaction.");
        }
    }

    @Override
    public ResponseBanks getBanks(final String secretKey) {
        try {
            Response<ResponseBanks> response = getClient().banks("Bearer " + secretKey).execute();
            if (!response.isSuccessful()) {
                String message = extractErrorBody(response.errorBody(), "Unable to get bank details.");
                throw new ChapaException(message);
            }
            return response.body() != null? response.body() : null;
        } catch (ChapaException | IOException e) {
            throw new RuntimeException("Unable to get bank details.");
        }
    }

    @Override
    public SubAccountResponseData createSubAccount(final String secretKey, Map<String, Object> fields) throws ChapaException {
        try {
            Response<SubAccountResponseData> response = getClient().createSubAccount("Bearer " + secretKey, fields).execute();
            if (!response.isSuccessful()) {
                String message = extractErrorBody(response.errorBody(), "Unable to create sub account.");
                throw new ChapaException(message);
            }
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create sub account.");
        }
    }

    @Override
    public SubAccountResponseData createSubAccount(final String secretKey, final String body) throws ChapaException {
        return this.createSubAccount(secretKey, jsonToMap(body));
    }
    
    private ChapaClientApi getClient() {
        if (chapaClientApi == null && isNotBlank(baseUrl)) {
            chapaClientApi = RetrofitClientConfigurator.buildClient(baseUrl);
        }
        return chapaClientApi;
    }

    private String extractErrorBody(ResponseBody responseBody, String message) {
        return Optional.ofNullable(responseBody).map(t -> {
            try {
                return t.string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(message);
    }
}
