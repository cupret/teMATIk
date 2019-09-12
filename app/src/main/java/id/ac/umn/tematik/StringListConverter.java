package id.ac.umn.tematik;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StringListConverter {
    @TypeConverter
    public String fromJson(ArrayList<String> stringList){
        if(stringList == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return gson.toJson(stringList, type);
    }

    @TypeConverter
    public ArrayList<String> toStringList(String json){
        if(json == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
