package com.is.kennedy.ilhafilmes.presenter;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by kennedy ximenes on 14/07/2017.
 */

public class GetJsonFromRetrofit {

    public static final String BASE_URL = "http://www.omdbapi.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
