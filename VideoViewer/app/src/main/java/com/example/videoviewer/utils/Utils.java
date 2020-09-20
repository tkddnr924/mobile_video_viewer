package com.example.videoviewer.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Utils {
    Resources res;
    String packageName;
    Context context;


    /*
    * onClick interface
    * */
    public interface CallBack {
        void displayVideo(View v);
    }

    /*
    * init Utils
    * @param resource               getResources()
    * @param name                   getPackageName()
    * @param ApplicationContext     getApplicationContext()
    * */
    public Utils(Resources resource, String name, Context ApplicationContext) {
        res = resource;
        packageName = name;
        context = ApplicationContext;
    }

    /*
     * Get Video Thumbnail
     * @param video     a Video file name
     * @return Bitmap
     * */
    private Bitmap getThumbnail(String video) {
        // get resource video id & path
        int rawId = res.getIdentifier(video, "raw", packageName);
        String path = "android.resource://" + packageName + "/raw/" + rawId;

        // get Thumbnail from video
        Uri uri = Uri.parse(path);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, uri);
        Bitmap bitmap =  retriever.getFrameAtTime(
                10, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC
        );

        return ThumbnailUtils.extractThumbnail(bitmap, 350, 200);
    }

    /*
     * Convert int to real dp value
     * @param dp     LayoutParams "dp"
     * @return int   Real dp value
     * */
    private int getDP(int dp) {
        final float scale = res.getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /*
     * Create TextView
     * @param video      a Video file name
     * @param name       Resource id value ex) "title_" or "place_"
     * @return TextView
     * */
    public TextView createTextView(String video, String name) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tv.setPadding(30, 0,0,10);
        tv.setText(res.getIdentifier(
                name + video, "string", packageName)
        );
        return tv;
    }

    /*
     * Create ImageView
     * @param video      a Video file name
     * @return ImageView
     * */
    public ImageView createImageView(String video) {
        ImageView iv = new ImageView(context);
        Bitmap thumbnail = getThumbnail(video);

        iv.setLayoutParams(new LinearLayout.LayoutParams(
                getDP(3), ViewGroup.LayoutParams.WRAP_CONTENT, 1
        ));
        iv.setImageBitmap(thumbnail);
        return iv;
    }

    /*
     * Create LinearLayout
     * @param video      a video file name
     * @param clickable  LinearLayout Clickable Flag
     * @return LinearLayout
     * */
    public LinearLayout createLayout(String video, Boolean clickable, final CallBack func) {
        LinearLayout layout = new LinearLayout(context);

        if (clickable) {
            layout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout.setId(
                    res.getIdentifier(video, "string", packageName)
            );
            layout.setPadding(0,0,0, 10);
            layout.setTag(video);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setClickable(true);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    func.displayVideo(view);
                }
            });
        } else {
            layout.setLayoutParams(new LinearLayout.LayoutParams(
                    getDP(0), ViewGroup.LayoutParams.WRAP_CONTENT, 2
            ));
            layout.setOrientation(LinearLayout.VERTICAL);
        }

        return layout;
    }
}
