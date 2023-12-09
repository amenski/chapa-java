package it.aman.chapa;

import it.aman.chapa.client.ChapaClient;
import it.aman.chapa.client.ChapaClientApi;
import it.aman.chapa.client.provider.DefaultRetrofitBuilderProvider;
import it.aman.chapa.client.provider.RetrofitBuilderProvider;
import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.InitializeResponseData;
import it.aman.chapa.model.SubAccountResponseData;
import it.aman.chapa.model.VerifyResponseData;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChapaClientTest {

    private final String baseUrl = "http://baseUrl";
    private final String secretKey = "secretKey";

    @Mock private Call call;
    @Mock private ChapaClientApi chapaClientApi;
    @InjectMocks private ChapaClient client;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void initialize_failOnNetworkCall() {
        Assertions.assertThrows(RuntimeException.class, () -> new ChapaClient(baseUrl).initialize(secretKey, new HashMap<>()));
    }

    @Test
    void initialize_failOnFailedResponse() throws Exception {
        //given
        when(chapaClientApi.initialize(anyString(), anyMap())).thenReturn(call);
        when(call.execute()).thenReturn(Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "")));

        //assert
        InitializeResponseData response = client.initialize(secretKey, new HashMap<>());
        Assertions.assertEquals("Unable to Initialize transaction.", response.getMessage());
    }

    @Test
    void initialize_Success() throws Exception {
        //given
        when(chapaClientApi.initialize(anyString(), anyMap())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success("{\"data\":{\"checkout_url\":\"https://checkout.chapa.co/checkout/payment/somestring\"},\"message\":\"Hosted Link\",\"status\":\"success\"}"));

        //assert
        InitializeResponseData response = client.initialize(secretKey, new HashMap<>());
        verify(chapaClientApi).initialize(anyString(), anyMap());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getMessage(), "Hosted Link");
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void initialize2_Success() throws Exception {
        //given
        when(chapaClientApi.initialize(anyString(), anyMap())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success("{\"data\":{\"checkout_url\":\"https://checkout.chapa.co/checkout/payment/somestring\"},\"message\":\"Hosted Link\",\"status\":\"success\"}"));

        //assert
        InitializeResponseData response = client.initialize(secretKey, "{\"ignore\": \"ignore\"}");
        verify(chapaClientApi).initialize(anyString(), anyMap());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getMessage(), "Hosted Link");
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void verify_failOnFailedResponse() throws Exception {
        //given
        when(chapaClientApi.verify(anyString(), anyString())).thenReturn(call);
        when(call.execute()).thenReturn(Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "")));

        //assert
        Assertions.assertThrows(ChapaException.class, () -> client.verify(secretKey, ""));
    }

    @Test
    void verify_Success() throws Exception {
        //given
        when(chapaClientApi.verify(anyString(), anyString())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success("{\n" +
                "    \"message\": \"Payment details\",\n" +
                "    \"status\": \"success\",\n" +
                "    \"data\": {\n" +
                "        \"first_name\": \"Bilen\",\n" +
                "        \"last_name\": \"Gizachew\",\n" +
                "        \"email\": \"abebech_bekele@gmail.com\",\n" +
                "        \"currency\": \"ETB\",\n" +
                "        \"amount\": 100,\n" +
                "        \"charge\": 3.5,\n" +
                "        \"mode\": \"test\",\n" +
                "        \"method\": \"test\",\n" +
                "        \"type\": \"API\",\n" +
                "        \"status\": \"success\",\n" +
                "        \"reference\": \"6jnheVKQEmy\",\n" +
                "        \"tx_ref\": \"chewatatest-6669\",\n" +
                "        \"customization\": {\n" +
                "            \"title\": \"Payment for my favourite merchant\",\n" +
                "            \"description\": \"I love online payments\",\n" +
                "            \"logo\": null\n" +
                "        },\n" +
                "        \"meta\": null,\n" +
                "        \"created_at\": \"2023-02-02T07:05:23.000000Z\",\n" +
                "        \"updated_at\": \"2023-02-02T07:05:23.000000Z\"\n" +
                "    }\n" +
                "}"));

        //assert
        VerifyResponseData response = client.verify(secretKey, "ignore");
        verify(chapaClientApi).verify(anyString(), anyString());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getMessage(), "Payment details");
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void getBanks_failOnFailedResponse() throws Exception {
        //given
        when(chapaClientApi.banks(anyString())).thenReturn(call);
        when(call.execute()).thenReturn(Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "")));

        //assert
        Assertions.assertThrows(ChapaException.class, () -> client.getBanks(secretKey));
    }

   @Test
    void createSubAccount_failOnFailedResponse() throws Exception {
        //given
        when(chapaClientApi.createSubAccount(anyString(), anyMap())).thenReturn(call);
        when(call.execute()).thenReturn(Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "")));

        //assert
        Assertions.assertThrows(ChapaException.class, () -> client.createSubAccount(secretKey, new HashMap<>()));
    }

    @Test
    void createSubAccount_Success() throws Exception {
        //given
        when(chapaClientApi.createSubAccount(anyString(), anyMap())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success("{\n" +
                "    \"message\": \"Subaccount created succesfully\",\n" +
                "    \"status\": \"success\",\n" +
                "    \"data\": {\n" +
                "        \"subaccounts[id]\": \"837b4e5e-57c8-4e39-b2df-66e7886b8bdb\"\n" +
                "    }\n" +
                "}"));

        // verify
        SubAccountResponseData response = client.createSubAccount(secretKey, new HashMap<>());
        verify(chapaClientApi).createSubAccount(anyString(), anyMap());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getMessage(), "Subaccount created succesfully");
    }

    @Test
    void createSubAccount_Success2() throws Exception {
        //given
        when(chapaClientApi.createSubAccount(anyString(), anyMap())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success("{\n" +
                "    \"message\": \"Subaccount created succesfully\",\n" +
                "    \"status\": \"success\",\n" +
                "    \"data\": {\n" +
                "        \"subaccounts[id]\": \"837b4e5e-57c8-4e39-b2df-66e7886b8bdb\"\n" +
                "    }\n" +
                "}"));

        // verify
        SubAccountResponseData response = client.createSubAccount(secretKey, "{\"ignore\": \"ignore\"}");
        verify(chapaClientApi).createSubAccount(anyString(), anyMap());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getMessage(), "Subaccount created succesfully");
    }
}