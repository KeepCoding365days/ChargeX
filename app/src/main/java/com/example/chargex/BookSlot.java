package com.example.chargex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import android.Manifest;

public class BookSlot extends AppCompatActivity implements OnMapReadyCallback {
    private List<Station> stationList;
    private LatLng userLocation;

    GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static int LOCATION_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        stationList=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment!=null){
            mapFragment.getMapAsync(this);}
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermission();
        getMachines(new callback() {


            @Override
            public void onSuccess(String result) {
                if(stationList.size()==0){
                    Log.d("BookSlotActivity", "Stations not found");
                }
                else {
                    Log.d("BookSlotActivity", "Stations Found");
                }

                if(stationList.size()==0){
                    Log.d("BookSlotActivity", "Stations not found");
                }
                else {
                    Log.d("BookSlotActivity", "Stations Found");
                }

                for(int i=0;i< stationList.size()-1;i++){
                    for(int j=0;j<stationList.size()-1;j++){
                        double firstValue=Math.sqrt(Math.pow(stationList.get(j).getLatitude()- userLocation.latitude,2)+Math.pow(stationList.get(j).getLongitude()-userLocation.longitude,2));
                        double secondValue=Math.sqrt(Math.pow(stationList.get(j+1).getLatitude()- userLocation.latitude,2)+Math.pow(stationList.get(j+1).getLongitude()-userLocation.longitude,2));
                        if(firstValue>secondValue){
                            Station temp=stationList.get(j);
                            stationList.set(j,stationList.get(j+1));
                            stationList.set(j+1,temp);

                        }
                    }
                }
                //all station with free slots will have their data stored in stationList.
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getUserLocation();
        } else {
            requestForpermissions();
        }
    }


        private void requestForpermissions(){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode== LOCATION_REQUEST_CODE){
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permission Accepted",Toast.LENGTH_SHORT).show();
                    getUserLocation();
                }
                else{
                    Toast.makeText(this,"Permission Rejected",Toast.LENGTH_SHORT).show();
                }
            }
        }



    private void getUserLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>(){
            @Override
            public void onSuccess(Location location){
                if(location!=null){
                    double lat=location.getLatitude();
                    double longitude=location.getLongitude();
                    userLocation=new LatLng(lat,longitude);
                    map.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                    map.animateCamera(CameraUpdateFactory.zoomTo(12));
                    MarkerOptions options=new MarkerOptions().position(userLocation).title("My location");
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    map.addMarker(options);

                }
            }
        });
    }

    public void getMachines(callback async){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Stations")
                .whereEqualTo("status","Free").get()
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        if(stationList.size()==0){
            Log.d("BookSlotActivity", "Stations not found");
        }
        else {
            Log.d("BookSlotActivity", "Stations Found");
        }

        if(map!=null){
            map.getUiSettings().setMyLocationButtonEnabled(true);}
        for(int i=0;i<5;i++){
            if(i>=stationList.size())
                break;
            LatLng location=new LatLng(stationList.get(i).getLatitude(),stationList.get(i).getLongitude());
            MarkerOptions options;
            if(i==0) {
                 options=new MarkerOptions().position(location).title("Closest Station");
            }
           else if(i==1){
                options=new MarkerOptions().position(location).title("Second Closest Station");
            }
           else if(i==2) {
                 options=new MarkerOptions().position(location).title("Third Closest Station");
            }
           else if(i==3){
                options=new MarkerOptions().position(location).title("Fourth Closest Station");
            }
            else{
                options=new MarkerOptions().position(location).title("Fifth Closest station");
            }


            if(i==0||i==1) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }
            else if(i==2||i==3) {
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            }
            else  options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            map.addMarker(options);
        }

    }

    private float getColorForMarker(int markerIndex) {
        switch (markerIndex) {
            case 0:
                return BitmapDescriptorFactory.HUE_RED;
            case 1:
                return BitmapDescriptorFactory.HUE_BLUE;
            case 2:
                return BitmapDescriptorFactory.HUE_GREEN;
            case 3:
                return BitmapDescriptorFactory.HUE_YELLOW;
            case 4:
                return BitmapDescriptorFactory.HUE_MAGENTA;
            default:
                return BitmapDescriptorFactory.HUE_RED;
        }
    }

    // Create a custom BitmapDescriptor with the specified color

}