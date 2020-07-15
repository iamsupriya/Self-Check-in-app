package com.example.ease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Objects;

public class booking extends AppCompatActivity {
    int Qroom[]= {101,102,103,104,105};
    int Droom[]= {201,202,203,204,205};
    int Sroom[]= {301,302,303,304,305};
    int Broom[]= new int[5];
    private EditText e1, e2, e3;
    private TextView t1, t2;
    int num1, num2, num3;
    int i=0;
    private Button sea;

    private Button queen;
    private Button delux;
    private Button btnback;
    private Button btnnext;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.booking);


        btnback = findViewById(R.id.goback);
        btnnext = findViewById(R.id.next);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1= new Intent(booking.this,recommend.class);
                    startActivity(intent1);
                    finish();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1= new Intent(booking.this,successscreen.class);
                    startActivity(intent1);
                    finish();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    // a public method to get the input numbers
    public boolean getNumbers()
    {

        // defining the edit text 1 to e1
        e1 = (EditText)findViewById(R.id.num1);

        // defining the edit text 2 to e2
        e2 = (EditText)findViewById(R.id.num2);
        e3 = (EditText)findViewById(R.id.num3);

        // defining the text view to t1
        t1 = (TextView)findViewById(R.id.result);
        t2 = (TextView)findViewById(R.id.bookedroom);

        sea = (Button) findViewById(R.id.btnsea);
        queen = (Button) findViewById(R.id.btnqueen);
        delux = (Button) findViewById(R.id.btndelux);


        // taking input from text box 1
        String s1 = e1.getText().toString();

        // taking input from text box 2
        String s2 = e2.getText().toString();
        String s3 = e3.getText().toString();

        // condition to check if box is not empty
        if ((s1.equals(null) && s2.equals(null)&& s3.equals(null))
                || (s1.equals("") && s2.equals("")&& s3.equals(""))) {

            String result = "Please enter a value";
            t1.setText(result);

            return false;
        }
        else {
            // converting string to int.
            num1 = Integer.parseInt(e1.getText().toString());

            // converting string to int.
            num2 = Integer.parseInt(e2.getText().toString());
            num3 = Integer.parseInt(e3.getText().toString());
        }

        return true;
    }
    public void doMul(View v) throws Exception
    {

        // get the input numbers
        if (getNumbers()) {

            queen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {int sum = num1 * num2 *num3* 500;
                        t1.setText("Estimated Price is:  " + Integer.toString(sum));
                        if(num3<=Qroom.length){
                            for (i=0; i<num3;i++) {
                                Broom[i]=Qroom[i];
                                for (i=0;i<Broom.length;i++){
                                    if (Broom[i]!= 0){
                                        int a=Broom[i];
                                        t2.setText(Integer.toString(a));
                                    }
                                }
                            }
                        }else {showMessage("Rooms available is less than requirement ");}
                    }catch (Throwable ex){
                        ex.printStackTrace();
                    }
                }
            });

            delux.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {int sum = num1 * num2*num3 * 100;
                        t1.setText("Estimated Price is:  "+ Integer.toString(sum));
                        if(num3<=Droom.length){
                            for (i=0; i<=num3;i++){
                                Broom[i]=Droom[i];

                                t2.setText(Arrays.toString(Broom));
                                }



                        }else {showMessage("Rooms available is less than requirement ");}

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });

            sea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {int sum = num1 * num2*num3 * 300 ;
                        t1.setText("Estimated Price is:  " + Integer.toString(sum));
                        if(num3<=Sroom.length){
                            for (i=0; i<=num3;i++){
                                Broom[i]=Sroom[i];
                                if((Broom[i]==301)||(Broom[i]==302)||(Broom[i]==303)||(Broom[i]==304)||(Broom[i]==305)){
                                    int a=Broom[i];
                                    t2.setText(Integer.toString(a));
                                }

                            }
                        }else {showMessage("Rooms available is less than requirement ");}

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });

        }
    }



    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

}



