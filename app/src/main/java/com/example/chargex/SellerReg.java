package com.example.chargex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SellerReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_reg);
    }
    public void register(View v){
        Station station=new Station();
        TextView dummy=findViewById(R.id.stationName);
        station.setName(dummy.getText().toString());
        dummy=findViewById(R.id.stationEmail);
        station.setEmail(dummy.getText().toString());
        dummy=findViewById(R.id.stationPassword);
        station.setPassword(dummy.getText().toString());
        station.setData();

    }
    public void login(View v){
        Intent login=new Intent(getApplicationContext(),StationLogin.class);
        startActivity(login);
    }

}