package com.example.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends Fragment {
    int position;
    NavController navController;
    LinearLayout linearLayout;
    public Dashboard()
    {
        // Required empty public constructor
    }
    ImageView weath_img;
    ArrayList<ConsolidatedWeather> weathers;
    RecyclerView recyclerView ;
    TextView  dayName, weatherName, mintemp, maxtemp, today_temp, humidity, predict;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            position = getArguments().getInt("pos");
        }
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
        navController = Navigation.findNavController(getActivity(), R.id.mainFragment);
        linearLayout = view.findViewById(R.id.llProgress);
        dayName = view.findViewById(R.id.dayName);
        weatherName = view.findViewById(R.id.weathername);
        humidity = view.findViewById(R.id.t_humid);
        predict = view.findViewById(R.id.predict);
        recyclerView = view.findViewById(R.id.recycleView);
        weath_img = view.findViewById(R.id.dash_img);
        mintemp = view.findViewById(R.id.t_mintemp);
        maxtemp = view.findViewById(R.id.t_maxtemp);
        today_temp = view.findViewById(R.id.t_temp);
        linearLayout.setVisibility(View.VISIBLE);
        getWeatherdata();
    }


   public void getWeatherdata()
    {

        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        Call<WeatherParent> call = service.getWeather();
        System.out.println("Call : " + call);
        call.enqueue(new Callback<WeatherParent>() {
            @Override
            public void onResponse(Call<WeatherParent> call, Response<WeatherParent> response) {

                System.out.println("Response method called");


                WeatherParent wp = response.body();

                weathers = new ArrayList<>(wp.getConsolidatedWeather());

                setData(weathers, position);
                try {
                    dayName.setText(getDay(weathers.get(position).getApplicableDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<WeatherParent> call, Throwable t) {

                System.out.println("Failure method Called! :" +t.getMessage());
            }
        });

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData(ArrayList<ConsolidatedWeather> arrweather, int position)
    {
        weatherName.setText(arrweather.get(position).getWeatherStateName());
        setImage(weath_img,getWeImage(arrweather.get(position).getWeatherStateAbbr()));
        String thetemp = String.format("%.2f", arrweather.get(position).getTheTemp());
        String mintemp = String.format("%.2f", arrweather.get(position).getMinTemp());
        String maxtemp = String.format("%.2f", arrweather.get(position).getMaxTemp());
        this.mintemp.setText(mintemp);
        this.maxtemp.setText(maxtemp);
        today_temp.setText(thetemp);
        humidity.setText("Humidity : "+ arrweather.get(position).getHumidity().toString()+"%");
        predict.setText("Predictability : "+ arrweather.get(position).getPredictability().toString()+"%");
        initView(arrweather);
        linearLayout.setVisibility(View.GONE);
    }
    public void initView(ArrayList<ConsolidatedWeather> weatherarr)
    {
        weatherarr.remove(0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewAdapter adapter = new RecycleViewAdapter(weatherarr,getActivity().getApplicationContext(), navController);
        recyclerView.setAdapter(adapter);
    }
    public String getWeImage(String img)
    {
        return "https://www.metaweather.com/static/img/weather/png/"+ img +".png";
    }
    public void setImage(ImageView img,String link)
    {
        Picasso.get().load(link).into(img);
    }

    public String getDay(String d) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(d);
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date);

        return dayOfTheWeek;
    }

}