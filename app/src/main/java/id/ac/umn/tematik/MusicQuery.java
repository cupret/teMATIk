package id.ac.umn.tematik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MusicQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Music music);

    @Query("SELECT * FROM music")
    public List<Music> getAllMusic();

    @Query("SELECT * FROM music")
    public LiveData<List<Music>> getAllLiveDataMusic();

    @Query("DELETE FROM music")
    public void deleteAllMusic();
}
