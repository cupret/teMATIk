package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    public ArrayList<String> getImages_url(){ return this.images_url; }
    public String getVideo_url(){ return this.video_url; }
    public String getDescription(){ return this.description; }
    public String getDate(){ return this.date; }
    public ArrayList<Integer> getProduct_list(){ return this.product_list; }
}
