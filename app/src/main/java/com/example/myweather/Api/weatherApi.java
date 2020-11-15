package com.example.myweather.Api;

import com.example.myweather.bo.City;
import com.example.myweather.bo.WResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface weatherApi {

    //Search the weather in addition with the base url
    @GET("weather")//We add the wanted city, the units to have datas in °C and api_key to have datas
    Call<WResponse> getWeather(@Query("q") String city_name,
                                    @Query("units") String units,
                                    @Query("appid") String api_key);

    /*@PATCH("city/{city}" )
    Call<WResponse> updateCity(@Path("city") String city_name, @Body City city);*/

}
