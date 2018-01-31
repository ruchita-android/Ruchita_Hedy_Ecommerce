package ecommerce.assesment.com.ruchita.serverutil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 30/1/18.
 */

public class ApiClient  {

    private static final String BASE_URL = "https://stark-spire-93433.herokuapp.com/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
