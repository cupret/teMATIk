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

@Entity(tableName = "product")
public class Product {
    @PrimaryKey
    @NonNull
    private Integer id;
    private String name;

    @TypeConverters(GambarConverter.class)
    private ArrayList<String> images_url;

    private String video_url;
    private Integer price;
    private String description;
    private boolean rodhium_surface;
    private Integer weight_estimation;

    @TypeConverters(SpesifikasiBerlianConverter.class)
    private ArrayList<Spesifikasi_berlian> diamond_specification;

    public Product(
            Integer id,
            String name,
            ArrayList<String> images_url,
            String video_url,
            Integer price,
            String description,
            boolean rodhium_surface,
            Integer weight_estimation,
            ArrayList<Spesifikasi_berlian> diamond_specification
    ){
        this.id = id;
        this.name = name;
        this.images_url = images_url;
        this.video_url = video_url;
        this.price = price;
        this.description = description;
        this.rodhium_surface = rodhium_surface;
        this.weight_estimation = weight_estimation;
        this.diamond_specification = diamond_specification;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.name; }
    public ArrayList<String> getImagesUrl(){ return this.images_url; }
    public String getVideoUrl(){ return this.video_url; }
    public String getDescription(){ return this.description; }
    public boolean isRodhiumSurface(){ return this.rodhium_surface; }
    public Integer getWeightEstimation(){ return this.weight_estimation; }
    public ArrayList<Spesifikasi_berlian> getDiamondSpecification(){ return this.diamond_specification; }

    public class Spesifikasi_berlian{
        private String name;
        private String shape;
        private Integer quantity;
        private Integer carat_weight;
        private boolean cut;
        private String color;
        private String clarity;

        public Spesifikasi_berlian(String nam3, String shape, Integer quantity, Integer carat_weight, boolean cut, String color, String clarity){
            this.name = name;
            this.shape = shape;
            this.quantity = quantity;
            this.carat_weight = weight_estimation;
            this.cut = cut;
            this.color = color;
            this.clarity = clarity;
        }

        public String getName(){ return this.name; }
        public String getShape(){ return this.shape; }
        public Integer getQuantity(){ return this.quantity; }
        public Integer getCaratWeight(){ return this.carat_weight; }
        public boolean isCut(){ return this.cut; }
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
