package id.ac.umn.tematik;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceProvider{
    private static ApiService apiService;
    private static ArrayList<Produk> produks;
    private static ArrayList<Promo> promos;
    private static ArrayList<Music> playList;

    static final String API_URL = "https://my-json-server.typicode.com/starfallproduction/mockdata/";

    public ApiServiceProvider(){}

    public static void init(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getInstance(){
        if(apiService == null) init();
        return apiService;
    }

    public void update(){
        loadProduk();
        loadPromo();
    }


    public static ArrayList<Produk> getProduk(){
        if(produks == null) loadProduk();
        return produks;
    }

    public static void loadProduk(){
        Call<ArrayList<Produk>> call = apiService.loadProduk();
        call.enqueue(new Callback<ArrayList<Produk>>() {
            @Override
            public void onResponse(Call<ArrayList<Produk>> call, Response<ArrayList<Produk>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        produks = response.body();
                        for(int x=0; x<produks.size(); x++){
                            Log.i("diamond", "Perhiasan nama: "+ produks.get(x).getName());
                            Log.i("diamond", "Perhiasan spesifikasi nama: "+ produks.get(x).getSpesifikasiBerlian().get(0).getName());
                        }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Produk>> call, Throwable t) {
            }
        });
    }


    public static ArrayList<Promo> getPromo(){
        if(promos == null) loadPromo();
        return promos;
    }

    private static void loadPromo(){
        Call<ArrayList<Promo>> call = apiService.loadPromo();
        call.enqueue(new Callback<ArrayList<Promo>>() {
            @Override
            public void onResponse(Call<ArrayList<Promo>> call, Response<ArrayList<Promo>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        promos = response.body();
                        for(int x=0; x<promos.size(); x++){
                            Log.i("promo", "Promo nama: "+ promos.get(x).getName());
                        }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Promo>> call, Throwable t) {

            }
        });
    }

    public static ArrayList<Music> getPlayList(){
        if(playList == null) loadPlayList();
        return playList;
    }

    public static void loadPlayList(){
        Call<ArrayList<Music>> call = apiService.loadPlayList();
        call.enqueue(new Callback<ArrayList<Music>>() {
            @Override
            public void onResponse(Call<ArrayList<Music>> call, Response<ArrayList<Music>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        playList = response.body();
                        for(int x=0; x<playList.size(); x++){
                            Log.i("playlist", "Music nama: "+ playList.get(x).getName());
                        }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Music>> call, Throwable t) {

            }
        });
    }

}

