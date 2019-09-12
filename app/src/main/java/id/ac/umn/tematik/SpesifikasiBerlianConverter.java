package id.ac.umn.tematik;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SpesifikasiBerlianConverter {
    @TypeConverter
    public String fromSpesifikasiBerlian(ArrayList<Product.Spesifikasi_berlian> spesifikasiBerlian){
        if(spesifikasiBerlian == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Product.Spesifikasi_berlian>>(){}.getType();
        return gson.toJson(spesifikasiBerlian, type);
    }

    @TypeConverter
    public ArrayList<Product.Spesifikasi_berlian> toSpesifikasiBerlian(String spesifikasiBerlian){
        if(spesifikasiBerlian == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Product.Spesifikasi_berlian>>(){}.getType();
        return gson.fromJson(spesifikasiBerlian, type);
    }
}
