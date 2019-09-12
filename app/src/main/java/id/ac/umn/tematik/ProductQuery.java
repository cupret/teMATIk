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

    @Query("SELECT * FROM product")
    public ArrayList<Product> getAllProduct();

    @Query("SELECT * FROM product")
    public LiveData<ArrayList<Product>> getAllLiveDataProduct();

    @Query("DELETE FROM product")
    public void deleteAllProduct();
}
