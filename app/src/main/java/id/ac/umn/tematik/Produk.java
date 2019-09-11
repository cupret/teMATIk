package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Entity(tableName = "produk")
public class Produk {
    @PrimaryKey
    @NonNull
    private Integer id;
    private String nama;

    @TypeConverters(GambarConverter.class)
    private ArrayList<String> gambar;

    private String video;
    private String deskripsi;
    private boolean lapisan_rodhium;
    private Integer estimasi_berat;

    @TypeConverters(SpesifikasiBerlianConverter.class)
    private ArrayList<Spesifikasi_berlian> spesifikasi_berlian;

    public Produk(
            Integer id,
            String nama,
            ArrayList<String> gambar,
            String deskripsi,
            boolean lapisan_rodhium,
            Integer estimasi_berat,
            ArrayList<Spesifikasi_berlian> spesifikasi_berlian
    ){
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.lapisan_rodhium = lapisan_rodhium;
        this.estimasi_berat = estimasi_berat;
        this.spesifikasi_berlian = spesifikasi_berlian;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.nama; }
    public ArrayList<String> getGambar(){ return this.gambar; }
    public String getVideo(){ return this.video; }
    public String getDeskripsi(){ return this.deskripsi; }
    public boolean getLapisanRodhium(){ return this.lapisan_rodhium; }
    public Integer getSstimasiBerat(){ return this.estimasi_berat; }
    public ArrayList<Spesifikasi_berlian> getSpesifikasiBerlian(){ return this.spesifikasi_berlian; }

    public class Spesifikasi_berlian{
        private String nama;
        private String bentuk;
        private Integer jumlah;
        private Integer berat_carat;
        private boolean cut;
        private String color;
        private String clarity;

        public Spesifikasi_berlian(String nama, String bentuk, Integer jumlah, Integer berat_carat, boolean cut, String color, String clarity){
            this.nama = nama;
            this.bentuk = bentuk;
            this.jumlah = jumlah;
            this.berat_carat = berat_carat;
            this.cut = cut;
            this.color = color;
            this.clarity = clarity;
        }

        public String getName(){ return this.nama; }
        public String getBentuk(){ return this.bentuk; }
        public Integer getJumlah(){ return this.jumlah; }
        public Integer getBeratCarat(){ return this.berat_carat; }
        public boolean getCut(){ return this.cut; }
        public String getColor(){ return this.color; }
        public String getClarity(){ return this.clarity; }
    }


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
    public class SpesifikasiBerlianConverter{
        @TypeConverter
        public String fromSpesifikasiBerlian(ArrayList<Spesifikasi_berlian> spesifikasiBerlian){
            if(spesifikasiBerlian == null) return null;
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Spesifikasi_berlian>>(){}.getType();
            return gson.toJson(spesifikasiBerlian, type);
        }

        @TypeConverter
        public ArrayList<Spesifikasi_berlian> toSpesifikasiBerlian(String spesifikasiBerlian){
            if(spesifikasiBerlian == null) return null;
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Spesifikasi_berlian>>(){}.getType();
            return gson.fromJson(spesifikasiBerlian, type);
        }
    }

}
