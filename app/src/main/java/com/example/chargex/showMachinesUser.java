package com.example.chargex;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class showMachinesUser extends AppCompatActivity {
    int count;
    List<ChargingMachine> machineList;
    double Latitude;
    double Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Latitude=getIntent().getDoubleExtra("latitude",31.5497);
        Longitude=getIntent().getDoubleExtra("longitude",74.3436);
        Log.d(TAG,"Longitude is:"+Longitude);
        Log.d(TAG,"Latitude is:"+Latitude);
        count=0;
        machineList=new ArrayList<>();
        final String Name[] = new String[0];
        StationRecord record=new StationRecord();
        getStation(Name, new callback() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG,"Selected station is:"+Name[0]);
                record.getMachines(new callback() {

                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG,"machines fetched are"+record.machineList.size());
                        updateUI();

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG,"Machine fetched failed");
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG,"Selected not found:"+Name[0]);
            }
        });
        super.onCreate(savedInstanceState);


        Log.d(TAG,"Machine fetched start");

        Log.d(TAG,"Machine fetched end");

        setContentView(R.layout.activity_show_machines_user);
    }
    public void next(View v){
        count++;
        updateUI();
    }

    @SuppressLint("SetTextI18n")
    private void updateUI(){
        if(count<machineList.size()) {
            TextView v = findViewById(R.id.stationName);
            v.setText(machineList.get(count).getStation().getName());
            v = findViewById(R.id.machineID);
            v.setText("ID: " + machineList.get(count).getId());
            v = findViewById(R.id.machineSpeed);
            v.setText("Speed" + machineList.get(count).getChargingSpeed()+"KW/h");
            v = findViewById(R.id.machinePrice);
            v.setText("Rate: " + machineList.get(count).getPrice()+"PKR/h");
            v = findViewById(R.id.stationAddress);
            v.setText("Address: " + machineList.get(count).getStation().getAddress());
        }
        else{
            Intent i=new Intent(getApplicationContext(), CustomerIndex.class);
            startActivity(i);
        }
    }

    /*public void populate(){
        getStations(new callback() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG,"Count is");
                updateUI();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }*/

    public void getStation(final String Name[],callback async){

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Station").where(Filter.and(Filter.equalTo("latitude",Latitude),
                        Filter.equalTo("longitude",Longitude)))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int tot_tasks=task.getResult().size();
                            final int[] tasks_completed = {0};
                            for(QueryDocumentSnapshot document:task.getResult()){
                                Station station=new Station();
                                Log.d(TAG,"staion name is"+document.getData().get("name").toString());
                                Name[0] =document.getData().get("name").toString();
                            }

                        }
                        else{
                            async.onFailure(task.getException());
                        }
                    }
                });
    }


}