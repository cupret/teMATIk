package id.ac.umn.tematik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayListQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(PlayList playList);

    @Query("SELECT * FROM playList")
    public List<PlayList> getAllPlayList();

    @Query("SELECT * FROM playList")
    public LiveData<List<PlayList>> getAllLiveDataPlayList();

    @Query("DELETE FROM playList")
    public void deleteAllPlayList();
}
