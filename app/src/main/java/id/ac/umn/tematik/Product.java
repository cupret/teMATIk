package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey @NonNull
    private String code;
    private String name;
    private ArrayList<String> images_url;
    private String video_url;
    private Integer price;
    private String description;
    private float weight;
    private String metal; //color
    private float purity;
    private ArrayList<DiamondSpecification> diamond_specification;

    public Product(
            @NonNull String code,
            String name,
            ArrayList<String> images_url,
            String video_url,
            Integer price,
            String description,
            float weight,
            String metal,
            float purity,
            ArrayList<DiamondSpecification> diamond_specification
    ){
        this.code = code;
        this.name = name;
        this.images_url = images_url;
        this.video_url = video_url;
        this.price = price;
        this.description = description;
        this.weight = weight;
        this.metal = metal;
        this.purity = purity;
        this.diamond_specification = diamond_specification;
    }

    public String getCode(){ return this.code; }
    public String getName(){ return this.name; }
    public Integer getPrice(){ return this.price; }
    public ArrayList<String> getImages_url(){ return this.images_url; }
    public String getVideo_url(){ return this.video_url; }
    public String getDescription(){ return this.description; }
    public float getWeight(){ return this.weight; }
    public String getMetal(){ return this.metal; }
    public float getPurity(){ return this.purity; }
    public ArrayList<DiamondSpecification> getDiamond_specification(){ return this.diamond_specification; }

    public class DiamondSpecification{
        private String gem_type;
        private Integer quantity;
        private String cut;
        private String color;
        private String clarity;
        private float carat; // float

        public DiamondSpecification(String gem_type, Integer quantity, String cut, String color, String clarity, float carat){
            this.gem_type = gem_type;
            this.quantity = quantity;
            this.carat = carat;
            this.cut = cut;
            this.color = color;
            this.clarity = clarity;
        }

        public String getGemType(){ return this.gem_type; }
        public Integer getQuantity(){ return this.quantity; }
        public float getCarat(){ return this.carat; }
        public String getCut(){ return this.cut; }
        public String getColor(){ return this.color; }
        public String getClarity(){ return this.clarity; }
    }
}
