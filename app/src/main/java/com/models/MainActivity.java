package com.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.samples.vision.ocrreader.OcrCaptureActivity;
import com.google.android.gms.samples.vision.ocrreader.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openOcrActivity(View view) {
        Intent openOcrActivity = new Intent(this, OcrCaptureActivity.class);
        //openOcrActivity.putExtra("KEY_TEXT", 5);
        startActivity(openOcrActivity);
    }
}
