package ecommerce.assesment.com.ruchita.serverutil;

import ecommerce.assesment.com.ruchita.models.ResultBean;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by root on 30/1/18.
 */

public interface ApiInterface {

    @GET("json")
    Call<ResultBean> getAllData();

}
