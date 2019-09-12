package id.ac.umn.tematik;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class IntegerListConverter {
    @TypeConverter
    public String fromJson(ArrayList<Integer> integerList){
        if(integerList == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        return gson.toJson(integerList, type);
    }

    @TypeConverter
    public ArrayList<Integer> toIntegerList(String json){
        if(json == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
