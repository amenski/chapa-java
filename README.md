```
 ,-----. ,--.                                             ,--.                              
'  .--./ |  ,---.   ,--,--.  ,---.   ,--,--. ,-----.      |  |  ,--,--. ,--.  ,--.  ,--,--. 
|  |     |  .-.  | ' ,-.  | | .-. | ' ,-.  | '-----' ,--. |  | ' ,-.  |  \  `'  /  ' ,-.  | 
'  '--'\ |  | |  | \ '-'  | | '-' ' \ '-'  |         |  '-'  / \ '-'  |   \    /   \ '-'  | 
 `-----' `--' `--'  `--`--' |  |-'   `--`--'          `-----'   `--`--'    `--'     `--`--'
```

[![BUILD](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/badge.svg)](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/yaphet17/chapa-java.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/yaphet17/chapa-java/context:java) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 

Unofficial Java package for Chapa Payment Gateway.

## Features
- You can now implement `IChapaClient` interface and create your own custom implementation
  to use your favorite HTTP client.
- Includes split payment feature added by Chapa. You can now get list of supported banks, create
  sub-account and perform a split payment. See [Split Payment](https://developer.chapa.co/docs/split-payment/) documentation for more details.
- Additional utility methods to help you to generate a convenient token for your transactions, to map json string
  to `PostData` object etc.

## Table of Contents
1. [Documentation](#documentation)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Contribution](#contribution)
5. [Example](#example)
6. [License](#license)

## Documentation
Visit official [Chapa's API Documentation](https://developer.chapa.co/docs)
## Installation
 Add the below maven dependency to your `pom.xml` file.
```xml
    <dependency>
      <groupId>it.aman</groupId>
      <artifactId>chapa</artifactId>
      <version>1.0.0</version>
    </dependency>
```
Or add the below gradle dependency to your `build.gradle` file.
```groovy
    implementation 'it.aman:chapa:1.0.0'
```

## Usage

> **Note** : This doc might not fully cover chapa-api. Please refer to the chapa developer doc and let me know (or create a PR) if you find anything. Thanks.


Instantiate a `Chapa` class.
```java       
Chapa chapa = new Chapa.ChapaBuilder()
        .client(null)
        .secretKey("secret-key")
        .build()
```
Or if you want to use your own implementation of `IChapaClient` interface.
```java
public class MyCustomChapaClient implements IChapaClient {
  ...
}

Chapa chapa = new Chapa(new MyCustomChapaClient(), "secrete-key");
```
Note: `MyCustomChapaClient` must implement `IChapaClient` interface.

To initialize a transaction, you simply need to specify your information by either using our `PostData` class.

```java
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
        
        ...
        
        chapa.initialize(postData);
```
Or, as a string JSON data.
```java 
String postDataString = " { " +
        "'amount': '100', " +
        "'currency': 'ETB'," +
        "'email': 'abebe@bikila.com'," +
        "'first_name': 'Abebe'," +
        "'last_name': 'Bikila'," +
        "'tx_ref': 'tx-myecommerce12345'," +
        "'callback_url': 'https://chapa.co'," +
        "'subaccount[id]': 'ACCT_xxxxxxxxx'," +
        "'customizations':{" +
        "       'customization[title]':'E-commerce'," +
        "       'customization[description]':'It is time to pay'," +
        "       'customization[logo]':'https://mylogo.com/log.png'" +
        "   }" +
        " }";

        chapa.initialize(postDataString)
```
Intitialize payment
```java
InitializeResponseData responseData = chapa.initialize(postData) 
```
Verify payment
```java
VerifyResponseData actualResponseData = chapa.verify("tx-ref"); 
```
Get list of banks
```java
List<Bank> banks = chapa.getBanks();
```
To create a subaccount, you can specify your information by either using our `Subaccount` class.
```java
SubAccount subAccount = new SubAccountDto()
        .setBusinessName("Abebe Suq")
        .setAccountName("Abebe Bikila")
        .setAccountNumber("0123456789")
        .setBankCode("001")
        .setSplitType(SplitTypeEnum.PERCENTAGE)
        .setSplitValue(0.2);
        
        ...
        
        SubAccountResponseData response = chapa.createSubAccount(subAccountDto);
```
Or, you can use a string JSON data.
```java
String subAccount = " { " +
        "'business_name': 'Abebe Suq', " +
        "'account_name': 'Abebe Bikila'," +
        "'account_number': '0123456789'," +
        "'bank_code': '96e41186-29ba-4e30-b013-2ca36d7e7025'," +
        "'split_type': 'percentage'," +
        "'split_value': '0.2'" +
        " }";

        ...

        SubAccountResponseData actualResponse = chapa.createSubAccount(subAccount);
```
Create subaccount
```java
 SubAccountResponseData actualResponse = chapa.createSubAccount(subAccountDto);
```
## Example
```java
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.yaphet17.chapa.Chapa;
import io.github.yaphet17.chapa.PostData;
import io.github.yaphet17.chapa.SubAccount;
import io.github.yaphet17.chapa.SplitType;
import io.github.yaphet17.chapa.Bank;

public class ChapaExample {
    public static void main(String[] args) {
      Chapa chapa = new Chapa("your-secrete-key");
    
      Map<String, String> customizations = new HashMap<>();
      customizations.put("customization[title]", "E-commerce");
      customizations.put("customization[description]", "It is time to pay");
      customizations.put("customization[logo]", "https://mylogo.com/log.png");
      PostData postData = PostData.builder()
              .amount(new BigDecimal("100"))
              .currency("ETB")
              .firstName("Abebe")
              .lastName("Bikila")
              .email("abebe@bikila.com")
              .txRef(Util.generateToken())
              .callbackUrl("https://chapa.co")
              .subAccountId("ACCT_xxxxxxxxx")
              .customizations(customizations)
              .build();
      
      SubAccount subAccount = SubAccount.builder()
              .businessName("Abebe Suq")
              .accountName("Abebe Bikila")
              .accountNumber("0123456789")
              .bankCode("96e41186-29ba-4e30-b013-2ca36d7e7025")
              .splitTypeEnum(SplitType.PERCENTAGE) // or SplitTypeEnum.FLAT
              .splitValue(0.2)
              .build();

      // list of banks
      List<Bank> banks = chapa.banks();
      banks.forEach(bank -> System.out.println("Bank name: " + bank.getName() + " Bank Code: " + bank.getId()));
      // create subaccount
      System.out.println("Create SubAccount response: " + chapa.createSubAccount(subAccount).asString());
      // initialize payment
      System.out.println("Initialize response: " + chapa.initialize(postData).asString());
      // verify payment
      System.out.println("Verify response: " + chapa.verify(postData.getTxRef()).asString());
      }
 }
```
## Contribution
If you find any bug or have any suggestion, please feel free to open an issue or pull request.

## License
This open source library is licensed under the terms of the MIT License.

Enjoy!
