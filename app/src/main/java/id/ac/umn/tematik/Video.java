package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "video")
public class Video {
    @PrimaryKey @NonNull
    private String name;
    private String video_url;

    public Video(String name, String video_url){
        this.name = name;
        this.video_url = video_url;
    }

    public String getName() { return name; }
    public String getVideo_url(){ return video_url; }
}
