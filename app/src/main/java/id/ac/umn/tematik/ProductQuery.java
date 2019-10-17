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

    @Query("SELECT * FROM product WHERE id = :id")
    public Product getProduct(int id);

    @Query("SELECT * FROM product WHERE id = :id")
    public LiveData<Product> getLiveDataProduct(int id);

    @Query("SELECT * FROM product WHERE :where")
    public LiveData<List<Product>> fukYuRyan(String where);

    @Query("DELETE FROM product")
    public void deleteAllProduct();
}
