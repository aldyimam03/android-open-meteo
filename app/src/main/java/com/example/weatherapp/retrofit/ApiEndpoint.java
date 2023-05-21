package com.example.weatherapp.retrofit;

import com.example.weatherapp.MainModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoint {
    @GET("forecast?latitude=-7.98&longitude=112.63&daily=weathercode&current_weather=true&timezone=auto")
    Call<MainModel> getData();
}
