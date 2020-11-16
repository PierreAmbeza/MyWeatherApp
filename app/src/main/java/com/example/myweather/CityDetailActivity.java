package com.example.myweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class CityDetailActivity extends AppCompatActivity {


    //We create the variables linked with the detail layout which will store the temp...
    private ImageView image;

    //final String api_key = "64808b9fc49499f3bff52b4eac1b7e8f";

    public static final String CITY_EXTRA = "cityExtra";

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

    //Function to check if we have called the API in the last hour
    private void callApiOrNot(String city_name)
    {
        int previous_time = AppPreferences.getLastTime(this, city_name);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE) + hour*60;
        if(minutes < previous_time)
            minutes = minutes + 1440; //Manage possible problems on day changing
        if(minutes - previous_time >= 60 || previous_time == 0) {
            weatherFromAPI(city_name);//If we don't have called the API in the last our then we call it
        }
        else {
            getCityWeather(city_name);//If we called the API in the last hour, then retrieve data from preferences
        }
    }

    //Matching view with corresponding variable
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

    //add a menu to the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //We add the "menu_order" to the current activity
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Delete a city when the delete button is click and display an alert message to confirm
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
                            CityRepository.getInstance(CityDetailActivity.this).deleteCity(city);//Delete city is "YES" button is clicked
                            AppPreferences.removeCity(CityDetailActivity.this, city.city);
                            finish();
                        }
                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();//Close dialog if "NO" button is clicked
                        }
                    });
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Check city to remove characters like "-" or spaces that have to be replaced by "+" to call the api

    private String checkCity(String city){

        city = city.replaceAll("-", " ");
        city = city.replaceAll("[^a-zA-Z0-9\\s]", "+");
        return city;
    }

    //Call the api
    private void weatherFromAPI(String city){
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor() ;
        httpLoggingInterceptor.setLevel(Level.BODY);
        city = checkCity(city);
        final OkHttpClient okHttp = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(MoshiConverterFactory.create())//.build();
                .client(okHttp).build();
        weatherApi service = retrofit.create(weatherApi.class);
        Call<WResponse> call = service.getWeather(city, "metric", api_key);
        call.enqueue(new Callback<WResponse>()
        {
            @Override
            public void onResponse(Call<WResponse> call, Response<WResponse> response) {
                WResponse data = response.body();//We get the data from the api
                if(!(response.isSuccessful()))
                    Log.d(CityDetailActivity.class.getSimpleName(), String.valueOf(response.code()));
                Main main = data.getMain();//We match different data from api, here main (for temp...)
                List<Weather> w = data.getWeather();//We match different data from api, here weather (for icon)
                setData(main, w);
                saveWeather(data);
                Log.d(CityDetailActivity.class.getSimpleName(), "api called");

            }
            @Override
            public void onFailure(Call<WResponse> call, Throwable t) {
                t.printStackTrace();
            } });
    }

    //We set the data into the TextViews and the icon
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

    //We save the weather of appropriate city into the App preferences
    public void saveWeather(WResponse data){
        AppPreferences.saveCityWeather(this, data, city_name.getText().toString(), minutes);
    }

    //We retrieve the weather from App preferences
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




