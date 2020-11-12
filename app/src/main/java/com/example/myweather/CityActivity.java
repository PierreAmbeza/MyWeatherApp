package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.myweather.bo.City;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myweather.repository.CityRepository;
import com.example.myweather.Adapter.CitiesAdapter;
import java.util.List;


public class CityActivity extends AppCompatActivity implements OnClickListener {


    private RecyclerView recyclerView;

    private View emptyView;

    public static final String TAG = CityActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        emptyView = findViewById(R.id.empty_view);
        findViewById(R.id.fab).setOnClickListener(this);
        initList();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //We init the list into the onResume method
        //so the list is updated each time the screen goes to foreground
        //initList();
        initList();
    }


    private void initList() {
        final List<City> cities = CityRepository.getInstance(this).getCities();
        final CitiesAdapter citiesAdapter = new CitiesAdapter(cities);
        recyclerView.setAdapter(citiesAdapter);
        checkIsEmpty(citiesAdapter);
    }

    /*
    private void initList(){
        //We retrieve the list of cities to display
        final List<City> cities = CityRepository.getInstance(this).getCities();
        final CitiesAdapter citiesAdapter = new CitiesAdapter(cities);
        //We create the adapter and we attach it to the RecyclerView
        if(cities.isEmpty())
        {
            setContentView(R.layout.activity_city_empty);
            findViewById(R.id.fab).setOnClickListener(this);
        }
        if(citiesAdapter != null)
        {
            recyclerView.setAdapter(citiesAdapter);
        }
    }*/

    private void checkIsEmpty(CitiesAdapter citiesAdapter)
    {
        emptyView.setVisibility(citiesAdapter == null || citiesAdapter.getItemCount() == 0? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(citiesAdapter == null || citiesAdapter.getItemCount() == 0? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View v){
        final Intent intent = new Intent(this, AddCityActivity.class);
        startActivity(intent);
    }
}