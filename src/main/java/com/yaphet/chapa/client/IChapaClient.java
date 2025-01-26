package com.yaphet.chapa.client;


import com.yaphet.chapa.exception.ChapaException;
import com.yaphet.chapa.model.InitializeResponse;
import com.yaphet.chapa.model.ResponseBanks;
import com.yaphet.chapa.model.SubAccountResponse;
import com.yaphet.chapa.model.VerifyResponse;

import java.util.Map;

/**
 * Chapa default retrofit client interface
 * <p>
 * Custom implementation can be created by implementing this interface
 */
public interface IChapaClient {

    /**
     * Initializes transaction
     *
     * @see <a href="https://developer.chapa.co/docs/accept-payments/">Chapa Dcoumentation</a>
     *
     * @param secretKey
     * @param fields
     * @return InitializeResponse
     * @throws ChapaException
     */
    InitializeResponse initialize(final String secretKey, Map<String, Object> fields) throws ChapaException;

    /**
     * An alias to {@link #initialize(String, Map)}
     *
     * @see <a href="https://developer.chapa.co/docs/accept-payments/">Chapa Dcoumentation</a>
     *
     * @param secretKey
     * @param body
     * @return InitializeResponse
     * @throws ChapaException
     */
    InitializeResponse initialize(final String secretKey, final String body) throws ChapaException;

    /**
     * Verify a transaction
     *
     * @see <a href="https://developer.chapa.co/docs/verify-payments/">Chapa Dcoumentation</a>
     *
     * @param secretKey
     * @param transactionReference
     * @return VerifyResponse
     * @throws ChapaException
     */
    VerifyResponse verify(String secretKey, String transactionReference) throws ChapaException;

    /**
     * Get all supported banks
     *
     * @see <a href="https://developer.chapa.co/docs/transfers/">Chapa Dcoumentation</a>
     *
     * @param secretKey
     * @return ResponseBanks
     * @throws ChapaException
     */
    ResponseBanks getBanks(String secretKey) throws ChapaException;

    /**
     *
     * Create sub account for split payment
     *
     * @see <a href="https://developer.chapa.co/docs/split-payment/">Chapa Dcoumentation</a>
     *
     * @param secretKey
     * @param fields
     * @return SubAccountResponse
     * @throws ChapaException
     */
    SubAccountResponse createSubAccount(String secretKey, Map<String, Object> fields) throws ChapaException;

    /**
     * Create sub account for split payment, an alternative method to {@link #createSubAccount(String, Map)}
     *
     * @see <a href="https://developer.chapa.co/docs/split-payment/">Chapa Dcoumentation</a>
     *
     * @param body
     * @param secretKey
     * @return SubAccountResponse
     * @throws ChapaException
     */
    SubAccountResponse createSubAccount(String secretKey, String body) throws ChapaException;
}
