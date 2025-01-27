```
 ,-----. ,--.                                             ,--.                              
'  .--./ |  ,---.   ,--,--.  ,---.   ,--,--. ,-----.      |  |  ,--,--. ,--.  ,--.  ,--,--. 
|  |     |  .-.  | ' ,-.  | | .-. | ' ,-.  | '-----' ,--. |  | ' ,-.  |  \  `'  /  ' ,-.  | 
'  '--'\ |  | |  | \ '-'  | | '-' ' \ '-'  |         |  '-'  / \ '-'  |   \    /   \ '-'  | 
 `-----' `--' `--'  `--`--' |  |-'   `--`--'          `-----'   `--`--'    `--'     `--`--'
```

[![BUILD](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/badge.svg)](https://github.com/yaphet17/chapa-java/actions/workflows/maven.yml/) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.yaphet17/Chapa/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.yaphet17/Chapa) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 

Unofficial Java SDK for Chapa Payment Gateway.

## What's new in this version
- You no longer need to deal with `JSON` or `Map<String, Object>` responses. You can just treat response data as a Java object using specific response classes for each request type (e.g. payment initialization, payment verification).
- Better exception handling. The SDK will throw the newly added `ChapaException` on failed requests to Chapa API.
- Bug fixes and design improvements.
- Well-tested and documented code. Check out the Javadoc [here](https://yaphet17.github.io/chapa-java/). 

## Table of Contents
1. [Documentation](#documentation)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Javadoc](https://yaphet17.github.io/chapa-java/)
5. [Contribution](#contribution)
6. [Example](#example)
7. [License](#license)

## Documentation
Visit official [Chapa's API Documentation](https://developer.chapa.co/docs)
## Installation
 Add the below maven dependency to your `pom.xml` file.
```xml
    <dependency>
      <groupId>io.github.yaphet17</groupId>
      <artifactId>Chapa</artifactId>
      <version>1.2.2</version>
    </dependency>
```
Or add the below gradle dependency to your `build.gradle` file.
```groovy
    implementation 'io.github.yaphet17:Chapa:1.2.2'
```

## Usage

> **Note** : This doc might not fully cover chapa-api. Please refer to the chapa developer doc for more. And contributions are welcome too. Thanks.


Instantiate a `Chapa` class.
```java       
Chapa chapa = new Chapa("your-secrete-key");
```
This will use the default implementation of `ChapaClient` interface. Internally, the SDK uses `DefaultRetrofitClientProvider` to make HTTP requests to Chapa API. This will not do retries on failed requests. If you want to implement your own client, you can do so by implementing the `ChapaClient` interface.

As an alternative, a retriable client is available. This client will retry failed requests up to 3 times(or more if configured).
```java
ChapaClientApi chapaClientRetrofit = new RetrierRetrofitClientProvider.Builder()
        .baseUrl("https://api.chapa.co/v1/")
        .maxRetries(5)
        .timeout(10000)
        .debug(true) // log request and response
        .build()
        .create();

Chapa chapa = new Chapa.Builder()
        .client(new ChapaClient(chapaClientRetrofit))
        .secretKey("your-secrete-key")
        .build();
```
> **Note:** Retry will be done with an [ExponentialBackoff](https://en.wikipedia.org/wiki/Exponential_backoff) strategy (with multiplier 1.5).

If this does not meet your requirements, you can implement the `IChapaClient` interface and create your own custom implementation to use your favorite HTTP client.

```java
public class MyCustomChapaClient implements IChapaClient {
    // Implement the methods from IChapaClient interface
}
```
Then, you can use your custom implementation like this:
```java
Chapa chapa = new Chapa.Builder()
        .client(new MyCustomChapaClient())
        .secretKey("your-secrete-key")
        .build();
```

## Methods
To initialize a transaction, you simply need to specify your information by either using our `PostData` class.

```java
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

public class ChapaExample {

    public static void main(String[] args) throws ChapaException {
        ChapaClientApi chapaClientRetrofit = new RetrierRetrofitClientProvider.Builder()
                .baseUrl("https://api.chapa.co/v1/")
                .maxRetries(3)
                .timeout(10000)
                .debug(true)
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
```
## Contribution
Please feel free to open an issue or pull request.

## License
MIT