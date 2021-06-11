package com.example.onlinebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
EditText mEmail,mPassword;
TextView mregistartionlink,mforgotPassword;
Button mLoginBtn;
ProgressBar progressBar;
ImageView mloginimg;
FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.loginemail);
        mPassword = findViewById(R.id.loginPassword);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginbtn);
        mregistartionlink = findViewById(R.id.registrationlink);
mforgotPassword= findViewById(R.id.forgotPassword);
mloginimg=findViewById(R.id.loginimg);


        mLoginBtn.setOnClickListener(v -> {
            String Email = mEmail.getText().toString().trim();
            String Password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(Email)){
                mEmail.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(Password))
            {
                mPassword.setError("Password is required");
                return;
            }
            if (Password.length()<6) {
                mPassword.setError("Password must be greater than 6 characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            //authenticating the user
fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(task -> {
    if (task.isSuccessful()){
        Toast.makeText(Login.this, "logged in successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),Bookingpage.class));
    }else{
        Toast.makeText(Login.this, "Email or password is incorrect !" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
});


        });
        mloginimg.setOnClickListener(v -> {
            String Email = mEmail.getText().toString().trim();
            String Password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(Email)){
                mEmail.setError("Email is required");
                return;
            }
            if (TextUtils.isEmpty(Password))
            {
                mPassword.setError("Password is required");
                return;
            }
            if (Password.length()<6) {
                mPassword.setError("Password must be greater than 6 characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            //authenticating the user
            fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Bookingpage.class));
                }else{
                    Toast.makeText(Login.this, "Email or password is incorrect !" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });


        });


        mregistartionlink.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Register.class)));

//sending reset email

mforgotPassword.setOnClickListener(v -> {
    EditText resetMail = new EditText(v.getContext());
    AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
    passwordResetDialog.setTitle("Reset Password");
    passwordResetDialog.setMessage("Enter your email to Recieve reset link");
    passwordResetDialog.setView(resetMail);


    passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
        // i will extract email and send reset link
String mail = resetMail.getText().toString();
fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused -> Toast.makeText(Login.this, "Reset Link Has Been Sent To Your Email", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(Login.this, "Link has not Been sent , Check your Email" + e.getMessage(), Toast.LENGTH_SHORT).show());

    });

    passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
        //close the dialog
    });
    passwordResetDialog.create().show();
});



    }
}