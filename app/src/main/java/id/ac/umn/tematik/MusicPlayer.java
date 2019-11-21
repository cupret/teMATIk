package id.ac.umn.tematik;

import android.app.DownloadManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayer {
    public MediaPlayer mp;
    public DownloadManager dm;
    public ArrayList<File> mySongs;
    private Context ctx;
    private View view;
    public String[] items;
    public int playIndex;
    public boolean isPlay;
    public MusicDownload downloader;
    public boolean canPlay;
    public ArrayList<String> musicNames;

    public MusicPlayer(View view, Context context){
        Log.e("asd", "hai2");
        isPlay = false;
        canPlay = false;
        this.ctx = context;
        this.view = view;
        String[] str = {"weakboi.mp3", "strongboi.mp3"};
        String[] url = {"https://www.androidtutorialpoint.com/wp-content/uploads/2016/09/AndroidDownloadManager.mp3","https://www.androidtutorialpoint.com/wp-content/uploads/2016/09/AndroidDownloadManager.mp3"};
        Log.e("asd", "hai3");
        Log.e("asd", "hai6");
        playIndex = 0;

        String strV = "hMan.mp4";
        String urlV = "https://firebasestorage.googleapis.com/v0/b/tematik-daab8.appspot.com/o/videoplayback_3.mp4?alt=media&token=c9ec771a-083d-4682-b049-ba9e28e10748";


        //downloader
//        downloader = new MusicDownload(view, context);
//        downloader.DownloadSongs(url, str);

//        downloader.DownloadVideo(urlV,strV);

        //
        Log.e("asd", "hai7");
        Log.e("ey",Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    void mpDownloadFromPlaylist(ArrayList<PlayList.Music> playList){
        if(playList!=null && !playList.isEmpty()){
            downloader = new MusicDownload(ctx);
            musicNames = new ArrayList<String>();
            for(int x=0; x<playList.size(); x++){
                downloader.DownloadSong(playList.get(x).getUrl(),nameToMp(playList.get(x).getName()));
                musicNames.add(nameToMp(playList.get(x).getName()));
            }
        }
    }

    void mpStop(){
        mp.stop();
        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void mpPlay(){
        if(!canPlay){
            if(downloader!=null && !downloader.isDownloading()) {
                canPlay = true;

//                checkFiles(musicNames);
                mySongs = findSongs(Environment.getExternalStorageDirectory());

                Log.e("ey","Data: " + mySongs.size());
                items = new String[mySongs.size()];

                for(int i=0;i<mySongs.size();i++){
                    items[i] = mySongs.get(i).getName().replace(".mp3", "");
                    Log.e("e","asd");
                }

                mp = MediaPlayer.create(ctx.getApplicationContext(), Uri.parse(mySongs.get(0).toString()));
                mp.start();
                isPlay = true;
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mpNext();
                    }
                });
            }
        }
        else {
            if (isPlay) {
                mp.pause();
                isPlay = false;
            } else {
                mp.start();
                isPlay = true;
            }
        }
    }
    void mpPause(){
        mp.pause();
    }
    void mpNext(){
        playIndex++;
        mp.release();
        if(playIndex >= mySongs.size()){
            playIndex = 0;
        }
        mp =  MediaPlayer.create(ctx.getApplicationContext(), Uri.parse(mySongs.get(playIndex).toString()));
        isPlay=false;
        mpPlay();
    }
    void mpPrev(){
        mp.release();
        if(playIndex <= 0){
            playIndex = mySongs.size() - 1;
        }
        else{
            playIndex--;
        }
        mp =  MediaPlayer.create(ctx.getApplicationContext(), Uri.parse(mySongs.get(playIndex).toString()));
        isPlay=false;
        mpPlay();
    }

    public ArrayList<File> findSongs(File root){
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden() ){
                al.addAll(findSongs(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3")){
                    al.add(singleFile);
                    Log.e("ey",al.size() + " song : " + singleFile.getName());
                }
            }
        }
        return al;
    }

    public void checkFiles(ArrayList<String> names){
        File[] files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/").listFiles();
        boolean check = false;
        int len = files.length;
        for(int x = 0; x < len ; x++){
            check = false;
            for(int y=0; y < names.size(); y++){
                if(nameToMp(names.get(y)).equals(files[x].getName())){
                    check=true;
                    break;
                }
            }
            if(!check) files[x].delete();
        }
    }

    public String nameToMp(String name){
        if(name.endsWith(".mp3"))return name;
        else return name + ".mp3";
    }
    public String mpToName(String name){
        if(name.endsWith(".mp3"))return name.replace(".mp3","");
        else return name;
    }
    void mpSeek(int prog){
        mp.seekTo(prog);
    }
    public void deleteFiles(){
        File[] files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/").listFiles();
        int len = files.length;
        for(int x = 0; x < len ; x++){
            files[x].delete();
        }
    }



}
