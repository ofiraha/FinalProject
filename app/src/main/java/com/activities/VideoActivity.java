package com.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.VideoView;

import com.google.android.gms.samples.vision.ocrreader.R;

public class VideoActivity extends AppCompatActivity {

    private WebView mWevView;
    private VideoView mVideoView;
    private Button mBtnPlay, mBtnPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mVideoView = (VideoView) findViewById(R.id.video_view);
        mBtnPlay = (Button)findViewById(R.id.play_btn);
        mBtnPause = (Button)findViewById(R.id.pause_btn);

        Intent intent = getIntent();
        String str = intent.getStringExtra("uri");
        Uri uri = Uri.parse(str);

        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
            }
        });
        mBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.pause();
            }
        });
    }

    public void playVideo(String urlId) {
        String url = "bSMZknDI6bg";
        mWevView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + url +"\" frameborder=\"0\" allowfullscreen></iframe>",
                "text/html" , "utf-8");
    }
}
