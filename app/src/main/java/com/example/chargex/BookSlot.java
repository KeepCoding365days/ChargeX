package com.example.chargex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.List;

public class BookSlot extends AppCompatActivity {
    private List<Station> stationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        stationList=new ArrayList<>();
        getMachines(new callback() {
            @Override
            public void onSuccess(String result) {
                //all station with free slots will have their data stored in stationList.
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);
    }
    public void getMachines(callback async){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Stations").document("*")
                .collection("ChargingStations").whereEqualTo("status","Free").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int tot_tasks=task.getResult().size();
                            final int[] tasks_completed = {0};
                        for(QueryDocumentSnapshot doc:task.getResult()){
                            Station station=new Station();
                            station.getAccount(doc.getData().get("station").toString(), new callback() {
                                @Override
                                public void onSuccess(String result) {
                                    stationList.add(station);
                                    tasks_completed[0]++;
                                    if(tasks_completed[0]==tot_tasks){
                                        async.onSuccess("All stations added");
                                    }
                                    else{
                                        async.onFailure(new Exception("data not fetched"));
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });
                        }
                        }
                    }
                });
    }
}