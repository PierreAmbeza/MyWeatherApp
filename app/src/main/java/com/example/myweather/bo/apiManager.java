package com.example.myweather.bo;

import com.squareup.moshi.Json;

public class apiManager {
    @Json(name = "main")
    public Main main;

    @Json(name = "weather")
    public Weather weather;

    public Main getMain() {
        return main;
    }

    public Weather getWeather(){
        return weather;
    }
}
