package com.example.myweather.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.myweather.dao.CityDAO;
import com.example.myweather.database.CityDatabase;

import com.example.myweather.bo.City;

import java.util.List;

public final class CityRepository {

    private static volatile CityRepository instance;

    // We accept the "out-of-order writes" case
    public static CityRepository getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (CityRepository.class)
            {
                if (instance == null)
                {
                    instance = new CityRepository(context);
                }
            }
        }

        return instance;
    }

    private final CityDatabase citydatabase;

    private CityRepository(Context context)
    {
        citydatabase = Room.databaseBuilder(context, CityDatabase.class, "city-database").allowMainThreadQueries().build();
    }

    public List<City> getCities()
    {
        return citydatabase.cityDAO().getAllCities();
    }

    public void deleteCity(City city)
    {
        citydatabase.cityDAO().deleteCity(city);
    }

    public void addCity(City city)
    {
        citydatabase.cityDAO().addCity(city);
    }

    /*public void delete(){
        citydatabase.cityDAO().del();
    }*/

}
