package com.yaphet.chapa;

import com.yaphet.chapa.client.ChapaClient;
import com.yaphet.chapa.exception.ChapaException;
import com.yaphet.chapa.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yaphet.chapa.utility.StringUtils.isNotBlank;

/**
 * The <code>Chapa</code> class is responsible for making GET and POST request to Chapa API to initialize
 * a transaction, verify a transaction and create a sub account.
 */
public class Chapa {

    private final ChapaClient chapaClient;
    private final String SECRETE_KEY;

    /**
     * @param secreteKey A secrete key provided from Chapa.
     */
    public Chapa(String secreteKey) { // TODO: consider deprecating this since it makes it hard to test this class
        this.SECRETE_KEY = secreteKey;
        this.chapaClient = new ChapaClient("https://api.chapa.co/v1");
    }

    /**
     * @param secreteKey  A secrete key provided from Chapa.
     * @param chapaClient Implementation of {@link ChapaClient} interface.
     */
    public Chapa(ChapaClient chapaClient, String secreteKey) {
        this.chapaClient = chapaClient;
        this.SECRETE_KEY = secreteKey;
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
        Map<String, Object> fields = new HashMap<>();
        fields.put("amount", postData.getAmount().toString());
        fields.put("currency", postData.getCurrency());
        fields.put("email", postData.getEmail());
        fields.put("first_name", postData.getFirstName());
        fields.put("last_name", postData.getLastName());
        fields.put("tx_ref", postData.getTxRef());

       Customization customization = postData.getCustomization();
        String callbackUrl = postData.getCallbackUrl();
        String returnUrl = postData.getReturnUrl();
        String subAccountId = postData.getSubAccountId();

        if (isNotBlank(subAccountId)) {
            fields.put("subaccount[id]", subAccountId);
        }

        if (isNotBlank(callbackUrl)) {
            fields.put("callback_url", callbackUrl);
        }

        if (isNotBlank(returnUrl)) {
            fields.put("return_url", returnUrl);
        }

        if (customization != null) {
            // TODO: consider directly adding all values to fields map
            if (isNotBlank(customization.getTitle())) {
                fields.put("customization[title]", customization.getTitle());
            }

            if (isNotBlank(customization.getDescription())) {
                fields.put("customization[description]", customization.getDescription());
            }

            if (isNotBlank(customization.getLogo())) {
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
        if (!isNotBlank(transactionRef)) {
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
     * @param subAccount An object of {@link SubAccount} containing
     *                   sub account details.
     * @return An object of {@link SubAccountResponseData} containing
     *        response data from Chapa API.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponseData createSubAccount(SubAccount subAccount) throws Throwable {
        Map<String, Object> fields = new HashMap<>();
        fields.put("business_name", subAccount.getBusinessName());
        fields.put("account_name", subAccount.getAccountName());
        fields.put("account_number", subAccount.getAccountNumber());
        fields.put("bank_code", subAccount.getBankCode());
        fields.put("split_type", subAccount.getSplitType().name().toLowerCase());
        fields.put("split_value", subAccount.getSplitValue());

        return chapaClient.createSubAccount(SECRETE_KEY, fields);
    }

    /**
     * <p>This method is used to create a sub account in Chapa. It is an overloaded method
     * of {@link #createSubAccount(SubAccount)}.</p><br>
     *
     * @param jsonData A json string containing sub account details.
     * @return An object of {@link SubAccountResponseData} containing
     *       response data from Chapa API.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponseData createSubAccount(String jsonData) throws Throwable {
       return chapaClient.createSubAccount(jsonData, SECRETE_KEY);
    }

}
