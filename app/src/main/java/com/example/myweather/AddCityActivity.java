package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweather.Api.weatherApi;
import com.example.myweather.bo.City;
import com.example.myweather.bo.Main;
import com.example.myweather.bo.apiManager;
import com.example.myweather.repository.CityRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class AddCityActivity extends AppCompatActivity implements OnClickListener {

    private EditText city_name;

    private TextView detail_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        city_name = findViewById(R.id.city);
        detail_view = findViewById(R.id.temperature);

        findViewById(R.id.save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        final String city = city_name.getEditableText().toString();
        final boolean canAddCity = checkFormEntry(city);

        if(canAddCity)
        {
            Toast.makeText(this, "Cannot add city", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //cityFromAPI(city);
            saveCity(city);
            resetForm();
            //onBackPressed();
        }
    }

    /*private void cityFromAPI(String city){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(MoshiConverterFactory. create()) .build() ;
        final weatherApi service = retrofit.create(weatherApi. class);
        final Call<apiManager> call = service.getCity(city, "metric", api_key);
        Log.d(AddCityActivity.class.getSimpleName(), "test");
        call.enqueue(new Callback<apiManager>()
        {
            @Override
            public void onResponse(Call<apiManager> call, Response<apiManager> response) {
                apiManager apiManager = response.body();
                Main main = apiManager.getMain();
                Log.d(AddCityActivity.class.getSimpleName(), "test:"+ Double.toString(main.temp));
                detail_view.setText(Double.toString(main.temp));


            }
            @Override
            public void onFailure(Call<apiManager> call, Throwable t) {
                //Toast.makeText(this, "Cannot add city", Toast.LENGTH_SHORT).show();
            } });
    }*/

    private void saveCity(String city) { CityRepository.getInstance(this).addCity(new City(city)); }

    private void resetForm(){
        city_name.setText(null);
    }

    private boolean checkFormEntry(String city)
    {
        return TextUtils.isEmpty(city);
    }
}