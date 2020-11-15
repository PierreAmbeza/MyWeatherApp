package com.example.myweather.bo;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class apiManager implements Serializable {


    @Json(name = "main")
    private Main main;

    @Json(name = "weather")
    public Weather weather;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Weather getWeather(){
        return weather;
    }
}
