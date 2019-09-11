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
    private String nama;
    private ArrayList<String> gambar;
    private String video;
    private String deskripsi;
    private String tanggal;
    private ArrayList<Integer> product_list;

    public Promo(Integer id, String nama, ArrayList<String> gambar, String video, String deskripsi, String tanggal, ArrayList<Integer> product_list){
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.video = video;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.product_list = product_list;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.nama; }
    public ArrayList<String> getGambar(){ return this.gambar; }
    public String getVideo(){ return this.video; }
    public String getDeskripsi(){ return this.deskripsi; }
    public String getTanggal(){ return this.tanggal; }
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
