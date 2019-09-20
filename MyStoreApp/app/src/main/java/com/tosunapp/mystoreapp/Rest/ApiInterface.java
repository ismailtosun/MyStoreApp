package com.tosunapp.mystoreapp.Rest;

import com.tosunapp.mystoreapp.Model.Store;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ismail tosun.
 */

public interface ApiInterface {

    @GET(" ")
    Call<List<Store>> getStoreList();

}
