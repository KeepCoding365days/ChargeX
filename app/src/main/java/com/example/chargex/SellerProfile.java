package com.example.chargex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class SellerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
    }
    public void apply(View v){
        Station station=new Station();
        station.setLongitude(34);
        station.setLatitude(29);//set longitude and latitude here
        //add name,email,address,contact Number text fields and update btn in activity_seller_profile.xml

    }

}