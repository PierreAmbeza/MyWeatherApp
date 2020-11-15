package com.example.myweather.bo;

import com.squareup.moshi.Json;

import retrofit2.http.Field;

public class Main {

    @Json(name = "temp")
    private Double temp;
    @Json(name = "feels_like")
    private Double feelsLike;
    @Json(name = "temp_min")
    private Double tempMin;
    @Json(name = "temp_max")
    private Integer tempMax;
    @Json(name = "pressure")
    private Integer pressure;
    @Json(name = "humidity")
    private Integer humidity;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Integer getTempMax() {
        return tempMax;
    }

    public void setTempMax(Integer tempMax) {
        this.tempMax = tempMax;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

}