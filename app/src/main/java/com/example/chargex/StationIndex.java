package com.example.chargex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class StationIndex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_index);
    }
    public void ViewProfile(View v){
        Intent profile=new Intent(getApplicationContext(), StationProfile.class);
        startActivity(profile);
    }
    public void apply_reg(View v){
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String username = preferences.getString("username", "");

        Station station=new Station();
        station.getAccount(username, new callback() {
            @Override
            public void onSuccess(String result) {
                station.setStatus("applied");
                station.setData();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }



}