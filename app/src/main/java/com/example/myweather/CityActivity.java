package com.example.myweather;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import com.example.myweather.bo.City;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myweather.repository.CityRepository;
import com.example.myweather.Adapter.CitiesAdapter;
import java.util.List;
import android.util.Log;

public class CityActivity extends AppCompatActivity implements OnClickListener {


    private RecyclerView recyclerView;

    public static final String TAG = CityActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<City> cities = CityRepository.getInstance(this).getCities();
        if(cities.isEmpty())
        {
            setContentView(R.layout.activity_city_empty);
        }
        else {
            setContentView(R.layout.activity_city);
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        final CitiesAdapter citiesAdapter = new CitiesAdapter(cities);
        recyclerView.setAdapter(citiesAdapter);
        }
        Log.d(CityActivity.class.getSimpleName(), "testing");
        //We configure the click on the fab
        findViewById(R.id.fab).setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //We init the list into the onResume method
        //so the list is updated each time the screen goes to foreground
        initList();
    }

    private void initList(){
        //We retrieve the list of cities to display
        final List<City> cities = CityRepository.getInstance(this).getCities();
        //We create the adapter and we attach it to the RecyclerView
        if(cities.isEmpty()) {
            setContentView(R.layout.activity_city_empty);
            findViewById(R.id.fab).setOnClickListener(this);
        }
        if(recyclerView != null) {
            final CitiesAdapter citiesAdapter = new CitiesAdapter(cities);
            recyclerView.setAdapter(citiesAdapter);
        }
    }

    @Override
    public void onClick(View v){
        final Intent intent = new Intent(this, AddCityActivity.class);
        startActivity(intent);
    }
}

/*
public class CityActivity extends AppCompatActivity {

    ExtendedRecyclerView recycler_view;
    View empty_view;

    Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        recycler_view = findViewById(R.id.recycler_view);
        empty_view = findViewById(R.id.empty_view);

        recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        adapter = new Adapter(getActivity(), myRandomList, false);
        recycler_view.setAdapter(adapter);
        recycler_view.setEmptyView(empty_view);

    }
}*/