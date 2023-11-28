package com.example.chargex;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        station.getAccount(input.getText().toString(), new callback() {
            @Override
            public void onSuccess(String result) {
                TextView input=findViewById(R.id.stationPassword);
                Log.d(TAG,"logIn done");
                Log.d(TAG,"pw is:"+station.getPassword());
                if(station.getPassword().equals(input.getText().toString())){
                    SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("loggedIn", "True");
                    editor.putString("role","user");
                    editor.putString("username",station.getName());
                    editor.apply();
                    Intent index=new Intent(getApplicationContext(),StationIndex.class);
                    startActivity(index);
                }
            }
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG,"logIn failed");
            }
        });


    }
    public void Register(View v){
        Intent register=new Intent(getApplicationContext(),SellerReg.class);
        startActivity(register);
    }


}