package com.example.myweather.bo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

import java.io.Serializable;
import java.util.List;

public class WResponse implements Serializable {


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
