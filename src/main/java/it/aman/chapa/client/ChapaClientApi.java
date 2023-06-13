package it.aman.chapa.client;

import it.aman.chapa.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.Map;


/**
 * Chapa default retrofit client
 */
public interface ChapaClientApi {

    @POST("transaction/initialize")
    Call<InitializeResponseData> initialize(@Header("Authorization") String authorizationHeader, @Body Map<String, Object> body);

    @GET("transaction/verify/{tx_ref}")
    Call<VerifyResponseData> verify(@Header("Authorization") String authorizationHeader, @Path("tx_ref") String transactionReference);

    @GET("banks")
    Call<ResponseBanks> banks(@Header("Authorization") String authorizationHeader);

    @POST("subaccount")
    Call<SubAccountResponseData> createSubAccount(@Header("Authorization") String authorizationHeader, @Body Map<String, Object> body);
}
