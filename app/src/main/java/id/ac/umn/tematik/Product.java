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
    private float weight_estimation;
    private String color; //color
    private boolean rodhium_surface; //pelapis besi
    private ArrayList<DiamondSpecification> diamond_specification;

    public Product(
            @NonNull Integer id,
            String name,
            ArrayList<String> images_url,
            String video_url,
            Integer price,
            String description,
            float weight_estimation,
            String color,
            boolean rodhium_surface,
            ArrayList<DiamondSpecification> diamond_specification
    ){
        this.id = id;
        this.name = name;
        this.images_url = images_url;
        this.video_url = video_url;
        this.price = price;
        this.description = description;
        this.weight_estimation = weight_estimation;
        this.color = color;
        this.rodhium_surface = rodhium_surface;
        this.diamond_specification = diamond_specification;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.name; }
    public Integer getPrice(){ return this.price; }
    public ArrayList<String> getImages_url(){ return this.images_url; }
    public String getVideo_url(){ return this.video_url; }
    public String getDescription(){ return this.description; }
    public float getWeight_estimation(){ return this.weight_estimation; }
    public String getColor(){ return this.color; }
    public boolean isRodhium_surface(){ return this.rodhium_surface; }
    public ArrayList<DiamondSpecification> getDiamond_specification(){ return this.diamond_specification; }

    public class DiamondSpecification{
        private String gem_type;
        private String shape;
        private Integer quantity;
        private float carat_weight; // float
        private String cut;
        private String color;
        private String clarity;

        public DiamondSpecification(String gem_type, String shape, Integer quantity, float carat_weight, boolean rodhium_surface, String cut, String color, String clarity){
            this.gem_type = gem_type;
            this.shape = shape;
            this.quantity = quantity;
            this.carat_weight = carat_weight;
            this.cut = cut;
            this.color = color;
            this.clarity = clarity;
        }

        public String getGemType(){ return this.gem_type; }
        public String getShape(){ return this.shape; }
        public Integer getQuantity(){ return this.quantity; }
        public float getCarat_weight(){ return this.carat_weight; }
        public String getCut(){ return this.cut; }
        public String getColor(){ return this.color; }
        public String getClarity(){ return this.clarity; }
    }
}
