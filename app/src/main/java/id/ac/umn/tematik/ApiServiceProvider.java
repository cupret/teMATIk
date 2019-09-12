package id.ac.umn.tematik;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceProvider{
    private static ApiServiceProvider apiServiceProvider;
    private ApiService apiService;
    static final String API_URL = "https://tematik-daab8.firebaseio.com/";

    public ApiServiceProvider(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static ApiServiceProvider getInstance(){
        if(apiServiceProvider == null){
            apiServiceProvider = new ApiServiceProvider();
        }

        return apiServiceProvider;
    }

    public void update(Context context){
        loadProduct(context);
        loadPromo(context);
        loadPlayList(context);
    }

    public void loadProduct(final Context context){
        Call<ArrayList<Product>> call = apiService.loadProduct();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        ArrayList<Product> products = response.body();
                        for(int x=0; x<products.size(); x++){
                            Log.i("diamond", "Perhiasan nama: "+ products.get(x).getName());
                            Log.i("diamond", "Perhiasan spesifikasi nama: "+ products.get(x).getDiamond_specification().get(0).getName());
                            LocalDatabase.getInstance(context).productQuery().insert(products.get(x));
                        }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
            }
        });
    }

    private void loadPromo(final Context context){
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
                            LocalDatabase.getInstance(context).promoQuery().insert(promos.get(x));
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

    private void loadPlayList(final Context context){
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
                            LocalDatabase.getInstance(context).musicQuery().insert(playList.get(x));
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

