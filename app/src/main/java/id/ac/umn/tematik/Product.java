package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey
    private Integer id;
    private String name;
    private ArrayList<String> images_url;
    private String video_url;
    private Integer price;
    private String description;
    private boolean rodhium_surface;
    private Integer weight_estimation;
    private ArrayList<Spesifikasi_berlian> diamond_specification;

    public Product(
            @NonNull Integer id,
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
    public Integer getPrice(){ return this.price; }
    public ArrayList<String> getImages_url(){ return this.images_url; }
    public String getVideo_url(){ return this.video_url; }
    public String getDescription(){ return this.description; }
    public boolean isRodhium_surface(){ return this.rodhium_surface; }
    public Integer getWeight_estimation(){ return this.weight_estimation; }
    public ArrayList<Spesifikasi_berlian> getDiamond_specification(){ return this.diamond_specification; }

    public class Spesifikasi_berlian{
        private String name;
        private String shape;
        private Integer quantity;
        private Integer carat_weight;
        private boolean cut;
        private String color;
        private String clarity;

        public Spesifikasi_berlian(String name, String shape, Integer quantity, Integer carat_weight, boolean cut, String color, String clarity){
            this.name = name;
            this.shape = shape;
            this.quantity = quantity;
            this.carat_weight = carat_weight;
            this.cut = cut;
            this.color = color;
            this.clarity = clarity;
        }

        public String getName(){ return this.name; }
        public String getShape(){ return this.shape; }
        public Integer getQuantity(){ return this.quantity; }
        public Integer getCarat_weight(){ return this.carat_weight; }
        public boolean isCut(){ return this.cut; }
        public String getColor(){ return this.color; }
        public String getClarity(){ return this.clarity; }
    }
}
