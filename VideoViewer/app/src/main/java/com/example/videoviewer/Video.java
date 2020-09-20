package com.example.videoviewer;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        Intent it = getIntent();
        String video = it.getStringExtra("it_video");
        Resources res = getResources();

        TextView tv = findViewById(R.id.title);
        VideoView vv = findViewById(R.id.video);

        int id_title = res.getIdentifier(
                "title_" + video, "string", getPackageName()
        );
        String title = res.getString(id_title);
        tv.setText(title);
        tv.setPadding(0,50,0, 50);

        // get resource video id & path
        int rawId = res.getIdentifier(video, "raw", getPackageName());
        String path = "android.resource://" + getPackageName() + "/raw/" + rawId;

        vv.setVideoPath(path);

        // Activity 실행 시 Video 실행
        vv.start();

        // Add Video controller
        vv.setMediaController(new MediaController(this));
    }

    /*
    * Close Activity
    * */
    public void closeVideo(View v) {
        finish();
    }
}
