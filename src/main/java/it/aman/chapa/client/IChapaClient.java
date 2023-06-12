package it.aman.chapa.client;

import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.*;

import java.util.Map;

public interface IChapaClient {

    InitializeResponseData initialize(final String secretKey, Map<String, Object> fields) throws ChapaException;

    InitializeResponseData initialize(final String secretKey, final String body) throws ChapaException;

    VerifyResponseData verify(String secretKey, String transactionReference) throws ChapaException;

    ResponseBanks getBanks(String secretKey) throws ChapaException;

    SubAccountResponseData createSubAccount(String secretKey, Map<String, Object> fields) throws ChapaException;

    SubAccountResponseData createSubAccount(String body, String secretKey) throws ChapaException;
}
