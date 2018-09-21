package com.controllers;

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
    private Button mBtnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

//        mWevView = (WebView) findViewById(R.id.web_view);
//        playVideo("1");


        mVideoView = (VideoView) findViewById(R.id.video_view);
        mBtnPlay = (Button)findViewById(R.id.play_btn);

        String str ="https://firebasestorage.googleapis.com/v0/b/booksproject-41fe3.appspot.com/o/library%2FThe%20Whale%20Who%20Ate%20Everything.mp4?alt=media&token=641dd415-ee5c-4cce-8db3-3b9aba512e43";
        Uri uri = Uri.parse(str);

        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
            }
        });
    }

    public void playVideo(String urlId) {
        String url = "bSMZknDI6bg";
        mWevView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + url +"\" frameborder=\"0\" allowfullscreen></iframe>",
                "text/html" , "utf-8");
    }
}
