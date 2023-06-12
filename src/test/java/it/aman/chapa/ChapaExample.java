package it.aman.chapa;

import it.aman.chapa.client.ChapaClient;
import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ChapaExample {

    public static void main(String[] args) throws ChapaException {
        Chapa chapa = new Chapa.ChapaBuilder()
                .client(new ChapaClient())
                .secretKey("CHASECK_TEST-gxAFoWyYjj1eLPy1qlfwezhxPyZc7bKr")
                .build();

        Customization customization = new Customization()
                .setTitle("E-commerce")
                .setDescription("It is time to pay")
                .setLogo("https://mylogo.com/log.png");
        PostData postData = new PostData()
                .setAmount(new BigDecimal("100"))
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
//        ResponseBanks banks = chapa.getBanks();
//        System.out.println("Bank list: " + banks);
//         create subaccount
//        SubAccountResponseData res = chapa.createSubAccount(subAccountDto);
//        System.out.println("Create SubAccount response: " + res);
//        // initialize payment
        System.out.println("Initialize response: " + chapa.initialize(postData));
//        // verify payment
        System.out.println("Verify response: " + chapa.verify(postData.getTxRef()));
    }
}