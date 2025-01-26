package com.yaphet.chapa.client;

import com.yaphet.chapa.exception.ChapaException;
import com.yaphet.chapa.model.InitializeResponse;
import com.yaphet.chapa.model.ResponseBanks;
import com.yaphet.chapa.model.SubAccountResponse;
import com.yaphet.chapa.model.VerifyResponse;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.yaphet.chapa.utility.Util.jsonToMap;

public class ChapaClient implements IChapaClient {

    public static final String BEARER = "Bearer ";

    protected ChapaClientApi chapaClientApi;

    public ChapaClient(ChapaClientApi client) {
        if (client == null) {
            throw new IllegalArgumentException("Chapa client can't be null");
        }
        this.chapaClientApi = client;
    }

    @Override
    public InitializeResponse initialize(final String secretKey, Map<String, Object> fields) throws ChapaException {
        try {
            Response<InitializeResponse> response = chapaClientApi.initialize(BEARER + secretKey, fields).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException(extractErrorMessageOrDefault(response.errorBody(), "Unable to Initialize transaction."));
            }
            return response.body();
        } catch (Exception e) {
            throw new ChapaException("Unable to Initialize transaction.", e);
        }
    }

    @Override
    public InitializeResponse initialize(final String secretKey, final String body) throws ChapaException {
        return this.initialize(secretKey, jsonToMap(body));
    }

    @Override
    public VerifyResponse verify(final String secretKey, final String transactionReference) throws ChapaException {
        try {
            Response<VerifyResponse> response = chapaClientApi.verify(BEARER + secretKey, transactionReference).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException(extractErrorMessageOrDefault(response.errorBody(), "Unable to verify transaction."));
            }
            return response.body();
        } catch (Exception e) {
            throw new ChapaException("Unable to verify transaction.", e);
        }
    }

    @Override
    public ResponseBanks getBanks(final String secretKey) throws ChapaException {
        try {
            Response<ResponseBanks> response = chapaClientApi.banks(BEARER + secretKey).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException(extractErrorMessageOrDefault(response.errorBody(), "Unable to get bank details."));
            }
            return response.body() != null ? response.body() : null;
        } catch (Exception e) {
            throw new ChapaException("Unable to get bank details.", e);
        }
    }

    @Override
    public SubAccountResponse createSubAccount(final String secretKey, Map<String, Object> fields) throws ChapaException {
        try {
            Response<SubAccountResponse> response = chapaClientApi.createSubAccount(BEARER + secretKey, fields).execute();
            if (!response.isSuccessful()) {
                throw new ChapaException(extractErrorMessageOrDefault(response.errorBody(), "Unable to create sub account."));
            }
            return response.body();
        } catch (Exception e) {
            throw new ChapaException("Unable to create sub account.", e);
        }
    }

    @Override
    public SubAccountResponse createSubAccount(final String secretKey, final String body) throws ChapaException {
        return this.createSubAccount(secretKey, jsonToMap(body));
    }

    private String extractErrorMessageOrDefault(ResponseBody errorMessage, String defaultMessage) {
        return Optional.ofNullable(errorMessage).map(t -> {
            try {
                return t.string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).orElse(defaultMessage);
    }

}
