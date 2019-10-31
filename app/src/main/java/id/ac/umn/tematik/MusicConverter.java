package id.ac.umn.tematik;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MusicConverter {
    @TypeConverter
    public String fromMusic(ArrayList<PlayList.Music> musics){
        if(musics == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<PlayList.Music>>(){}.getType();
        return gson.toJson(musics, type);
    }

    @TypeConverter
    public ArrayList<PlayList.Music> toMusic(String musics){
        if(musics == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<PlayList.Music>>(){}.getType();
        return gson.fromJson(musics, type);
    }
}
