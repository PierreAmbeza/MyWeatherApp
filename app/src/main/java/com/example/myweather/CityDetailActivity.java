package com.example.myweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myweather.Api.weatherApi;
import com.example.myweather.bo.City;
import com.example.myweather.bo.Main;
import com.example.myweather.bo.WResponse;
import com.example.myweather.bo.Weather;
import com.example.myweather.preferences.AppPreferences;
import com.example.myweather.repository.CityRepository;
import okhttp3.logging.HttpLoggingInterceptor.Level;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.*;

public class CityDetailActivity extends AppCompatActivity {

    private ImageView image;

    //final String api_key = "64808b9fc49499f3bff52b4eac1b7e8f";

    public static final String CITY_EXTRA = "cityExtra";


    private ImageView weather;

    private TextView city_name;

    private TextView real_temp;

    private TextView feel_temp;

    private TextView max_temp;

    private TextView min_temp;

    int minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.CITY_EXTRA);
        setContentView(R.layout.activity_city_detail);
        initView(city);
        callApiOrNot(city.city);

    }

    private void callApiOrNot(String city_name)
    {
        int previous_time = AppPreferences.getLastTime(this, city_name);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE) + hour*60;
        if(minutes - previous_time >= 3 || previous_time == 0) {
            weatherFromAPI(city_name);
        }
        else {
            getCityWeather(city_name);
        }
    }

    private void initView(City city){
        image = findViewById(R.id.weather_image);
        city_name = findViewById(R.id.city);
        city_name.setText(city.city);
        getSupportActionBar().setTitle(city.city);
        real_temp = findViewById(R.id.temperature);
        feel_temp = findViewById(R.id.feels);
        min_temp = findViewById(R.id.min);
        max_temp = findViewById(R.id.max);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //We add the "menu_order" to the current activity
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CityDetailActivity.this);
            alertDialog.setTitle("Warning!");
            alertDialog.setMessage("Do you really want to delete this city from your favorites?");
            alertDialog.setCancelable(true);
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.CITY_EXTRA);
                            CityRepository.getInstance(CityDetailActivity.this).deleteCity(city);
                            AppPreferences.removeCity(CityDetailActivity.this, city.city);
                            finish();
                        }
                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private String checkCity(String city){

        city = city.replaceAll("-", " ");
        city = city.replaceAll("[^a-zA-Z0-9\\s]", "+");
        return city;
    }

    private void weatherFromAPI(String city){
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor() ;
        httpLoggingInterceptor.setLevel(Level.BODY);
        city = checkCity(city);
        final OkHttpClient okHttp = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();//.addInterceptor(new MyInterceptor()).build();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(MoshiConverterFactory.create())//.build();
                .client(okHttp).build();
        weatherApi service = retrofit.create(weatherApi.class);
        Call<WResponse> call = service.getWeather(city, "metric", api_key);
        call.enqueue(new Callback<WResponse>()
        {
            @Override
            public void onResponse(Call<WResponse> call, Response<WResponse> response) {
                WResponse data = response.body();
                if(!(response.isSuccessful()))
                    Log.d(CityDetailActivity.class.getSimpleName(), String.valueOf(response.code()));
                Main main = data.getMain();
                List<Weather> w = data.getWeather();
                setData(main, w);
                saveWeather(data);
                Log.d(CityDetailActivity.class.getSimpleName(), "api called");

            }
            @Override
            public void onFailure(Call<WResponse> call, Throwable t) {
                t.printStackTrace();
            } });
    }

    private void setData(Main main, List<Weather> w)
    {
        real_temp.setText(Double.toString(main.getTemp()) + "°C");
        feel_temp.setText("feels like " + Double.toString(main.getFeelsLike()) + "°C");
        min_temp.setText("Min " + Double.toString(main.getTempMin()) + "°C");
        max_temp.setText("Max " + Double.toString(main.getTempMax()) +"°C");
        int imageResource = getResources()
                .getIdentifier("@drawable/img_" + w.get(0).getIcon(), null, getPackageName());
        image.setImageResource(imageResource);
    }

    public void saveWeather(WResponse data){
        Log.d(CityDetailActivity.class.getSimpleName(), Integer.toString(minutes));
        AppPreferences.saveCityWeather(this, data, city_name.getText().toString(), minutes);
        Log.d(CityDetailActivity.class.getSimpleName(), AppPreferences.getCityTemp(this, city_name.getText().toString()));
    }

    private void getCityWeather(String city)
    {
        real_temp.setText(AppPreferences.getCityTemp(this, city) + "°C");
        feel_temp.setText(AppPreferences.getCityFT(this, city) + "°C");
        min_temp.setText(AppPreferences.getCityMinTemp(this, city) + "°C");
        max_temp.setText(AppPreferences.getCityMaxTemp(this, city) +"°C");
        int imageResource = getResources()
                .getIdentifier("@drawable/img_" + AppPreferences.getCityIcon(this, city), null, getPackageName());
        image.setImageResource(imageResource);
    }

}




