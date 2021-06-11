package com.example.onlinebookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Bookingpage extends AppCompatActivity {
//
    private static final String[] FROM = new String[]{
            "JKUAT","Nairobi","Thika","Juja","Makongeni","Kahawa wendani","kahawa sukari","K-road","allsoaps","Roysambu","Kiimbo","Ruiru","Ruaraka","Kasarani","Githurai","kiambu town"

    };
    private static final String[] DESTINATION =new String[]{
            "JKUAT","Nairobi","Thika","Juja","Makongeni","Kahawa wendani","kahawa sukari","K-road","allsoaps","Roysambu","Kiimbo","Ruiru","Ruaraka","Kasarani","Githurai","kiambu town"

    };
    ImageView accounta;

    TextView fullName,email,verifyMsg,tvdate;
    DatePickerDialog.OnDateSetListener setListener;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    Button resendCode,search_buses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingpage);

        accounta=findViewById(R.id.account);
        accounta.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Useraccountpage.class)));
        //date
        tvdate = findViewById(R.id.tv_date);
//getting the instance of the calendar
        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvdate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                  Bookingpage.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    setListener,year,month,day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
datePickerDialog.show();

        });
        setListener = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            String date = day+"/"+ month1 +"/"+ year1;
            tvdate.setText(date);
        };
        tvdate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Bookingpage.this, (view, year12, month12, day1) -> {
                        month12 = month12 +1;
                        String date = day1 +"/"+ month12 +"/"+ year12;
                        tvdate.setText(date);
                        tvdate.setText(date);
                    },year,month,day);
            datePickerDialog.show();
        });


        //autocomplete for from
        AutoCompleteTextView editText = findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,FROM);
        editText.setAdapter(adapter);

        //autocomplete for destination
        AutoCompleteTextView tolocation = findViewById(R.id.act2);
        ArrayAdapter<String> to = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,DESTINATION);
        tolocation.setAdapter(to);


        fullName = findViewById(R.id.profilefullName);
        email = findViewById(R.id.profileEmail);
        verifyMsg = findViewById(R.id.verifyMsg);
        resendCode = findViewById(R.id.resendCode);
        search_buses=findViewById(R.id.search_buses);
//
        fAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        FirebaseUser user = fAuth.getCurrentUser();
//
        if(!user.isEmailVerified()){
            resendCode.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            resendCode.setOnClickListener(v -> user.sendEmailVerification().addOnSuccessListener(unused -> Toast.makeText(v.getContext(), "Verification Email Has Been Sent",
                    Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Log.d("TAG", "onFailure: Verification Email not Sent please check your email "+ e.getMessage())));


        }
        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, (documentSnapshot, error) -> {
            assert documentSnapshot != null;
            fullName.setText(documentSnapshot.getString("fName"));
            email.setText(documentSnapshot.getString("Email"));
        });


        search_buses.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Busselection.class)));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();


    }

    public void account(View view) {
        startActivity(new Intent(getApplicationContext(),Useraccountpage.class));
    }
}
