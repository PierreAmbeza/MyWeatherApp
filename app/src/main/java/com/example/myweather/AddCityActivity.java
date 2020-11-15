package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweather.bo.City;
import com.example.myweather.repository.CityRepository;

import java.util.List;

public class AddCityActivity extends AppCompatActivity implements OnClickListener {

    private EditText city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        city_name = findViewById(R.id.city);
        findViewById(R.id.save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        final String city = city_name.getEditableText().toString();
        final boolean canAddCity = checkFormEntry(city) && checkList(city);

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

    private boolean checkList(String city){
        final List<City> cities = CityRepository.getInstance(this).getCities();
        for(City c:cities)
        {
            if(c.city == city)
                return false;
        }
        return true;
    }

    private void saveCity(String city) {
        CityRepository.getInstance(this).addCity(new City(city));
    }

    private void resetForm(){
        city_name.setText(null);
    }

    private boolean checkFormEntry(String city)
    {
        return TextUtils.isEmpty(city);
    }
}