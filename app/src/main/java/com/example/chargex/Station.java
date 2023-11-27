package com.example.chargex;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Station {
    private String email;
    private String password;
    private String address;
    private double longitude;
    private double latitude;
    private String contactNumber;
    private double avg_rating;

    private String name;
    public Station(){
        name="";
        email="";
        contactNumber="";
        password="";
        address="";
        avg_rating=0.0;

    }
    public void setName(String name){
        this.name=name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setNumber(String number){
        this.contactNumber=number;
    }
    public void setPassword(String pw){
        this.password=pw;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public void setRating(double rating){
        this.avg_rating=rating;
    }

    public void setLongitude(double longitude){this.longitude=longitude;}

    public void setLatitude(double latitude){this.latitude=latitude;}
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getContactNumber(){
        return this.contactNumber;
    }
    public String getAddress(){
        return this.address;
    }
    public double getRating(){
        return this.avg_rating;
    }
    public String getPassword(){
        return this.password;
    }
    public double getLongitude(){return this.longitude;}
    public double getLatitude(){return this.latitude;}

    public String Encrypt(String pw){
        char[] arr=pw.toCharArray();
        for(int i=0;i<arr.length;i++){
            arr[i]=(char)(((int) arr[i])+2);
        }
        pw=String.valueOf(arr);
        return pw;
    }

    public String Decrypt(String pw){
        char[] arr=pw.toCharArray();
        for(int i=0;i<arr.length;i++){
            arr[i]=(char)(((int) arr[i])-2);
        }
        pw=String.valueOf(arr);
        return pw;
    }

    public void getAccount(String name,callback async){

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference doc=db.collection("Station").document(name);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot user_data=task.getResult();
                    if(user_data.exists()){
                        Map<String,Object> data=user_data.getData();
                        if(data.containsKey("name")){
                            Log.d(TAG,"name is set");
                            setName(data.get("name").toString());
                        }
                        if(data.containsKey("email")){
                            Log.d(TAG,"email is set");
                            setEmail(data.get("email").toString());
                            //Log.d(TAG,""+user.getEmail());
                        }
                        if(data.containsKey("longitude")){
                            Log.d(TAG,"longitude is set");
                            setLongitude((double)(data.get("longitude")));
                        }
                        if(data.containsKey("latitude")){
                            Log.d(TAG,"latitude is set");
                            setLatitude((double)(data.get("latitude")));
                        }
                        if(data.containsKey("address")){
                            setAddress(data.get("address").toString());
                        }
                        if(data.containsKey("contactNumber")){
                            setNumber(data.get("contactNumber").toString());
                        }
                        if(data.containsKey("password")){
                            setPassword(Decrypt(data.get("password").toString()));
                        }
                        async.onSuccess("Data set!");
                    }
                    else{
                        async.onFailure(new Exception("Document not found!"));
                    }
                }
                else{
                    async.onFailure(task.getException());
                }
            }
        });

    }
    public void setData(){
        Map<String,Object> data=new HashMap<>();
        data.put("email",this.getEmail());
        data.put("name",this.getName());
        data.put("address",this.getAddress());
        data.put("password",Encrypt(this.getPassword()));
        data.put("contactNumber",this.getContactNumber());
        data.put("longitude",this.getLongitude());
        data.put("latitude",this.getLatitude());
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference doc=db.collection("Station").document(this.getName());
        doc.set(data);
    }
}