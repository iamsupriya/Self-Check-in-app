package com.example.ease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class room1 extends AppCompatActivity {
    Button next;
    Button booknow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room1);
        setupUI();
        setupListeners();

    }
    private void setupUI(){
        next = findViewById(R.id.next);
        booknow = findViewById(R.id.booknow);

    }
    private void setupListeners() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetonext();
            }
        });
        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movetobooknow();
            }
        });

    }
    public void movetonext(){
        Intent intent = new Intent(this, room2.class);
        startActivity(intent);
    }
    public void movetobooknow(){
        Intent intent = new Intent(this, booking.class);
        startActivity(intent);
    }






}

