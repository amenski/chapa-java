package com.yaphet.chapa;

import com.yaphet.chapa.client.IChapaClient;
import com.yaphet.chapa.exception.ChapaException;
import com.yaphet.chapa.model.Bank;
import com.yaphet.chapa.model.Customization;
import com.yaphet.chapa.model.InitializeResponse;
import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.ResponseBanks;
import com.yaphet.chapa.model.SubAccountDto;
import com.yaphet.chapa.model.SubAccountResponse;
import com.yaphet.chapa.model.VerifyResponse;
import com.yaphet.chapa.utility.StringUtils;

import java.util.Map;
import java.util.Optional;

import static com.yaphet.chapa.utility.StringUtils.EMPTY;
import static com.yaphet.chapa.utility.Util.isAnyNull;
import static com.yaphet.chapa.utility.Util.putIfNotNull;

/**
 * The <code>Chapa</code> class is responsible for making GET and POST request to Chapa API to initialize
 * a transaction, verify a transaction and create a sub account.
 */
public class Chapa {

    private final String secreteKey;

    private final IChapaClient chapaClient;

    private Chapa(Builder builder) {
        this.chapaClient = builder.client;
        this.secreteKey = builder.secretKey;
    }

    /**
     * <p>This method is used to initialize payment in Chapa. It is an overloaded method
     * of {@link #initialize(String)}.</p><br>
     *
     * @param postData Object of {@link PostData} instantiated with post fields.
     * @return An object of {@link InitializeResponse} containing response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public InitializeResponse initialize(PostData postData) throws ChapaException {
        Map<String, Object> fields = postData.getAsMap();

        putIfNotNull(fields, "return_url", postData.getReturnUrl());
        putIfNotNull(fields, "callback_url", postData.getCallbackUrl());
        putIfNotNull(fields, "subaccounts[id]", postData.getSubAccountId());
        putIfNotNull(fields, "customization[title]", Optional.ofNullable(postData.getCustomization()).map(Customization::getTitle).orElse(EMPTY));
        putIfNotNull(fields, "customization[description]", Optional.ofNullable(postData.getCustomization()).map(Customization::getDescription).orElse(EMPTY));
        putIfNotNull(fields, "phone_number", Optional.ofNullable(postData.getPhoneNumber()).orElse(EMPTY));

        if (fields.isEmpty() || isAnyNull(fields, "amount", "currency", "tx_ref")) {
            throw new ChapaException("Wrong or empty payload");
        }
        return chapaClient.initialize(secreteKey, fields);
    }

    /**
     * <p>This method is used to initialize payment in Chapa. It is an overloaded method
     * of {@link #initialize(PostData)}.</p><br>
     *
     * @param jsonData A json string containing post fields.
     * @return An object of {@link InitializeResponse} containing
     * response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */

    public InitializeResponse initialize(String jsonData) throws ChapaException {
        return chapaClient.initialize(secreteKey, jsonData);
    }

    /**
     * @param transactionRef A transaction reference which was associated
     *                       with tx_ref field in post data. This field uniquely
     *                       identifies a transaction.
     * @return An object of {@link VerifyResponse} containing
     * response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public VerifyResponse verify(String transactionRef) throws ChapaException {
        if (StringUtils.isBlank(transactionRef)) {
            throw new ChapaException("Transaction reference can't be null or empty");
        }
        return chapaClient.verify(secreteKey, transactionRef);
    }

    /**
     * @return A list of {@link Bank} containing all banks supported by Chapa.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public ResponseBanks getBanks() throws ChapaException {
        return chapaClient.getBanks(secreteKey);
    }

    /**
     * <p>This method is used to create a sub account in Chapa. It is an overloaded method
     * of {@link #createSubAccount(String)}.</p><br>
     *
     * @param subAccountDto An object of {@link SubAccountDto} containing sub-account details.
     * @return An object of {@link SubAccountResponse} containing response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponse createSubAccount(SubAccountDto subAccountDto) throws ChapaException {
        return chapaClient.createSubAccount(secreteKey, subAccountDto.getAsMap());
    }

    /**
     * <p>This method is used to create a sub account in Chapa. It is an overloaded method
     * of {@link #createSubAccount(SubAccountDto)}.</p><br>
     *
     * @param jsonData A json string containing sub account details.
     * @return An object of {@link SubAccountResponse} containing response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponse createSubAccount(String jsonData) throws ChapaException {
        return chapaClient.createSubAccount(secreteKey, jsonData);
    }

    public static class Builder {
        private IChapaClient client;
        private String secretKey;

        public Builder client(IChapaClient client) {
            if (client == null) {
                throw new IllegalArgumentException("Client can't be null");
            }
            this.client = client;
            return this;
        }

        public Builder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public Chapa build() {
            return new Chapa(this);
        }
    }

}
