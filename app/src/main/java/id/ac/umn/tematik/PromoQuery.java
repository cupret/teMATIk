package id.ac.umn.tematik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface PromoQuery {
    @Insert
    public void insert(Promo promo);

    @Query("SELECT * FROM promo")
    public ArrayList<Promo> getAllPromo();

    @Query("SELECT * FROM promo")
    public LiveData<ArrayList<Promo>> getAllLiveDataPromo();

    @Query("DELETE FROM promo")
    public void deleteAllPromo();
}
