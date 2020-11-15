package com.example.myweather.Api;

import com.example.myweather.bo.City;
import com.example.myweather.bo.WResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface weatherApi {

    @GET("weather")
    Call<WResponse> getWeather(@Query("q") String city_name,
                               @Query("appid") String api_key);

    @PATCH("city/{city}" )
    Call<WResponse> updateCity(@Path("city") String city_name, @Body City city);

}
