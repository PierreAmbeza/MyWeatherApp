package com.example.myweather.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.myweather.bo.City;
import com.example.myweather.dao.CityDAO;


@Database(entities = {City.class}, version = 1)
public abstract class CityDatabase
        extends RoomDatabase
{
    public abstract CityDAO cityDAO();
}
