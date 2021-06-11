package com.example.onlinebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Footer extends AppCompatActivity {
TextView mprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);
       mprofile= findViewById(R.id.profileprofile);
       mprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               
           }
       });
    }
}
