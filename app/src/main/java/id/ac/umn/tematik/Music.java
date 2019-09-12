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

    public Music(@NonNull Integer id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.name; }
    public String getUrl(){ return this.url; }
}
