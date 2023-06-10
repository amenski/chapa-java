package it.aman.chapa;

import it.aman.chapa.client.ChapaClient;
import it.aman.chapa.client.IChapaClient;
import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.*;
import it.aman.chapa.utility.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * The <code>Chapa</code> class is responsible for making GET and POST request to Chapa API to initialize
 * a transaction, verify a transaction and create a sub account.
 */
public class Chapa {

    private final IChapaClient chapaClient;
    private final String SECRETE_KEY;

    /**
     * @param secreteKey  A secrete key provided from Chapa.
     * @param chapaClient Implementation of {@link ChapaClient} interface.
     */
    public Chapa(ChapaClient chapaClient, String secreteKey) {
        this.chapaClient = chapaClient;
        this.SECRETE_KEY = secreteKey;
    }

    /**
     * @param builder A builder with client and secretKey set
     */
    public Chapa(ChapaBuilder builder) {
        this.chapaClient = builder.client;
        this.SECRETE_KEY = builder.secretKey;
    }


    /**
     * <p>This method is used to initialize payment in Chapa. It is an overloaded method
     * of {@link #initialize(String)}.</p><br>
     *
     * @param postData Object of {@link PostData} instantiated with
     *                 post fields.
     * @return An object of {@link InitializeResponseData} containing
     *          response data from Chapa API.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public InitializeResponseData initialize(PostData postData) throws Throwable { // TODO: consider creating custom exception handler and wrap any exception thrown by http client
        Map<String, Object> fields = postData.getAsMap();

       Customization customization = postData.getCustomization();
        String callbackUrl = postData.getCallbackUrl();
        String returnUrl = postData.getReturnUrl();
        String subAccountId = postData.getSubAccountId();

        if (StringUtils.isNotBlank(subAccountId)) {
            fields.put("subaccount[id]", subAccountId);
        }

        if (StringUtils.isNotBlank(callbackUrl)) {
            fields.put("callback_url", callbackUrl);
        }

        if (StringUtils.isNotBlank(returnUrl)) {
            fields.put("return_url", returnUrl);
        }

        if (customization != null) {
            // TODO: consider directly adding all values to fields map
            if (StringUtils.isNotBlank(customization.getTitle())) {
                fields.put("customization[title]", customization.getTitle());
            }

            if (StringUtils.isNotBlank(customization.getDescription())) {
                fields.put("customization[description]", customization.getDescription());
            }

            if (StringUtils.isNotBlank(customization.getLogo())) {
                fields.put("customization[logo]", customization.getLogo());
            }
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
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */

    public InitializeResponseData initialize(String jsonData) throws Throwable {
        return chapaClient.initialize(SECRETE_KEY, jsonData);
    }

    /**
     * @param transactionRef A transaction reference which was associated
     *                       with tx_ref field in post data. This field uniquely
     *                       identifies a transaction.
     * @return An object of {@link VerifyResponseData} containing
     *        response data from Chapa API.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public VerifyResponseData verify(String transactionRef) throws Throwable {
        if (!StringUtils.isNotBlank(transactionRef)) {
            throw new ChapaException("Transaction reference can't be null or empty");
        }
        return chapaClient.verify(SECRETE_KEY, transactionRef);
    }

    /**
     * @return A list of {@link Bank} containing all banks supported by Chapa.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public List<Bank> getBanks() throws Throwable {
        return chapaClient.getBanks(SECRETE_KEY);
    }

    /**
     * <p>This method is used to create a sub account in Chapa. It is an overloaded method
     * of {@link #createSubAccount(String)}.</p><br>
     *
     * @param subAccountDto An object of {@link SubAccountDto} containing
     *                   sub account details.
     * @return An object of {@link SubAccountResponseData} containing
     *        response data from Chapa API.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponseData createSubAccount(SubAccountDto subAccountDto) throws Throwable {
        return chapaClient.createSubAccount(SECRETE_KEY, subAccountDto.getAsMap());
    }

    /**
     * <p>This method is used to create a sub account in Chapa. It is an overloaded method
     * of {@link #createSubAccount(SubAccountDto)}.</p><br>
     *
     * @param jsonData A json string containing sub account details.
     * @return An object of {@link SubAccountResponseData} containing
     *       response data from Chapa API.
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
