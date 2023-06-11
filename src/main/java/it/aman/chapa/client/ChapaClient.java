package it.aman.chapa.client;

import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.*;
import it.aman.chapa.utility.Util;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static it.aman.chapa.utility.StringUtils.isNotBlank;
import static it.aman.chapa.utility.Util.jsonToMap;

public class ChapaClient implements IChapaClient {

    private String baseUrl = "https://api.chapa.co/v1/";
//    private String baseUrl = "http://locahost:8080/";
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
    public InitializeResponseData initialize(final String secretKey, final String body) throws ChapaException {
        return this.initialize(secretKey, jsonToMap(body));
    }

    @Override
    public VerifyResponseData verify(final String secretKey, final String transactionReference) throws ChapaException {
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
            Response<ResponseBanks> response = getClient().banks("Bearer " + secretKey).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException("Unable to get bank details.");
            }
            return (response.body() != null && response.body().getData() != null) ? response.body().getData() : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Unable to get bank details.");
        }
    }

    @Override
    public SubAccountResponseData createSubAccount(final String secretKey, Map<String, Object> fields) throws ChapaException {
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
    public SubAccountResponseData createSubAccount(final String secretKey, final String body) throws ChapaException {
        return this.createSubAccount(secretKey, jsonToMap(body));
    }
    
    private ChapaClientApi getClient() {
        if (chapaClientApi == null && isNotBlank(baseUrl)) {
            chapaClientApi = RetrofitClientConfigurator.buildClient(baseUrl);
        }
        return chapaClientApi;
    }
}
