package it.aman.chapa.client;

import com.google.gson.Gson;
import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.InitializeResponseData;
import it.aman.chapa.model.ResponseBanks;
import it.aman.chapa.model.SubAccountResponseData;
import it.aman.chapa.model.VerifyResponseData;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
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
            Response<InitializeResponseData> response = getClient().initialize("Bearer " + secretKey, fields).execute();
            if (!response.isSuccessful()) {
                return extractErrorBody(response.errorBody(), InitializeResponseData.class, "Unable to Initialize transaction.");
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
                return extractErrorBody(response.errorBody(), VerifyResponseData.class, "Unable to verify transaction.");
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
                return extractErrorBody(response.errorBody(), ResponseBanks.class, "Unable to get bank details.");
            }
            return response.body() != null? response.body() : null;
        } catch (IOException e) {
            throw new RuntimeException("Unable to get bank details.");
        }
    }

    @Override
    public SubAccountResponseData createSubAccount(final String secretKey, Map<String, Object> fields) throws ChapaException {
        try {
            Response<SubAccountResponseData> response = getClient().createSubAccount("Bearer " + secretKey, fields).execute();
            if (!response.isSuccessful()) {
                return extractErrorBody(response.errorBody(), SubAccountResponseData.class, "Unable to create sub account.");
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

    private <T> T extractErrorBody(ResponseBody responseBody, Class<T> cls, String message) {
        String body = Optional.ofNullable(responseBody).map(t -> {
            try {
                return t.string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(message);

        return new Gson().fromJson(body, cls);
    }
}
