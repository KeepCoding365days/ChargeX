package com.example.chargex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    public void addSlot(View v){
        SharedPreferences preferences=getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String username=preferences.getString("username","customer");
        Slot slot=new Slot();
        TextView view=findViewById(R.id.slotStartTime);
        slot.setStartTime(LocalTime.parse(view.getText().toString()));
        view=findViewById(R.id.slotEndTime);
        slot.setEndTime(LocalTime.parse(view.getText().toString()));
        view=findViewById(R.id.slotDate);
        slot.setDate(LocalDate.parse(view.getText().toString()));
        slot.setUser(username);
        slot.setStation("10XCharger");
        slot.setMachine_id(1);
        slot.setData();




    }
}