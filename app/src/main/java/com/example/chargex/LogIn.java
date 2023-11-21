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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }
    public void login(View v){
        TextView email_view=findViewById(R.id.login_email);
        String email=email_view.getText().toString();
        TextView pw_view=findViewById(R.id.loginPassword);
        String pw=pw_view.getText().toString();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference doc= db.collection("Person").document(email);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc=task.getResult();
                    if(doc.exists()){
                        Map<String,Object> user=doc.getData();
                        String pass=user.get("password").toString();
                        Log.d(TAG,"password is:"+pass);
                        Log.d(TAG,"user enterd:"+pw);
                        if (pass.equals(pw)){
                            Intent profile=new Intent(getApplicationContext(),Profile.class);
                            startActivity(profile);

                            Log.d(TAG,"password checked");
                        }
                        else {
                            Log.d(TAG, "password is wrong");
                        }
                    }
                }
            }
        });

    }

}