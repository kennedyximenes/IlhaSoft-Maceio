package com.is.kennedy.ilhafilmes.presenter;

import com.is.kennedy.ilhafilmes.model.Filme;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kennedy ximenes on 14/07/2017.
 */

public interface RetrofitObjectAPI {

    @GET("?")
    Call<Filme> getFilmeDetails(@Query("t") String Nome, @Query("apikey") String apiKey);

}
