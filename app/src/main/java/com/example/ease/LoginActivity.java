package com.example.ease;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    EditText inputemail;
    EditText inputpassword;
    Button btnlogin;
    Button btnregister;
    private FirebaseAuth mAuth;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        inputemail = findViewById(R.id.username);
        inputpassword = findViewById(R.id.password);
        btnregister = findViewById(R.id.register);
        btnlogin = findViewById(R.id.login);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputemail.getText().toString();
                final String password = inputpassword.getText().toString();
                if (email.isEmpty()|| password.isEmpty()){
                    showMessage("Please Verify all Fields");
                }
                else{
                    //Progress Dialog
                    progressDoalog = new ProgressDialog(LoginActivity.this);
                    progressDoalog.setMax(100);
                    progressDoalog.setMessage("Please Wait...");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDoalog.show();
                    signIn(email,password);
                }

            }
        });


    }
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    private void signIn(String mail,String password) {
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDoalog.dismiss();
                    showMessage("Welcome To Easy");

                    Intent intent2= new Intent(LoginActivity.this,recommend.class);
                    startActivity(intent2);
                    finish();
                }
                else{
                    progressDoalog.dismiss();
                    showMessage("Login failed" + task.getException().getMessage());

                }
            }
        });
    }




}