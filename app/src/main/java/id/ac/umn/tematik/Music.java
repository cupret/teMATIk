package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music")
public class Music {
    @PrimaryKey
    @NonNull
    private Integer id;
    public String nama;
    public String url;

    public Music(Integer id, String nama, String url){
        this.id = id;
        this.nama = nama;
        this.url = url;
    }

    public Integer getId(){ return this.id; }
    public String getName(){ return this.nama; }
    public String getUrl(){ return this.url; }
}
