package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "playList")
public class PlayList {
    @PrimaryKey
    private Integer id;
    private String start_date;
    private String end_date;
    private ArrayList<Music> musicList;

    public PlayList(@NonNull Integer id, String start_date, String end_date, ArrayList<Music> musicList){
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.musicList = musicList;
    }

    public Integer getId(){ return this.id; }
    public String getStart_date(){ return this.start_date; }
    public String getEnd_date(){ return this.end_date; }
    public ArrayList<Music> getMusicList(){ return this.musicList; }

    public static long stringToDateLong(String str){
        long dateLong = 0;
        try {
            SimpleDateFormat df =  new SimpleDateFormat("dd/MM/yyyy");
            Date date = df.parse(str);
            long oneDay = 1000 * 60 * 60 * 24;
            dateLong = date.getTime() / oneDay;
        } catch (ParseException e) {
            //handle exception
        }
        return dateLong;
    }

    public static ArrayList<Music> getActivePlaylistMusics(ArrayList<PlayList> playLists){
        ArrayList<Music> musics = new ArrayList<>();

        long prevStartDate = 0;
        for(PlayList value: playLists) {
            long start = stringToDateLong(value.getStart_date());
            long end = stringToDateLong(value.getEnd_date());
            long today = stringToDateLong(new Date().toString());
            if(start >= prevStartDate) {
                prevStartDate = start;
                if(start <= today && today <= end) {
                    musics = value.getMusicList();
                }
            }
        }

        return musics;
    }

    public class Music{
        private Integer id;
        private String name;
        private String url;

        public Integer getId(){ return this.id; }
        public String getName(){ return this.name; }
        public String getUrl(){ return this.url; }
    }


}
