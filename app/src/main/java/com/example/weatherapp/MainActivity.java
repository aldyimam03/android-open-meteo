package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.helper.WeatherCode;
import com.example.weatherapp.retrofit.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener, View.OnClickListener {
    private final String TAG = "MainActivity";
    private static final String JSON_URL = "https://api.open-meteo.com/v1/forecast?latitude=-7.98&longitude=112.63&daily=weathercode&current_weather=true&timezone=auto";
    private RequestQueue requestQueue;
    private ActivityMainBinding binding;
    private WeatherAdapter weatherAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.requestQueue = Volley.newRequestQueue(this);

        binding.btnVolley.setOnClickListener(this);
        binding.btnRetrofit.setOnClickListener(this);
    }

    private void getDataFromRetrofit() {
        ApiService.endpoint().getData().enqueue(new Callback<MainModel>()  {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()){
                    MainModel data = response.body();

                    binding.ivCuaca.setImageResource(WeatherCode.getCodeIcon(data.getCurrent_weather().getWeathercode()));
                    binding.tvTemperature.setText(data.getCurrent_weather().getTemperature() + "°C");
                    binding.tvKondisi.setText(WeatherCode.getKondisi(data.getCurrent_weather().getWeathercode()));
                    binding.tvWindSpeed.setText("Windspeed : " + data.getCurrent_weather().getWindspeed());
                    binding.tvKoordinat.setText("Koordinat : " + data.getLatitude() + ", " + data.getLongitude());

                    weatherAdapter = new WeatherAdapter(data.getDaily().getTime(), data.getDaily().getWeathercode(), data.getDaily().getTime().size());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    binding.rvWeathers.setLayoutManager(layoutManager);
                    binding.rvWeathers.setHasFixedSize(true);
                    binding.rvWeathers.setAdapter(weatherAdapter);

                    Log.d(TAG, data.toString());
                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    private void getDataFromVolley() {
        JsonObjectRequest jr = new JsonObjectRequest(
                Request.Method.GET,
                JSON_URL,
                null,
                this,
                this
        );
        this.requestQueue.add(jr);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject crnWeather = response.getJSONObject("current_weather");
            int weatherCode = Integer.parseInt(crnWeather.getString("weathercode"));

            JSONObject daily = response.getJSONObject("daily");
            JSONArray time = daily.getJSONArray("time");
            JSONArray wCode = daily.getJSONArray("weathercode");

            List<String> timeList = new ArrayList<String>();
            List<Integer> wCodeList = new ArrayList<Integer>();

            for (int i = 0; i < time.length(); i++) {
                timeList.add(time.getString(i));
                wCodeList.add(Integer.parseInt(wCode.getString(i)));
            }

            String latitude = response.getString("latitude");
            String longitude = response.getString("longitude");

            binding.ivCuaca.setImageResource(WeatherCode.getCodeIcon(weatherCode));
            binding.tvTemperature.setText(crnWeather.getString("temperature") + "°C");
            binding.tvKondisi.setText(WeatherCode.getKondisi(Integer.parseInt(crnWeather.getString("weathercode"))));
            binding.tvWindSpeed.setText("Windspeed : " + crnWeather.getString("windspeed"));
            binding.tvKoordinat.setText("Koordinat : " + latitude + ", " + longitude);

            weatherAdapter = new WeatherAdapter(timeList, wCodeList, timeList.size());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            binding.rvWeathers.setLayoutManager(layoutManager);
            binding.rvWeathers.setHasFixedSize(true);
            binding.rvWeathers.setAdapter(weatherAdapter);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnVolley.getId()){
            getDataFromVolley();
        }else if(view.getId() == binding.btnRetrofit.getId()){
            getDataFromRetrofit();
        }
    }
}