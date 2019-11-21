package id.ac.umn.tematik;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MusicDownload {
    public DownloadManager dm;
    private Context ctx;
    public int songDownloading;


    public MusicDownload(Context context){
        this.ctx = context;
        songDownloading = 0;
        ctx.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            // your code
            songDownloading--;
            Log.e("download", "download complete bruh " + songDownloading );
            if(songDownloading == 0){
                ctx.unregisterReceiver(onComplete);
            }
        }
    };

    public boolean isDownloading(){
        if(songDownloading == 0){
//            ctx.unregisterReceiver(onComplete);
            return false;
        }
        else return true;
    }

    public void DownloadSong(Uri uri, String name){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/" + name);
        if(!file.exists()){
            DownloadManager.Request request = new DownloadManager.Request(uri)
                    .setTitle("Test Download")// Title of the Download Notification
                    .setDescription("Downloading")// Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationInExternalPublicDir("/Music", name)
                    .setRequiresCharging(false)// Set if charging is required to begin the download
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
            dm = (DownloadManager)ctx.getSystemService(ctx.DOWNLOAD_SERVICE);
            dm.enqueue(request);
            songDownloading++;
        }
        else{
            Log.e("download", "file already exist");
        }
    }

    public void DownloadSong(String url, String name){
        Log.e("asd", "hai5");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/" + name);
        if(!file.exists()){
            Log.e("download", "!exist");
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                    .setTitle("Test Download")// Title of the Download Notification
                    .setDescription("Downloading")// Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationInExternalPublicDir("/Music", name)
                    .setRequiresCharging(false)// Set if charging is required to begin the download
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
            dm = (DownloadManager)ctx.getSystemService(ctx.DOWNLOAD_SERVICE);
            dm.enqueue(request);
            songDownloading++;
        }
        else{
            Log.e("download", "file already exist");
        }
    }

    public void DownloadSongs(String[] url, String[] name){
        Log.e("asd", "hai4");
        for(int x=0; x<name.length;x++){
            DownloadSong(url[x], name[x]);
        }
    }

    public void DownloadSongs(Uri[] uri, String[] name){
        for(int x=0; x<name.length;x++){
            DownloadSong(uri[x], name[x]);
        }
    }

    public void DownloadVideo(String url, String name){
        Log.e("asd", "hai5");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/" + name);
        if(!file.exists()){
            Log.e("download", "!exist");
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                    .setTitle("Test Download")// Title of the Download Notification
                    .setDescription("Downloading")// Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationInExternalPublicDir("/Video", name)
                    .setRequiresCharging(false)// Set if charging is required to begin the download
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
            dm = (DownloadManager)ctx.getSystemService(ctx.DOWNLOAD_SERVICE);
            dm.enqueue(request);
            songDownloading++;
        }
        else{
            Log.e("download", "file already exist");
        }

    }


}
