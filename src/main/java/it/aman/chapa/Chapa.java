package it.aman.chapa;

import it.aman.chapa.client.ChapaClient;
import it.aman.chapa.client.IChapaClient;
import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.Bank;
import it.aman.chapa.model.Customization;
import it.aman.chapa.model.InitializeResponseData;
import it.aman.chapa.model.PostData;
import it.aman.chapa.model.SubAccountDto;
import it.aman.chapa.model.SubAccountResponseData;
import it.aman.chapa.model.VerifyResponseData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static it.aman.chapa.utility.StringUtils.isBlank;
import static it.aman.chapa.utility.Util.isAnyNull;
import static it.aman.chapa.utility.Util.putIfNotNull;

/**
 * The <code>Chapa</code> class is the interface to Chapa API to initialize
 * a transaction, verify a transaction and create a sub-account for split payments.
 */
public class Chapa {

    private final IChapaClient chapaClient;
    private final String SECRETE_KEY;

    /**
     * @param secreteKey  A secrete key provided from Chapa.
     * @param chapaClient Implementation of {@link ChapaClient} interface.
     */
    public Chapa(IChapaClient chapaClient, String secreteKey) {
        this.chapaClient = chapaClient;
        this.SECRETE_KEY = secreteKey;
    }

    /**
     * @param builder A builder with client and secretKey
     */
    public Chapa(ChapaBuilder builder) {
        this.chapaClient = builder.client;
        this.SECRETE_KEY = builder.secretKey;
    }


    /**
     * <p>This method is used to initialize payment in Chapa. It is an overloaded method
     * of {@link #initialize(String)}.</p><br>
     *
     * @param postData Object of {@link PostData} instantiated with post fields.
     * @return An object of {@link InitializeResponseData} containing response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public InitializeResponseData initialize(PostData postData) throws ChapaException {
        Map<String, Object> fields = postData.getAsMap();

        putIfNotNull(fields, "return_url", postData.getReturnUrl());
        putIfNotNull(fields, "callback_url", postData.getCallbackUrl());
        putIfNotNull(fields, "subaccount[id]", postData.getSubAccountId());
        putIfNotNull(fields, "customization[logo]", Optional.ofNullable(postData.getCustomization()).map(Customization::getLogo).orElse(null));
        putIfNotNull(fields, "customization[title]", Optional.ofNullable(postData.getCustomization()).map(Customization::getTitle).orElse(null));
        putIfNotNull(fields, "customization[description]", Optional.ofNullable(postData.getCustomization()).map(Customization::getDescription).orElse(null));

        if(fields.isEmpty() || isAnyNull(fields, "amount", "currency", "tx_ref")) {
            throw new ChapaException("Wrong or empty payload");
        }
        return chapaClient.initialize(SECRETE_KEY, fields);
    }

    /**
     * <p>This method is used to initialize payment in Chapa. It is an overloaded method
     * of {@link #initialize(PostData)}.</p><br>
     *
     * @param jsonData A json string containing post fields.
     * @return An object of {@link InitializeResponseData} containing
     *         response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */

    public InitializeResponseData initialize(String jsonData) throws ChapaException {
        return chapaClient.initialize(SECRETE_KEY, jsonData);
    }

    /**
     * @param transactionRef A transaction reference which was associated
     *                       with tx_ref field in post data. This field uniquely
     *                       identifies a transaction.
     * @return An object of {@link VerifyResponseData} containing
     *        response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public VerifyResponseData verify(String transactionRef) throws ChapaException {
        if (isBlank(transactionRef)) {
            throw new ChapaException("Transaction reference can't be null or empty");
        }
        return chapaClient.verify(SECRETE_KEY, transactionRef);
    }

    /**
     * @return A list of {@link Bank} containing all banks supported by Chapa.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public List<Bank> getBanks() throws ChapaException {
        return chapaClient.getBanks(SECRETE_KEY);
    }

    /**
     * <p>This method is used to create a sub account in Chapa. It is an overloaded method
     * of {@link #createSubAccount(String)}.</p><br>
     *
     * @param subAccountDto An object of {@link SubAccountDto} containing sub-account details.
     * @return An object of {@link SubAccountResponseData} containing response data from Chapa API.
     * @throws ChapaException Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponseData createSubAccount(SubAccountDto subAccountDto) throws ChapaException {
        return chapaClient.createSubAccount(SECRETE_KEY, subAccountDto.getAsMap());
    }

    /**
     * <p>This method is used to create a sub account in Chapa. It is an overloaded method
     * of {@link #createSubAccount(SubAccountDto)}.</p><br>
     *
     * @param jsonData A json string containing sub account details.
     * @return An object of {@link SubAccountResponseData} containing response data from Chapa API.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponseData createSubAccount(String jsonData) throws Throwable {
       return chapaClient.createSubAccount(jsonData, SECRETE_KEY);
    }


    public static class ChapaBuilder {
        private IChapaClient client;
        private String secretKey;

        public ChapaBuilder client(IChapaClient client) {
            if(client == null) throw new IllegalArgumentException("Client can't be null");
            this.client = client;
            return this;
        }

        public ChapaBuilder client(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }
    }
}
