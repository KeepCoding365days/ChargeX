package com.example.chargex;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VerifyStations extends AppCompatActivity {
    List<Station> stationList;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count=0;
        stationList=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_stations);
        populate();
    }

    public void next (View v){
        if(count<stationList.size()-1){
            count++;
            updateUI();
        }
    }
    public void verify(View v){
        stationList.get(count).setStatus("verified");
        stationList.get(count).setData();
    }


    private void updateUI(){
        if(count<stationList.size()) {
            TextView v = findViewById(R.id.stationName);
            v.setText(stationList.get(count).getName());
            v = findViewById(R.id.stationEmail);
            v.setText("Email: " + stationList.get(count).getEmail());
            v = findViewById(R.id.stationPhone);
            v.setText("Phone " + stationList.get(count).getContactNumber());
            v = findViewById(R.id.stationAddress);
            v.setText("Address: " + stationList.get(count).getAddress());
        }
        else{
            Intent i=new Intent(getApplicationContext(), AdminIndex.class);
            startActivity(i);
        }
    }

    public void populate(){
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
    }

    public void getStations(callback async){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Station").whereEqualTo("status","applied")
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

                                station.getAccount(document.getData().get("name").toString(),
                                        new callback() {
                                            @Override
                                            public void onSuccess(String result) {
                                                stationList.add(station);
                                                tasks_completed[0]++;
                                                if(tasks_completed[0] ==tot_tasks) {
                                                    async.onSuccess("stations added!");
                                                }
                                                Log.d(TAG,"station added");
                                            }


                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.d(TAG,"Failed to grab station");
                                            }
                                        });
                            }

                        }
                        else{
                            async.onFailure(task.getException());
                        }
                    }
                });

    }
}