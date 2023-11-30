package com.example.chargex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookSlot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);
    }

    public void moveStations(View v){
        SharedPreferences preferences=getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String username=preferences.getString("username","customer");
        Slot slot=new Slot();
        TextView view=findViewById(R.id.slotStartTime);
        String startTime=view.getText().toString();
        view=findViewById(R.id.slotEndTime);
        String endTime=view.getText().toString();
        view=findViewById(R.id.slotDate);
        String date=view.getText().toString();

        Intent i=new Intent(getApplicationContext(), ViewStations.class);
        i.putExtra("startTime",startTime);
        i.putExtra("endTime",endTime);
        i.putExtra("date",date);
        startActivity(i);


    }
}