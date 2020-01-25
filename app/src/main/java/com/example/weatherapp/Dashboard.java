package com.example.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Dashboard extends Fragment {
    public Dashboard()
    {
        // Required empty public constructor
    }
    ImageView weath_img;
    ArrayList<ConsolidatedWeather> weathers;
    RecyclerView recyclerView ;
    TextView username, weatherName, mintemp, maxtemp, today_temp, humidity, predict;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("dashboard fragment called");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        weatherName = view.findViewById(R.id.weathername);
        humidity = view.findViewById(R.id.t_humid);
        predict = view.findViewById(R.id.predict);
        recyclerView = view.findViewById(R.id.recycleView);
        weath_img = view.findViewById(R.id.dash_img);
        mintemp = view.findViewById(R.id.t_mintemp);
        maxtemp = view.findViewById(R.id.t_maxtemp);
        today_temp = view.findViewById(R.id.t_temp);
    }


   /* public void getWeatherdata()
    {

        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        Call<WeatherParent> call = service.getWeather();
        System.out.println("Call : " + call);
        call.enqueue(new Callback<WeatherParent>() {
            @Override
            public void onResponse(Call<WeatherParent> call, Response<WeatherParent> response) {

                System.out.println("Response method called");


                // we have data as response object named wp
                WeatherParent wp = response.body();


                // convert the simple list into arraylist - declare global
                weathers = new ArrayList<>(wp.getConsolidatedWeather());

                // call method for set the data
                setData(weathers);

            }

            @Override
            public void onFailure(Call<WeatherParent> call, Throwable t) {

                System.out.println("Failure method Called! :" +t.getMessage());
            }
        });

    }*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData(ArrayList<ConsolidatedWeather> arrweather)
    {
        weatherName.setText(arrweather.get(0).getWeatherStateName());
        setImage(weath_img,getWeImage(arrweather.get(0).getWeatherStateAbbr()));
        String thetemp = String.format("%.2f", arrweather.get(0).getTheTemp());
        String mintemp = String.format("%.2f", arrweather.get(0).getMinTemp());
        String maxtemp = String.format("%.2f", arrweather.get(0).getMaxTemp());
        this.mintemp.setText(mintemp);
        this.maxtemp.setText(maxtemp);
        today_temp.setText(thetemp);
        humidity.setText("Humidity : "+ arrweather.get(0).getHumidity().toString()+"%");
        predict.setText("Predictability : "+ arrweather.get(0).getPredictability().toString()+"%");
        initView(arrweather);
    }
    public void initView(ArrayList<ConsolidatedWeather> weatherarr)
    {
        weatherarr.remove(0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapter adapter = new RecycleViewAdapter(weatherarr,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
    public String getWeImage(String img)
    {
        return "https://www.metaweather.com/static/img/weather/png/"+ img +".png";
    }
    public void setImage(ImageView img,String link)
    {
        //Picasso.get().load(link).into(img);
    }
}