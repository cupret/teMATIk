package id.ac.umn.tematik;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playList")
public class PlayList {
    @PrimaryKey
    private Integer id;
    private String start_date;
    private String end_date;

    public PlayList(@NonNull Integer id, String start_date, String end_date){
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Integer getId(){ return this.id; }
    public String getStart_date(){ return this.start_date; }
    public String getEnd_date(){ return this.end_date; }

    public class Music{
        private Integer id;
        private String name;
        private String url;

        public Integer getId(){ return this.id; }
        public String getName(){ return this.name; }
        public String getUrl(){ return this.url; }
    }


}
