package com.example.chargex;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StationLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_login);
    }
    public void logIn(View v){
        Station station=new Station();
        TextView input=findViewById(R.id.stationName);
        station.getAccount(input.getText().toString());
        input=findViewById(R.id.stationPassword);
        if(station.getPassword().equals(input.getText().toString())){
            Intent index=new Intent(getApplicationContext(),StationIndex.class);
            startActivity(index);
        }
        Log.d(TAG,"logIn done");
    }
    public void Register(View v){
        Intent register=new Intent(getApplicationContext(),SellerReg.class);
        startActivity(register);
    }


}