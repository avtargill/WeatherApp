package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET("3534")
    Call<WeatherParent> getWeather();
}
