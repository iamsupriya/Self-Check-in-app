package com.example.ease;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    EditText inputpassword;

    EditText inputemail;

    Button btnregister;
    Button btnlogin;

    private FirebaseAuth mAuth;
    ProgressDialog progressDoalog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inputpassword = findViewById(R.id.password);

        inputemail = findViewById(R.id.email);

        btnregister = findViewById(R.id.register);
        btnlogin = findViewById(R.id.login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1= new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent1);
                    finish();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputemail.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();

                if ((email.isEmpty()) ||  (password.isEmpty())) {
                    showMessage("Please Verify all field ");
                }
                else {
                    //Progress Dialog
                    progressDoalog = new ProgressDialog(MainActivity.this);
                    progressDoalog.setMax(100);
                    progressDoalog.setMessage("Account Creating...");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDoalog.show();

                    CreateUserAccount(email, password);
                }
            }
        } );
    }
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

    private void CreateUserAccount(final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDoalog.dismiss();
                            showMessage("account created");

                        } else {
                            progressDoalog.dismiss();
                            showMessage("account creation failed" + task.getException().getMessage());
                        }
                    }
                });
    }


}