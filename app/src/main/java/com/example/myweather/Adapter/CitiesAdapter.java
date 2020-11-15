package com.example.myweather.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.example.myweather.R;
import com.example.myweather.bo.City;
import com.example.myweather.Adapter.CitiesAdapter.CityViewHolder;
import com.example.myweather.CityDetailActivity;

import java.util.List;

//Adapter for the recycler view

public final class CitiesAdapter extends Adapter<CityViewHolder>{


    //The ViewHolder class
    //Each Widget is created as an attribut in order to update the UI
    public static final class CityViewHolder
            extends ViewHolder
    {

        private final TextView city_name;

        public CityViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //We find the references of the widgets
            city_name = itemView.findViewById(R.id.city);

        }

        public void update(final City city)
        {
            //We update the UI binding the current user to the current item
            city_name.setText(city.city);

            //We handle the click on the current item in order to display a new activity
            itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    //We create the intent that display the UserDetailActivity.
                    //The current user is added as an extra
                    //The User class implement the "Serializable" interface so I can put the whole object as an extra
                    final Intent intent = new Intent(itemView.getContext(), CityDetailActivity.class);
                    intent.putExtra(CityDetailActivity.CITY_EXTRA, city);
                    itemView.getContext().startActivity(intent);
                }

            });

        }

    }


    private final List<City> cities;

    public CitiesAdapter(List<City> cities)
    {
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We create the ViewHolder
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position)
    {
        //We update the ViewHolder
        holder.update(cities.get(position));
    }

    @Override
    public int getItemCount()
    {
        return cities.size();
    }

}
