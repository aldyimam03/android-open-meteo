package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.databinding.RowItemBinding;
import com.example.weatherapp.helper.WeatherCode;

import java.util.List;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.VHWeather> {
    private List<String> dates;
    private List<Integer> weatherCodes;
    private int size;

    public WeatherAdapter(List<String> dates, List<Integer> weatherCodes, int size) {
        this.dates = dates;
        this.weatherCodes = weatherCodes;
        this.size = size;
    }

    @NonNull
    @Override
    public VHWeather onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemBinding binding = RowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VHWeather(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VHWeather holder, int position) {
        holder.bind(this.dates.get(position), this.weatherCodes.get(position));
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class VHWeather extends RecyclerView.ViewHolder{
        final RowItemBinding binding;

        VHWeather(RowItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String date, int weatherCode){
            binding.tvTanggal.setText(date);
            binding.tvKondisi.setText(WeatherCode.getKondisi(weatherCode));
            binding.imageView.setImageResource(WeatherCode.getCodeIcon(weatherCode));
        }


    }

}
