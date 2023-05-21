package com.example.weatherapp.helper;

import com.example.weatherapp.R;

public class WeatherCode {
    static int[] cerah = {1, 2, 3};
    static int[] cerahBerawan = {45, 48};
    static int[] berawan = {51, 53, 55};
    static int[] hujan = {56, 57, 66, 67, 85, 86};
    static int[] hujanLebat = {61, 63, 65, 80, 81, 82};
    static int[][] arrKondisi = {cerah, cerahBerawan, berawan, hujan, hujanLebat};
    static String[] stringKondisi = {"Cerah", "Cerah Berawan", "Berawan", "Hujan", "Hujan Lebat"};
    static int[] kodeIcon = {R.drawable.cerah, R.drawable.cerah_berawan, R.drawable.berawan, R.drawable.hujan, R.drawable.hujan_lebat};

    public static String getKondisi(int weatherCode) {
        for (int i = 0; i < arrKondisi.length; i++) {
            for (int j = 0; j < arrKondisi[i].length; j++) {
                if (weatherCode == arrKondisi[i][j]){
                    return stringKondisi[i];
                }
            }
        }
        return "Cuaca Tidak Terdeteksi";
    }

    public static int getCodeIcon(int weatherCode) {
        for (int i = 0; i < arrKondisi.length; i++) {
            for (int j = 0; j < arrKondisi[i].length; j++) {
                if (weatherCode == arrKondisi[i][j]){
                    return kodeIcon[i];
                }
            }
        }
        return R.drawable.cerah;
    }
}
