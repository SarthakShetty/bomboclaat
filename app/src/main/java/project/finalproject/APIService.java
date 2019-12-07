package project.finalproject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("/authenticate")
    @FormUrlEncoded
    Call<AuthenticateResponse> authenticate(@Field("User") String title,
                        @Field("Model") String body);
}
