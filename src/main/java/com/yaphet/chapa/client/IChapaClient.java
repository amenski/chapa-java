package com.yaphet.chapa.client;

import com.yaphet.chapa.exception.ChapaException;
import com.yaphet.chapa.model.Bank;
import com.yaphet.chapa.model.InitializeResponseData;
import com.yaphet.chapa.model.SubAccountResponseData;
import com.yaphet.chapa.model.VerifyResponseData;

import java.util.List;
import java.util.Map;

public interface IChapaClient {

    InitializeResponseData initialize(Map<String, Object> fields, final String secretKey) throws ChapaException;

    InitializeResponseData initialize(final String body, final String secretKey) throws ChapaException;

    VerifyResponseData verify(String transactionReference, String secretKey) throws ChapaException;

    List<Bank> getBanks(String secretKey) throws ChapaException;

    SubAccountResponseData createSubAccount(Map<String, Object> fields, String secretKey) throws ChapaException;

    SubAccountResponseData createSubAccount(String body, String secretKey) throws ChapaException;
}
