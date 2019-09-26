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
    private String start_date; //start date
    private String end_date; //en_date
    private ArrayList<Integer> product_list;

    public Promo(Integer id, String name, ArrayList<String> images_url, String video_url, String description, String start_date, String end_date, ArrayList<Integer> product_list){
        this.id = id;
        this.name = name;
        this.images_url = images_url;
        this.video_url = video_url;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.product_list = product_list;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.name; }
    public ArrayList<String> getImages_url(){ return this.images_url; }
    public String getVideo_url(){ return this.video_url; }
    public String getDescription(){ return this.description; }
    public String getStart_date(){ return this.start_date; }
    public String getEnd_date(){ return this.end_date; }
    public ArrayList<Integer> getProduct_list(){ return this.product_list; }
}
