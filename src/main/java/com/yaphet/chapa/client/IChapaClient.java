package com.yaphet.chapa.client;

import com.yaphet.chapa.exception.ChapaException;
import com.yaphet.chapa.model.Bank;
import com.yaphet.chapa.model.InitializeResponseData;
import com.yaphet.chapa.model.SubAccountResponseData;
import com.yaphet.chapa.model.VerifyResponseData;

import java.util.List;
import java.util.Map;

public interface IChapaClient {

    InitializeResponseData initialize(final String secretKey, Map<String, Object> fields) throws ChapaException;

    InitializeResponseData initialize(final String secretKey, final String body) throws ChapaException;

    VerifyResponseData verify(String secretKey, String transactionReference) throws ChapaException;

    List<Bank> getBanks(String secretKey) throws ChapaException;

    SubAccountResponseData createSubAccount(String secretKey, Map<String, Object> fields) throws ChapaException;

    SubAccountResponseData createSubAccount(String body, String secretKey) throws ChapaException;
}
