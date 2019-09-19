package id.ac.umn.tematik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PromoQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Promo promo);

    @Query("SELECT * FROM promo")
    public List<Promo> getAllPromo();

    @Query("SELECT * FROM promo")
    public LiveData<List<Promo>> getAllLiveDataPromo();

    @Query("SELECT * FROM promo  WHERE id = :id")
    public Promo getPromo(int id);

    @Query("SELECT * FROM promo  WHERE id = :id")
    public LiveData<Promo> getLiveDataPromo(int id);

    @Query("DELETE FROM promo")
    public void deleteAllPromo();
}
