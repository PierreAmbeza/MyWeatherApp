package com.example.myweather.bo;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Main implements Serializable {

    @Json(name = "temp")
    public double temp;

    @Json(name = "feels_like")
    public double feels_like;

    @Json(name = "temp_min")
    public double temp_min;

    @Json(name = "temp_max")
    public double temp_max;

}
