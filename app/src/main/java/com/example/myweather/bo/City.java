package com.example.myweather.bo;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.io.Serializable;

@Entity
final public class City implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "City")
    public final String city;


    public City(@NonNull String city){
        this.city = city;
    }



    /*
    public City(@NonNull String city){
        this.city = city;
        this.r_temp = null;
        this.f_temp = null;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.weather = weather;
    }*/
}
