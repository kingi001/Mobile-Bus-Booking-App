package com.example.onlinebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Busselection extends AppCompatActivity {
RelativeLayout selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busselection);
        selected = findViewById(R.id.sselectbus);

        selected.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Seatchart.class)));
    }
}