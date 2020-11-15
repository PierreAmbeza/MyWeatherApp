package com.example.myweather.bo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

//Class to get the main response from the API
public class WResponse implements Serializable {

    //As for the "main" and "weather" classes, we only need some field from the API
    //So we just need "main" and "weather" fields

    @Json(name = "main")
    private Main main;

    @Json(name = "weather")
    public List<Weather> weather;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather(){
        return this.weather;
    }
}
