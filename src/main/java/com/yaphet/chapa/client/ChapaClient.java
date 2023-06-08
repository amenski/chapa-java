package com.yaphet.chapa.client;

import com.google.gson.Gson;
import com.yaphet.chapa.exception.ChapaException;
import com.yaphet.chapa.model.Bank;
import com.yaphet.chapa.model.InitializeResponseData;
import com.yaphet.chapa.model.SubAccountResponseData;
import com.yaphet.chapa.model.VerifyResponseData;
import com.yaphet.chapa.utility.Util;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.yaphet.chapa.utility.StringUtils.isNotBlank;

public class ChapaClient implements IChapaClient {

    private final String baseUrl;
    private ChapaClientApi chapaClientApi;

    public ChapaClient(final String url) {
        this.baseUrl = url;
    }

    @Override
    public InitializeResponseData initialize(Map<String, Object> fields, final String secretKey) throws ChapaException {
        try {
            Response<String> response = getClient().initialize("Bearer " + secretKey, fields).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException("Unable to Initialize transaction.");
            }
            return Util.jsonToInitializeResponseData(response.body())
                    .setStatusCode(response.code())
                    .setRawJson(response.body());
        } catch (IOException e) {
            throw new RuntimeException("Unable to Initialize transaction.");
        }
    }

    @Override
    public InitializeResponseData initialize(final String body, final String secretKey) throws ChapaException {
        return this.initialize(new Gson().fromJson(body, Map.class), secretKey);
    }

    @Override
    public VerifyResponseData verify(final String transactionReference, final String secretKey) throws ChapaException {
        try {
            Response<String> response = getClient().verify("Bearer " + secretKey, transactionReference).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException("Unable to verify transaction.");
            }
            return Util.jsonToVerifyResponseData(response.body())
                    .setStatusCode(response.code())
                    .setRawJson(response.body());
        } catch (IOException e) {
            throw new RuntimeException("Unable to verify transaction.");
        }
    }

    @Override
    public List<Bank> getBanks(final String secretKey) throws ChapaException {
        try {
            Response<String> response = getClient().banks("Bearer " + secretKey).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException("Unable to get bank details.");
            }
            return Util.extractBanks(response.body());
        } catch (IOException e) {
            throw new RuntimeException("Unable to get bank details.");
        }
    }

    @Override
    public SubAccountResponseData createSubAccount(Map<String, Object> fields, final String secretKey) throws ChapaException {
        try {
            Response<String> response = getClient().createSubAccount("Bearer " + secretKey, fields).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException("Unable to create sub account.");
            }
            return Util.jsonToSubAccountResponseData(response.body())
                    .setRawJson(response.body())
                    .setStatusCode(response.code());
        } catch (IOException e) {
            throw new RuntimeException("Unable to create sub account.");
        }
    }

    @Override
    public SubAccountResponseData createSubAccount(final String body, final String secretKey) throws ChapaException {
        return this.createSubAccount(new Gson().fromJson(body, Map.class), secretKey);
    }
    
    private ChapaClientApi getClient() {
        if (chapaClientApi == null && isNotBlank(baseUrl)) {
            chapaClientApi = RetrofitClientConfigurator.buildClient(ChapaClientApi.class, baseUrl);
        }
        return chapaClientApi;
    }
}
