package com.example.myweather.bo;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.io.Serializable;
//Base class

@Entity
final public class City implements Serializable {

    //Add a unique key for each city added into the database
    @PrimaryKey(autoGenerate = true)
    public int id;

    //Add the column with city name
    @ColumnInfo(name = "City")
    public final String city;


    public City(@NonNull String city){
        this.city = city;
    }
}
