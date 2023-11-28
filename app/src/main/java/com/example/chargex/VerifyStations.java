package com.example.chargex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class VerifyStations extends AppCompatActivity {
    List<Station> stationList;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_stations);
        populate();
    }

    public void next (View v){
        //if(stationList.size()
    }
    public void populate(){
        getStations(new callback() {
            @Override
            public void onSuccess(String result) {
                TextView v=findViewById(R.id.stationName);
                v.setText(stationList.get(0).getName());
                v=findViewById(R.id.stationEmail);
                v.setText(stationList.get(0).getEmail());
                v=findViewById(R.id.stationPhone);
                v.setText(stationList.get(0).getContactNumber());
                v=findViewById(R.id.stationAddress);
                v.setText(stationList.get(0).getAddress());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void getStations(callback async){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Stations").whereEqualTo("status","applied")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                                Station station=new Station();
                                station.getAccount(document.getData().get("name").toString(),
                                        new callback() {
                                            @Override
                                            public void onSuccess(String result) {
                                                stationList.add(station);
                                            }

                                            @Override
                                            public void onFailure(Exception e) {

                                            }
                                        });
                            }
                            async.onSuccess("stations added!");
                        }
                        else{
                            async.onFailure(task.getException());
                        }
                    }
                });

    }
}