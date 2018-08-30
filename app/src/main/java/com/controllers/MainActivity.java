package com.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.samples.vision.ocrreader.OcrCaptureActivity;
import com.google.android.gms.samples.vision.ocrreader.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
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
}
