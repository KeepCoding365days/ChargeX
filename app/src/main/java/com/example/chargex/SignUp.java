package com.example.chargex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void SignUp(View v){
        TextView email_view=findViewById(R.id.SignUpEmailAddress);
        String email=email_view.getText().toString();
        TextView pw_view=findViewById(R.id.SignUpPassword);
        String pw=pw_view.getText().toString();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Map<String,Object> user= new HashMap<>();
        user.put("email",email);
        user.put("password",pw);
        DocumentReference doc= db.collection("Person").document(email);
        doc.set(user);
    }

}