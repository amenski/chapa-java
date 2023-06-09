package it.aman.chapa;

import it.aman.chapa.client.ChapaClient;
import it.aman.chapa.client.ChapaClientApi;
import it.aman.chapa.exception.ChapaException;
import it.aman.chapa.model.InitializeResponseData;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChapaClientTest {

    private final String baseUrl = "http://baseUrl";
    private final String secretKey = "secretKey";

    @Mock private ChapaClientApi chapaClientApi;
    @InjectMocks private ChapaClient client;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initialize_failOnNetworkCall() {
        Assertions.assertThrows(RuntimeException.class, () -> new ChapaClient(baseUrl).initialize(secretKey, new HashMap<>()));
    }

    @Test
    public void initialize_failOnFailedResponse() throws Exception {
        //given
        Call<String> call = mock(Call.class);
        when(chapaClientApi.initialize(anyString(), anyMap())).thenReturn(call);
        when(call.execute()).thenReturn(Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "")));

        //assert
        Assertions.assertThrows(ChapaException.class, () -> client.initialize(secretKey, new HashMap<>()));
    }

    @Test
    public void initialize_Success() throws Exception {
        //given
        Call<String> call = mock(Call.class);
        when(chapaClientApi.initialize(anyString(), anyMap())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success("{\"data\":{\"checkout_url\":\"https://checkout.chapa.co/checkout/payment/somestring\"},\"message\":\"Hosted Link\",\"status\":\"success\"}"));

        //assert
        InitializeResponseData response = client.initialize(secretKey, new HashMap<>());
        verify(chapaClientApi).initialize(anyString(), anyMap());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getMessage(), "Hosted Link");
        Assertions.assertNotNull(response.getData());
    }
}