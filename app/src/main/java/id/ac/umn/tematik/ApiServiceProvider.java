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
    private ApiService apiService;

    static final String API_URL = "https://my-json-server.typicode.com/starfallproduction/mockdata/";

//    public void start() {
    public ApiServiceProvider(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void getProduk(){
        Call<ArrayList<Produk>> call = apiService.loadProduk();
        call.enqueue(new Callback<ArrayList<Produk>>() {
            @Override
            public void onResponse(Call<ArrayList<Produk>> call, Response<ArrayList<Produk>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        ArrayList<Produk> produks = response.body();
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

    public void getPromo(){
        Call<ArrayList<Promo>> call = apiService.loadPromo();
        call.enqueue(new Callback<ArrayList<Promo>>() {
            @Override
            public void onResponse(Call<ArrayList<Promo>> call, Response<ArrayList<Promo>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        ArrayList<Promo> promos = response.body();
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

    public void getPlayList(){
        Call<ArrayList<Music>> call = apiService.loadPlayList();
        call.enqueue(new Callback<ArrayList<Music>>() {
            @Override
            public void onResponse(Call<ArrayList<Music>> call, Response<ArrayList<Music>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        ArrayList<Music> playList = response.body();
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

