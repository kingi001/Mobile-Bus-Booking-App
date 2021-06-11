package com.example.onlinebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhone;
    ImageView mregisterimg;
Button mRegistartionBtn;
TextView mLoginBtnlink;
FirebaseAuth fAuth;
ProgressBar progressBar;
FirebaseFirestore fstore;
String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.profilefullName);
        mEmail = findViewById(R.id.profileEmail);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.profilePhone);
        mRegistartionBtn=findViewById(R.id.registerBtn);
        mLoginBtnlink = findViewById(R.id.LoginBtnlink);
        mregisterimg = findViewById(R.id.registerimg);
        //initializing instances
        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
//if the user has account it would automatically log him/her in
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Bookingpage.class));
            finish();
        }

        //events after the register button is clicked


      mRegistartionBtn.setOnClickListener(v -> {
          String email = mEmail.getText().toString().trim();
          String password = mPassword.getText().toString().trim();

          // storing full name and phone number to the database
String fullName = mFullName.getText().toString();
String phone = mPhone.getText().toString();
          if (TextUtils.isEmpty(email)){
              mEmail.setError("Email is required");
              return;
          }
          if (TextUtils.isEmpty(password))
          {
              mPassword.setError("Password is required");
              return;
          }
          if (password.length()<6) {
              mPassword.setError("Password must be greater than 6 characters");
              return;
          }
          progressBar.setVisibility(View.VISIBLE);

          //register the user in firebase

          fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
if(task.isSuccessful()){


    //sending verification link for authentication
    FirebaseUser fuser = fAuth.getCurrentUser();
    fuser.sendEmailVerification().addOnSuccessListener(unused -> Toast.makeText(Register.this, "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Log.d("TAG", "onFailure: Verification Email not Sent please check your email "+ e.getMessage()));
//
Toast.makeText(Register.this, "User Account Created", Toast.LENGTH_SHORT).show();
userID= fAuth.getCurrentUser().getUid();
DocumentReference documentReference = fstore.collection("users").document(userID);
Map<String,Object> user = new HashMap<>();
user.put("fName",fullName);
user.put("Email",email);
user.put("phone",phone);
documentReference.set(user).addOnSuccessListener(aVoid -> Log.d("TAG", "onSuccess: user profile is created for " + userID));

startActivity(new Intent(getApplicationContext(),Bookingpage.class));
}else{
Toast.makeText(Register.this, "Error occured" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
progressBar.setVisibility(View.GONE);
}
          });
      });


        mregisterimg.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            // storing full name and phone number to the database
            String fullName = mFullName.getText().toString();
            String phone = mPhone.getText().toString();
            if (TextUtils.isEmpty(email)){
                mEmail.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(password))
            {
                mPassword.setError("Password is required");
                return;
            }
            if (password.length()<6) {
                mPassword.setError("Password must be greater than 6 characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            //register the user in firebase

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){


                    //sending verification link for authentication
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(unused -> Toast.makeText(Register.this, "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Log.d("TAG", "onFailure: Verification Email not Sent please check your email "+ e.getMessage()));
//
                    Toast.makeText(Register.this, "User Account Created", Toast.LENGTH_SHORT).show();
                    userID= fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("fName",fullName);
                    user.put("Email",email);
                    user.put("phone",phone);
                    documentReference.set(user).addOnSuccessListener(aVoid -> Log.d("TAG", "onSuccess: user profile is created for " + userID));

                    startActivity(new Intent(getApplicationContext(),Bookingpage.class));
                }else{
                    Toast.makeText(Register.this, "Error occured" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }


            });
        });
        mLoginBtnlink.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
    }
}