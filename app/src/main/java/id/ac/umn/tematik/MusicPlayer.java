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
    private static MusicPlayer musicPlayer;
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
    public boolean isMute = false;

    public static MusicPlayer getInstance(){

        return musicPlayer;

    }
    public static MusicPlayer getInstance(View view, Context context){
        if(musicPlayer == null){
            musicPlayer = new MusicPlayer(view, context);
        }
        return musicPlayer;

    }


    public MusicPlayer(View view, Context context){
        Log.e("asd", "hai2");
        isPlay = false;
        canPlay = false;
        this.ctx = context;
        this.view = view;
        playIndex = 0;


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

                checkFiles(musicNames);
                mySongs = findSongs(Environment.getExternalStorageDirectory());

                Log.e("ey","Data: " + mySongs.size());
                items = new String[mySongs.size()];

                for(int i=0;i<mySongs.size();i++){
                    items[i] = mySongs.get(i).getName().replace(".mp3", "");
                    Log.e("asdasd",mySongs.get(i).getName());
                }

                mp = MediaPlayer.create(ctx.getApplicationContext(), Uri.parse(mySongs.get(0).toString()));
                try{
                    mp.start();
                }catch(Exception e){
                    Log.e("e", "fukkin file is html");
                }
                isPlay = true;
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.e("song", "song next pls");
                        mpPlay();
                        mpNext();
                    }
                });
                mp.setVolume(15,15);
            }
        }
        else {
            if (isPlay) {
                if(mp!=null)mp.pause();
                isPlay = false;
            } else {
                try{
                    if(mp == null) mp = MediaPlayer.create(ctx.getApplicationContext(), Uri.parse(mySongs.get(playIndex).toString()));
                    mp.start();
                }catch(Exception e){
                    Log.e("e", "fukkin file is html");
                    mpNext();
                    mpPlay();
                }
                isPlay = true;
                changeVolume(15);
            }
        }
    }
    void mpPause(){
        isPlay = false;
        mp.pause();
    }
    void mpNext(){
        playIndex++;
        if(playIndex >= mySongs.size()){
            playIndex = 0;
        }

        if(mp!=null)mp.release();
        else return;
        mp =  MediaPlayer.create(ctx.getApplicationContext(), Uri.parse(mySongs.get(playIndex).toString()));
        isPlay=false;
        mpPlay();
    }
    void mpPrev(){
        if(playIndex <= 0){
            playIndex = mySongs.size() - 1;
        }
        else{
            playIndex--;
        }
        if(mp!=null)mp.release();
        else return;
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
        name = name.replace(" ","");
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

    public void changeMusicIndex(int index){
        Log.e("e", ""+index);
        playIndex = index-1;
        mpNext();
    }
    public void changeMusicTitle(String title){
        for(int x = 0; x < mySongs.size(); x++){
//            Log.e("e",title.replace(" ", "") + " and " + mySongs.get(x).getName());
            if(nameToMp(title).equals(mySongs.get(x).getName())){
                changeMusicIndex(x);
                break;
            }
        }
    }
    public void changeVolume(float vol){
        mp.setVolume(vol, vol);
    }

}
