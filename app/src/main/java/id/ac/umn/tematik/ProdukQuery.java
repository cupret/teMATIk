package id.ac.umn.tematik;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface ProdukQuery {
    @Insert
    public void insert(Produk produk);

    @Query("SELECT * FROM produk")
    public ArrayList<Produk> getAllProduk();

    @Query("SELECT * FROM produk")
    public LiveData<ArrayList<Produk>> getAllLiveDataProduk();

    @Query("DELETE FROM produk")
    public void deleteAllProduk();
}
