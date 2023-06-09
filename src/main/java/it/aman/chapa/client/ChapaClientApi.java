package it.aman.chapa.client;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ChapaClientApi {

    @POST("/transaction/initialize")
    Call<String> initialize(@Header("Authorization") String authorizationHeader, @Body Map<String, Object> body);

    @GET("/transaction/verify/{tx_ref}")
    Call<String> verify(@Header("Authorization") String authorizationHeader, @Path("tx_ref") String transactionReference);

    @GET("/transaction/banks")
    Call<String> banks(@Header("Authorization") String authorizationHeader);

    @POST("/subaccount")
    Call<String> createSubAccount(@Header("Authorization") String authorizationHeader, @Body Map<String, Object> body);
}
