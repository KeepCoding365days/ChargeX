package com.example.chargex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddSlot extends AppCompatActivity {
    private String stationName;
    private String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Station station=new Station();
        SharedPreferences preferences=getSharedPreferences("user_data",Context.MODE_PRIVATE);
        stationName=preferences.getString("username","station");
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_slot);
    }
    public void addSlot(View v){
        Slot slot=new Slot();
        TextView view=findViewById(R.id.slotStartTime);
        slot.setStartTime(view.getText().toString());
        view=findViewById(R.id.slotEndTime);
        slot.setEndTime(view.getText().toString());
        Spinner spin=findViewById(R.id.selectMachine);

        Station station=new Station();
        ChargingMachine machine =new ChargingMachine();
        SharedPreferences preferences=getSharedPreferences("user_data", Context.MODE_PRIVATE);

    }

}