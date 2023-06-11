package it.aman.chapa.client;

import com.google.gson.Gson;
import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.Bank;
import it.aman.chapa.model.InitializeResponseData;
import it.aman.chapa.model.SubAccountResponseData;
import it.aman.chapa.model.VerifyResponseData;
import it.aman.chapa.utility.Util;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static it.aman.chapa.utility.StringUtils.isNotBlank;

public class ChapaClient implements IChapaClient {

    private String baseUrl = "https://api.chapa.co/v1";
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
        return this.initialize(secretKey, new Gson().fromJson(body, Map.class));
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
        return this.createSubAccount(secretKey, new Gson().fromJson(body, Map.class));
    }
    
    private ChapaClientApi getClient() {
        if (chapaClientApi == null && isNotBlank(baseUrl)) {
            chapaClientApi = RetrofitClientConfigurator.buildClient(baseUrl);
        }
        return chapaClientApi;
    }
}
