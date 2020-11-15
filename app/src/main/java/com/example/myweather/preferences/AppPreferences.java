package com.example.myweather.preferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.myweather.bo.Main;
import com.example.myweather.bo.WResponse;
import com.example.myweather.bo.Weather;

import java.util.List;
import java.util.Map;

public abstract class AppPreferences {

    private static final String WEATHER_PREFERENCES_KEY = "loginPreferencesKey";


    //Save all datas for a specific city
    public static void saveCityWeather(@NonNull Context context, @NonNull WResponse data, String city, int time)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor editor = defaultSharedPreferences.edit();
        Main m = data.getMain();
        List<Weather> w = data.getWeather();
        editor.putString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"0", Double.toString(m.getTemp()));
        editor.putString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"1", Double.toString(m.getFeelsLike()));
        editor.putString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"2", Double.toString(m.getTempMax()));
        editor.putString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"3", Double.toString(m.getTempMin()));
        editor.putString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"4", w.get(0).getIcon());
        editor.putInt(AppPreferences.WEATHER_PREFERENCES_KEY+city+"5", time);
        editor.apply();
    }

    //Get all datas from a specific city
    @Nullable
    public static String getCityTemp(@NonNull Context context, String city)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"0", null);
    }

    @Nullable
    public static String getCityFT(@NonNull Context context, String city)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"1", null);
    }

    @Nullable
    public static String getCityMinTemp(@NonNull Context context, String city)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"2", null);
    }

    @Nullable
    public static String getCityMaxTemp(@NonNull Context context, String city)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"3", null);
    }

    @Nullable
    public static String getCityIcon(@NonNull Context context, String city)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.WEATHER_PREFERENCES_KEY+city+"4", null);
    }

    @Nullable
    public static int getLastTime(@NonNull Context context, String city)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getInt(AppPreferences.WEATHER_PREFERENCES_KEY+city+"5", 0);
    }

    //remove data for a specific city

    @Nullable
    public static void removeCity(@NonNull Context context, String city)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor editor = defaultSharedPreferences.edit();
        for(int i = 0; i < 6; i++)//Remove everything associated with the city
        {
            editor.remove(AppPreferences.WEATHER_PREFERENCES_KEY+city+Integer.toString(i));
        }
        editor.apply();
    }

    private AppPreferences() {

    }
}
