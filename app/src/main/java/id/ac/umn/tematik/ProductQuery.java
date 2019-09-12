package id.ac.umn.tematik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface ProductQuery {
    @Insert
    public void insert(Product product);

    @Query("SELECT * FROM Product")
    public ArrayList<Product> getAllProduct();

    @Query("SELECT * FROM Product")
    public LiveData<ArrayList<Product>> getAllLiveDataProduct();

    @Query("DELETE FROM Product")
    public void deleteAllProduct();
}
