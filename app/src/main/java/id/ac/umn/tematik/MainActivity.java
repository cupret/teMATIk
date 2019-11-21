package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSIONS_WRITE_STORAGE= 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finishAndRemoveTask();
        }

        //ask permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("asd", "req stroage granted bro");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE_STORAGE);
        } else {
            Log.e("asd", "stroage already granted bro");
            setContentView(R.layout.activity_main);
        }

        final Context context = this;
        LocalDatabase.getInstance(this).videoQuery().getLiveDataVideo().observe(this, new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> videos) {
                // check if video available
                // video only 1 so only check video[0]
                if(videos != null && videos.get(0) != null) {
                    Video video = videos.get(0);
                    MusicDownload downloader = new MusicDownload(context);
                    downloader.DownloadVideo(video.getVideo_url(), video.getName());
                }
            }
        });
    }

    public void setActionBar(Toolbar toolbar){
        setSupportActionBar(toolbar);
    }

    public void setHomeButton(int icon){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(icon);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_WRITE_STORAGE : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("asd", "stroage granted bro");
                    setContentView(R.layout.activity_main);
                } else {
                    Log.e("asd", "stroage not granted bro");
                    finishAndRemoveTask();
                }
            }
            return;
        }
    }
    public void playIdleVideo(){
//        View view;
//        ImageButton vid = view.findViewById(R.id.fragment_main_openplaylist);
//        final VideoView vv = view.findViewById(R.id.videoView);
//        final MediaController media_controller = new MediaController(this.getContext());
//        vv.setMediaController(media_controller);
//        File idv = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/" + "hMan.mp4");
//        vv.setVideoURI(Uri.parse(idv.toString()));
//        vv.setVisibility(View.VISIBLE);
//        vv.start();
//        vv.setVisibility(View.INVISIBLE);

    }
}
