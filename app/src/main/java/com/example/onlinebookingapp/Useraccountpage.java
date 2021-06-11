package com.example.onlinebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Useraccountpage extends AppCompatActivity {
ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraccountpage);
        home = findViewById(R.id.backhome);
        home.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Bookingpage.class)));
    }
}