package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSIONS_WRITE_STORAGE= 1;

    private Context mContext;

    private Handler handler = new Handler();
    private Integer timer = 0, stop = 60;
    private boolean idle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

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
            initVideoPlayer(mContext);
        }

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
                    initVideoPlayer(mContext);
                } else {
                    Log.e("asd", "stroage not granted bro");
                    finishAndRemoveTask();
                }
            }
            return;
        }
    }

    private class DownloadVideo extends AsyncTask<Void, Integer, String> {

        private Context context;
        private DownloadManager dm;

        final VideoView vv = findViewById(R.id.videoView);

        public DownloadVideo (Context context){ this.context = context; }

        @Override
        protected String doInBackground(Void... voids) {

            List<Video> videos = LocalDatabase.getInstance(context).videoQuery().getVideo();
            Log.i("vidd",videos.toString());
            if(videos != null && videos.size() > 0 && videos.get(0) != null) {
                Video video = videos.get(0);
                Log.i("vidd", " try download "+video.getName());

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/" + video.getName());
                if(!file.exists()){
                    Log.e("vidd", "!exist");
                    try {
                        URL url = new URL(video.getVideo_url());
                        url.openConnection().connect();

                        InputStream input = new BufferedInputStream(url.openStream());
                        OutputStream output = new FileOutputStream(file);

                        byte data[] = new byte[1024]; int count;
                        while ((count = input.read(data)) != -1) output.write(data, 0, count);

                        output.flush();
                        output.close();
                        input.close();

                    } catch (Exception e) {
                        Log.e("Error: ", e.getMessage());
                    }
                }
                else{
                    Log.e("vidd", "file already exist");
                }

                return video.getName();
            }
            else {
                Log.i("vidd", "no download");
                return "nyeh";
            }
        }

        @Override
        protected void onPostExecute(String name) {
            super.onPostExecute(name);

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/" + name);
            if(file.exists()) {
                vv.setVideoURI(Uri.parse(file.toString()));
                Log.i("vidd", "get video " + name);
            } else Log.i("vidd", "no video");

        }
    }

    public void initVideoPlayer(final Context mContext){
        Log.i("vidd", "init videoPlayer");
        final VideoView vv = findViewById(R.id.videoView);
        final MediaController media_controller = new MediaController(mContext);
        vv.setMediaController(media_controller);

        vv.setVisibility(View.VISIBLE);
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        vv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("vidd", "click video");
                vv.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        vv.setVisibility(View.INVISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(timer < stop) {
                    timer +=1;
                    Log.i("timer", timer.toString());
                }
                else if(!idle){
                    vv.setVisibility(View.VISIBLE);
                    vv.start();
                    idle = true;
                    Log.i("timer", "idle");
                }
                handler.postDelayed(this, 1000);
            }
        },  1000);

        LocalDatabase.getInstance(this).videoQuery().getLiveDataVideo().observe(this, new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> videos) {
                new DownloadVideo(mContext).execute();
            }
        });
    }

    @Override
    public void onUserInteraction(){
        timer = 0;
        idle = false;
    }

}
