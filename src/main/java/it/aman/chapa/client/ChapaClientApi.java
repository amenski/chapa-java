package it.aman.chapa.client;

import it.aman.chapa.model.Bank;
import it.aman.chapa.model.ResponseBanks;
import it.aman.chapa.model.ResponseData;
import it.aman.chapa.model.SubAccountResponseData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

public interface ChapaClientApi {

    @POST("transaction/initialize")
    Call<String> initialize(@Header("Authorization") String authorizationHeader, @Body Map<String, Object> body);

    @GET("transaction/verify/{tx_ref}")
    Call<String> verify(@Header("Authorization") String authorizationHeader, @Path("tx_ref") String transactionReference);

    @GET("banks")
    Call<ResponseBanks> banks(@Header("Authorization") String authorizationHeader);

    @POST("subaccount")
    Call<SubAccountResponseData> createSubAccount(@Header("Authorization") String authorizationHeader, @Body Map<String, Object> body);
}
