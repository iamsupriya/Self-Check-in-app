package com.example.ease;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
public class recommend extends AppCompatActivity {

    //  ImageView imageView1;
    ImageView imageview2;
    TextView tv1;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);
        //imageView1= findViewById(R.id.bckg);
        imageview2= findViewById(R.id.logo);
        tv1= findViewById(R.id.textView2);
        next = findViewById(R.id.next);
        setupListeners();
    }
    private void setupListeners() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetonext();
            }
        });
    }
    public void movetonext(){
        Intent intent = new Intent(this, room1.class);
        startActivity(intent);
    }






}
