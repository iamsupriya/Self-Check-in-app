package com.example.ease;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class checkin extends AppCompatActivity {
    private static final String TAG = checkin.class.getSimpleName();
    private Button btnChoose, btnUpload, btnnext, btnback, btnadd;
    private ImageView imageView;
    private TextView txtDetails;
    private EditText inputfullname;
    private EditText inputemailaddress;
    private EditText inputphone;
    private FirebaseStorage storage;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mFirebaseDatabase;

    private FirebaseDatabase mFirebaseInstance;
    private StorageReference storageReference;
    private Uri filePath;
    private String userId;

    private final int PICK_IMAGE_REQUEST = 71;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        firebaseAuth= FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        mFirebaseInstance.getReference("app_title").setValue("Easy");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputfullname = (EditText) findViewById(R.id.fullname);
        inputemailaddress = (EditText) findViewById(R.id.emailaddress);
        inputphone = (EditText) findViewById(R.id.phone);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnadd = (Button) findViewById(R.id.addbtn);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnnext = (Button) findViewById(R.id.next);
        btnback = (Button) findViewById(R.id.back);
        imageView = (ImageView) findViewById(R.id.imgView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1= new Intent(checkin.this,recommend.class);
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
                    Intent intent1= new Intent(checkin.this,quit.class);
                    startActivity(intent1);
                    finish();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailaddress = inputemailaddress.getText().toString().trim();
                String fullname = inputfullname.getText().toString().trim();
                String phone = inputphone.getText().toString().trim();
                if (TextUtils.isEmpty(userId)) {
                    createUser(fullname, emailaddress, phone);
                } else {
                    updateUser(fullname, emailaddress, phone);
                }
            }
        });

        toggleButton();
    }
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnadd.setText("Save");
        } else {
            btnadd.setText("Update");
        }
    }
    private void createUser(String fullname, String emailaddress, String phone) {

        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        User user = new User(fullname, emailaddress, phone);;

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.fullname + ", " + user.emailaddress);

                // Display newly updated name and email

                txtDetails.setText(user.fullname+","+user.emailaddress+","+user.phone);
                // clear edit text
                inputemailaddress.setText("");
                inputfullname.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String fullname, String emailaddress, String phone) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(fullname))
            mFirebaseDatabase.child(userId).child("fullname").setValue(fullname);

        if (!TextUtils.isEmpty(emailaddress))
            mFirebaseDatabase.child(userId).child("emailaddress").setValue(emailaddress);
        if (!TextUtils.isEmpty(phone))
            mFirebaseDatabase.child(userId).child("phoneno").setValue(phone);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(checkin.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(checkin.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }



}