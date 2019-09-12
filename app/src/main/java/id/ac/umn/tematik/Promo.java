package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Entity(tableName = "promo")
public class Promo {
    @PrimaryKey
    @NonNull
    private Integer id;
    private String name;
    private ArrayList<String> images_url;
    private String video_url;
    private String description;
    private String date;
    private ArrayList<Integer> product_list;

    public Promo(Integer id, String name, ArrayList<String> images_url, String video_url, String description, String date, ArrayList<Integer> product_list){
        this.id = id;
        this.name = name;
        this.images_url = images_url;
        this.video_url = video_url;
        this.description = description;
        this.date = date;
        this.product_list = product_list;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.name; }
    public ArrayList<String> getImagesUrl(){ return this.images_url; }
    public String getVideoUrl(){ return this.video_url; }
    public String getDescription(){ return this.description; }
    public String getDate(){ return this.date; }
    public ArrayList<Integer> getProductList(){ return this.product_list; }

    public class GambarConverter{
        @TypeConverter
        public String fromGambar(ArrayList<String> gambar){
            if(gambar == null) return null;
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            return gson.toJson(gambar, type);
        }

        @TypeConverter
        public ArrayList<String> toGambar(String gambar){
            if(gambar == null) return null;
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            return gson.fromJson(gambar, type);
        }
    }

    public class ProdukListConverter{
        @TypeConverter
        public String fromProdukList(ArrayList<Integer> produkList){
            if(produkList == null) return null;
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
            return gson.toJson(produkList, type);
        }

        @TypeConverter
        public ArrayList<Integer> toProdukList(String produkList){
            if(produkList == null) return null;
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
            return gson.fromJson(produkList, type);
        }
    }
}
