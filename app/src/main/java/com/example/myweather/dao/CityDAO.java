package com.example.myweather.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.myweather.bo.City;
import java.util.List;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM City")
    List<City> getAllCities();

    @Delete
    void deleteCity(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCity(City city);


}
