package project.finalproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @POST("mc/authenticate")
    @Headers("Content-Type: application/json")
    Call<AuthenticateResponse> authenticate(@Body AuthenticateRequest request);
}
