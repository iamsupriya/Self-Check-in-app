package com.example.ease;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
public class quit extends AppCompatActivity {

    //  ImageView imageView1;
    ImageView imageview2;
    TextView tv1;
    Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quit);
        //imageView1= findViewById(R.id.bckg);
        imageview2= findViewById(R.id.logo);
        tv1= findViewById(R.id.ss);
        signout = findViewById(R.id.btnsignout);

        setupListeners();
    }
    private void setupListeners() {
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetologin();
            }
        });

    }
    public void movetologin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }







}



