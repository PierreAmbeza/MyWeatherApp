package com.example.myweather.bo;

import com.squareup.moshi.Json;

public class Weather {

    @Json(name = "icon")
    private String icon;

    public String getIcon(){
        return this.icon;
    }
}
