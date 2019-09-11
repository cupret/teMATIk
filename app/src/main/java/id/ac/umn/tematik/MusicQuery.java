package id.ac.umn.tematik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface MusicQuery {
    @Insert
    public void insert(Music music);

    @Query("SELECT * FROM music")
    public ArrayList<Music> getAllMusic();

    @Query("SELECT * FROM music")
    public LiveData<ArrayList<Music>> getAllLiveDataMusic();

    @Query("DELETE FROM music")
    public void deleteAllMusic();
}
