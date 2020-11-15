package com.example.myweather.Api;

import com.example.myweather.bo.City;
import com.example.myweather.bo.apiManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface weatherApi {

    @GET("weather?")
    Call<apiManager> getCity(@Query("q") String city_name,
                                   @Query("units") String units,
                                   @Query("appid") String api_key
                       );

    @PATCH("city/{city}" )
    Call<apiManager> updateCity(@Path("city") String city_name, @Body City city);

}
