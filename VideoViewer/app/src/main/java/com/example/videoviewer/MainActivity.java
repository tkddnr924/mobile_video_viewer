package com.example.videoviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.videoviewer.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // util class
        Utils util = new Utils(getResources(), getPackageName(), getApplicationContext());

        // Layout 자동 생성
        String[] videos = getResources().getStringArray(R.array.videos);
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.main_layout);

        for(String video: videos) {
            LinearLayout layout = util.createLayout(video, true, new Utils.CallBack() {
                @Override
                public void displayVideo(View v) {
                    MainActivity.this.displayVideo(v);
                }
            });
            ImageView iv = util.createImageView(video);

            LinearLayout sub_layout = util.createLayout(video, false, null);
            TextView tv_title = util.createTextView(video, "title_");
            TextView tv_place = util.createTextView(video, "place_");

            sub_layout.addView(tv_title);
            sub_layout.addView(tv_place);

            layout.addView(iv);
            layout.addView(sub_layout);

            main_layout.addView(layout);
        }
    }

    /*
    * Click Video
    * Show Detail Video Activity
    * @param v
    * @return
    * */
    public void displayVideo(View v) {
        // get id
        int id = v.getId();
        LinearLayout layout = (LinearLayout) v.findViewById(id);
        String tag = (String) layout.getTag();

        // Intent 생성 후 클릭한 비디오 sub activity 실행
        Intent it = new Intent(this, Video.class);
        it.putExtra("it_video", tag);
        startActivity(it);
    }
}