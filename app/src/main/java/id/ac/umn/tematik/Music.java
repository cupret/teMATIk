package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music")
public class Music {
    @PrimaryKey
    private Integer id;
    private String name;
    private String url;
    private String start_date;
    private String end_date;

    public Music(@NonNull Integer id, String name, String url, String start_date, String end_date){
        this.id = id;
        this.name = name;
        this.url = url;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.name; }
    public String getUrl(){ return this.url; }
    public String getStart_date(){ return this.start_date; }
    public String getEnd_date(){ return this.end_date; }
}
