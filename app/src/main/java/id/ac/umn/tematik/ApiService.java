package id.ac.umn.tematik;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("produk") Call<ArrayList<Produk>> loadProduk();
}

