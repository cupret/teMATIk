package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music")
public class Music {
    @PrimaryKey
    @NonNull
    private Integer id;
    public String name;
    public String url;

    public Music(Integer id, String nama, String url){
        this.id = id;
        this.name = nama;
        this.url = url;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.name; }
    public String getUrl(){ return this.url; }
}
