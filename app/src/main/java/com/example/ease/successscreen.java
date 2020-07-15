package com.example.ease;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class successscreen extends AppCompatActivity {

    //  ImageView imageView1;
    ImageView imageview2;
    TextView tv1;
    Button signout;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successscreen);
        //imageView1= findViewById(R.id.bckg);
        imageview2= findViewById(R.id.logo);
        tv1= findViewById(R.id.ss);
        signout = findViewById(R.id.btnsignout);
        next = findViewById(R.id.btnnext);
        setupListeners();
    }
    private void setupListeners() {
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetologin();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetonext();
            }
        });
    }
    public void movetologin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void movetonext(){
        Intent intent = new Intent(this, checkin.class);
        startActivity(intent);
    }






}


