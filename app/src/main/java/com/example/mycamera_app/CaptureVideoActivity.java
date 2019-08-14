package com.example.mycamera_app;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class CaptureVideoActivity extends AppCompatActivity {

    private VideoView mvideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_video);
        mvideoView = findViewById(R.id.videoView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Uri videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));
        mvideoView.setVideoURI(videoUri);
        mvideoView.start();



    }


}
