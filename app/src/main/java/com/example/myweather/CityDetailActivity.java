package com.example.myweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.myweather.bo.apiManager;
import com.example.myweather.repository.CityRepository;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class CityDetailActivity extends AppCompatActivity {

    private ImageView image;

    final String api_key = "a24468e2938a78e4a1b58acd44317d4b";

    public static final String CITY_EXTRA = "cityExtra";

    private static final HashMap<String, String> map = new HashMap<String, String>() {{
        put("01d", "img_01d");
        put("02d", "img_02d");
        put("03d", "img_03d");
        put("04d", "img_04d");
        put("09d", "img_09d");
        put("10d", "img_10d");
        put("11d", "img_11d");
        put("13d", "img_13d");
        put("50d", "img50d");
        put("01n", "img_01n");
        put("02n", "img_02n");
        put("03n", "img_03n");
        put("04n", "img_04n");
        put("09n", "img_09n");
        put("10n", "img_10n");
        put("11n", "img_11n");
        put("13n", "img_13n");
        put("50n", "img50n");
    }};

    private ImageView weather;

    private TextView city_name;

    private TextView real_temp;

    private TextView feel_temp;

    private TextView max_temp;

    private TextView min_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.CITY_EXTRA);
        setContentView(R.layout.activity_city_detail);
        int imageResource = getResources().getIdentifier("@drawable/img_10d", null, getPackageName());
        image = findViewById(R.id.weather_image);
        image.setImageResource(imageResource);
        city_name = findViewById(R.id.city);
        city_name.setText(city.city);
        getSupportActionBar().setTitle(city.city);
        cityFromAPI(city.city);
        real_temp = findViewById(R.id.temperature);

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

    private void cityFromAPI(String city){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(MoshiConverterFactory. create()) .build() ;
        final weatherApi service = retrofit.create(weatherApi. class);
        final Call<apiManager> call = service.getCity(city, "metric", api_key);
        Log.d(CityDetailActivity.class.getSimpleName(), "test"+ service.getCity(city, "metric", api_key));
        call.enqueue(new Callback<apiManager>()
        {
            @Override
            public void onResponse(Call<apiManager> call, Response<apiManager> response) {
                Log.d(CityDetailActivity.class.getSimpleName(), "reponse");
                apiManager apiManager = response.body();
                Main main = apiManager.getMain();
                Log.d(AddCityActivity.class.getSimpleName(), "test:"+ Double.toString(main.temp));
                real_temp.setText(Double.toString(main.temp));


            }
            @Override
            public void onFailure(Call<apiManager> call, Throwable t) {
                Log.d(CityDetailActivity.class.getSimpleName(), "failure");
                //Toast.makeText(this, "Cannot add city", Toast.LENGTH_SHORT).show();
            } });


    }
}




