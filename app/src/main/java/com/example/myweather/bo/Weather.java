package com.example.myweather.bo;

import com.squareup.moshi.Json;

//Class corresponding to the "weather" part of the JSON provided by the api
public class Weather {

    //We only need the "icon" field from weather to dispay the image
    @Json(name = "icon")
    private String icon;

    public String getIcon(){
        return this.icon;
    }
}
