package id.ac.umn.tematik;

import android.util.SparseArray;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Product product);

    @Query("SELECT * FROM product")
    public List<Product> getAllProduct();

    @Query("SELECT * FROM product")
    public LiveData<List<Product>> getAllLiveDataProduct();

    @Query("SELECT * FROM product WHERE code = :code")
    public Product getProduct(String code);

    @Query("SELECT * FROM product WHERE code = :code")
    public LiveData<Product> getLiveDataProduct(String code);

    @Query("DELETE FROM product")
    public void deleteAllProduct();
}
