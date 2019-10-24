package id.ac.umn.tematik;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("product.json") Call<ArrayList<Product>> loadProduct();

    @GET("promo.json") Call<ArrayList<Promo>> loadPromo();

    @GET("playlist.json") Call<ArrayList<PlayList>> loadPlayList();

    @GET("idleVideo.json") Call<Video> loadIdleVideo();
}

