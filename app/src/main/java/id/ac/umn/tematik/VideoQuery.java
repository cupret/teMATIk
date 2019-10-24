package id.ac.umn.tematik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VideoQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Video video);

    @Query("SELECT * FROM video")
    public List<Video> getVideo();

    @Query("SELECT * FROM video")
    public LiveData<List<Video>> getLiveDataVideo();

    @Query("DELETE FROM video")
    public void deleteAllVideo();
}