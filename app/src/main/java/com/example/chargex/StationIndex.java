package com.example.chargex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StationIndex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_index);
    }
    public void ViewProfile(View v){
        Intent profile=new Intent(getApplicationContext(),StationProfile.class);
        startActivity(profile);
    }


}