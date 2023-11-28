package com.example.chargex;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String username=preferences.getString("username","");
        setContentView(R.layout.activity_profile);
        update();
    }
    public void update() {
        SharedPreferences preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String username=preferences.getString("username","");
        user=new User();
        user.getAccount(username, new callback() {
                    @Override
                    public void onSuccess(String result) {
                        TextView dummy=findViewById(R.id.Name);
                        if(user.getName()!=null) {
                            dummy.setText(user.getName());
                        }
                        dummy=findViewById(R.id.email);
                        if(user.getEmail()!=null) {
                            dummy.setText(user.getEmail());
                        }

                        dummy = findViewById(R.id.cnic);
                        if(user.getCnic()!=null) {
                            dummy.setText(user.getCnic());
                        }
                        dummy=findViewById(R.id.DoB);
                        if(user.getDateOfBirth()!=null) {
                            dummy.setText(user.getDateOfBirth());
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });

        Log.d(TAG,"data fetched");

        Log.d(TAG,"data set");
    }
}