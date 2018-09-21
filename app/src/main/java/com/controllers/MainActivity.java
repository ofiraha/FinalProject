package com.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.samples.vision.ocrreader.OcrCaptureActivity;
import com.google.android.gms.samples.vision.ocrreader.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        checkCameraPermission();
    }

    public void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    public void openToReadListActivity(View view)
    {
        Intent openToReadListActivity = new Intent(this, BookListActivity.class);
        // openToReadListActivity.putExtra("KEY_TEXT", 5);
        startActivity(openToReadListActivity);
    }


    public void openOcrActivity(View view) {
        Intent openOcrActivity = new Intent(this, OcrCaptureActivity.class);
        //openOcrActivity.putExtra("KEY_TEXT", 5);
        startActivity(openOcrActivity);
    }

    public void openVideo(View view) {
        Intent openOcrActivity = new Intent(this, VideoActivity.class);
        startActivity(openOcrActivity);
    }

}
