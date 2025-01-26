package com.yaphet.chapa.client;

import com.yaphet.chapa.model.InitializeResponse;
import com.yaphet.chapa.model.ResponseBanks;
import com.yaphet.chapa.model.SubAccountResponse;
import com.yaphet.chapa.model.VerifyResponse;
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
    Call<InitializeResponse> initialize(@Header("Authorization") String authorizationHeader, @Body Map<String, Object> body);

    @GET("transaction/verify/{tx_ref}")
    Call<VerifyResponse> verify(@Header("Authorization") String authorizationHeader, @Path("tx_ref") String transactionReference);

    @GET("banks")
    Call<ResponseBanks> banks(@Header("Authorization") String authorizationHeader);

    @POST("subaccount")
    Call<SubAccountResponse> createSubAccount(@Header("Authorization") String authorizationHeader, @Body Map<String, Object> body);
}
