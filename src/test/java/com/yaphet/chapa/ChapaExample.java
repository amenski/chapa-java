package com.yaphet.chapa;

import com.yaphet.chapa.client.ChapaClient;
import com.yaphet.chapa.client.ChapaClientApi;
import com.yaphet.chapa.client.provider.RetrierRetrofitClientProvider;
import com.yaphet.chapa.exception.ChapaException;
import com.yaphet.chapa.model.Customization;
import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.ResponseBanks;
import com.yaphet.chapa.model.SplitTypeEnum;
import com.yaphet.chapa.model.SubAccountDto;

import java.util.UUID;

public class ChapaExample {

    public static void main(String[] args) throws ChapaException {
        ChapaClientApi chapaClientRetrofit = new RetrierRetrofitClientProvider.Builder()
                .baseUrl("https://api.chapa.co/v1/")
                .maxRetries(3)
                .timeout(10000)
                .build()
                .create();

        Chapa chapa = new Chapa.Builder()
                .client(new ChapaClient(chapaClientRetrofit))
                .secretKey("CHASECK_TEST-....")
                .build();

        Customization customization = new Customization()
                .setTitle("E-commerce")
                .setDescription("It is time to pay")
                .setLogo("https://mylogo.com/log.png");
        PostData postData = new PostData()
                .setAmount("100")
                .setCurrency("ETB")
                .setFirstName("Abebe")
                .setLastName("Bikila")
                .setEmail("abebe@bikila.com")
                .setTxRef(UUID.randomUUID().toString())
                .setCallbackUrl("https://chapa.co")
                .setReturnUrl("https://chapa.co")
                .setSubAccountId("testSubAccountId")
                .setCustomization(customization);

        SubAccountDto subAccountDto = new SubAccountDto()
                .setBusinessName("Abebe Suq")
                .setAccountName("Abebe Bikila")
                .setAccountNumber("0123456789")
                .setBankCode("853d0598-9c01-41ab-ac99-48eab4da1513")
                .setSplitType(SplitTypeEnum.PERCENTAGE)
                .setSplitValue(0.2);

        // list of banks
        ResponseBanks banks = chapa.getBanks();
        if ((banks == null || banks.getData() == null)) {
            System.out.println("Bank response: " + banks);
        } else {
            banks.getData().forEach(System.out::println);
        }
        // create subaccount
        System.out.println("Create SubAccount response: " + chapa.createSubAccount(subAccountDto));
        // initialize payment
        System.out.println("Initialize response: " + chapa.initialize(postData));
        // verify payment
        System.out.println("Verify response: " + chapa.verify(postData.getTxRef()));
    }
}