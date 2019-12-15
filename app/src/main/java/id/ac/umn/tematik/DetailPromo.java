package id.ac.umn.tematik;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPromo extends Fragment {
    private int promoId;

    private ProductListAdapter productListAdapter;
    private GridLayoutManager layoutManager;
    private NavController navController;

    final DisplayMetrics metrics = new DisplayMetrics();

    private HashMap<String, LiveData<Product>> productsLiveData = new HashMap<>(0);
    private HashMap<String, Product> products = new HashMap(0);
    private Observer<Product> productObserver = new Observer<Product>() {
        @Override
        public void onChanged(Product product) {
            // assign value to products and notify productListAdapter
            products.put(product.getCode(), product);

            ArrayList<Product> newProducts = new ArrayList<>();
            for(Product xxx: products.values()){
                newProducts.add(xxx);
            }

            productListAdapter.SetData(newProducts);
            productListAdapter.notifyDataSetChanged();
        }
    };

    private ImageButton backButton;
    private TextView name;
    private TextView dateFrom;
    private TextView dateTo;
    private TextView desc;
    private LinearLayout cont_vid;
    private VideoView vid;
    private RecyclerView product_imgs;
    private CarouselView promoImg;

    private ArrayList<String> urls;
    private ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            String url = urls.get(position);
            if(!url.isEmpty()) {
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Picasso.get().load(url).into(imageView);
            }
        }
    };

    public DetailPromo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_promo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WindowManager windowManager = ((Activity) getContext()).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(metrics);

//        // set toolbar
//        setHasOptionsMenu(true);
//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((MainActivity)getActivity()).setActionBar(toolbar);
//        ((MainActivity)getActivity()).setHomeButton(android.R.drawable.ic_delete);

        // set navController
        navController =  NavHostFragment.findNavController(this);

        // init views
        backButton = view.findViewById(R.id.button_back);
        name = view.findViewById(R.id.detail_promo_title);
        dateFrom = view.findViewById(R.id.detail_promo_date_from);
        dateTo = view.findViewById(R.id.detail_promo_date_to);
        desc = view.findViewById(R.id.detail_promo_desc);
        cont_vid = view.findViewById(R.id.detail_promo_cont_vid);
        vid = view.findViewById(R.id.detail_promo_vid);
        product_imgs = view.findViewById(R.id.detail_promo_imgs);
        promoImg = view.findViewById(R.id.detail_promo_list_img);
        promoImg.getLayoutParams().height = (int)(metrics.heightPixels); //height carousell

        promoImg.setImageListener(imageListener);

        // init recycle list
        layoutManager = new GridLayoutManager(getContext(), 4);
        productListAdapter = new ProductListAdapter(navController);

        // access promoId
        promoId = getArguments() != null ? DetailPromoArgs.fromBundle(getArguments()).getPromoId() : 0;
        final LifecycleOwner owner = this;
        LocalDatabase.getInstance(getContext()).promoQuery().getLiveDataPromo(promoId).observe(owner, new Observer<Promo>() {
            @Override
            public void onChanged(Promo promo) {
                name.setText(promo.getName().toUpperCase());
                dateFrom.setText(promo.getStart_date());
                dateTo.setText(promo.getEnd_date());

                desc.setText(promo.getDescription());
                desc.setTypeface(ResourcesCompat.getFont(getContext(), R.font.neutra_text_demi_italic));

                // assign detail promo list img carousel view
                urls = promo.getImages_url();
                if(urls != null)
                    promoImg.setPageCount(urls.size());
                else
                    promoImg.setPageCount(0);

                // will display video if url exist
                if(promo.getVideo_url().isEmpty()) {
                    vid.setVisibility(View.GONE);
                } else {
                    vid.setVisibility(View.VISIBLE);
                }

                // update products livedata observer
                for(LiveData<Product> product: productsLiveData.values()){
                    product.removeObserver(productObserver);
                }

                productsLiveData.clear();
                for(String code : promo.getProduct_code_list()){
                    LiveData<Product> liveData = LocalDatabase.getInstance(getContext()).productQuery().getLiveDataProduct(code);
                    liveData.observe(owner, productObserver);
                    productsLiveData.put(code, liveData);
                }

                //init video
                final MediaController media_controller = new MediaController(getContext());
                vid.setMediaController(media_controller);
                media_controller.setVisibility(View.GONE);

                vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setVolume(0,0);
                        mp.setLooping(true);
                    }
                });
                vid.setVisibility(View.INVISIBLE);
                cont_vid.setVisibility(View.INVISIBLE);

                new DownloadVideo(getContext()).execute(promoId+"", promo.getVideo_url());
            }
        });

        product_imgs.setLayoutManager(layoutManager);
        product_imgs.setAdapter(productListAdapter);

        // back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                getActivity().onBackPressed();
//                return  true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private class DownloadVideo extends AsyncTask<String, Integer, String> {

        private Context context;
        private DownloadManager dm;

        public DownloadVideo (Context context){ this.context = context; }

        @Override
        protected String doInBackground(String... args) {

            if(args[1] != null) {
                File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video");
                if (!folder.exists()) folder.mkdirs();

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/promo_" + args[0]);
                if(!file.exists()){
                    Log.e("viddPromo", "!exist");
                    try {
                        URL url = new URL(args[1]);
                        url.openConnection().connect();

                        InputStream input = new BufferedInputStream(url.openStream());
                        OutputStream output = new FileOutputStream(file);

                        byte data[] = new byte[1024]; int count;
                        while ((count = input.read(data)) != -1) output.write(data, 0, count);

                        output.flush();
                        output.close();
                        input.close();

                    } catch (Exception e) {
                        Log.e("Error: ", e.getMessage());
                    }
                }
                else{
                    Log.e("viddPromo", "file already exist");
                }

                return "promo_" + args[0];
            }
            else {
                Log.i("viddPromo", "no download");
                return "nyeh";
            }
        }

        @Override
        protected void onPostExecute(String name) {
            super.onPostExecute(name);

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Video/" + name);
            if(file.exists()) {
                vid.setVideoURI(Uri.parse(file.toString()));
                vid.setVisibility(View.VISIBLE);
                cont_vid.setVisibility(View.VISIBLE);
                vid.start();
                Log.i("viddPromo", "get video " + name);
            } else {
                vid.setVisibility(View.INVISIBLE);
                Log.i("viddPromo", "no video");
            }

        }
    }

}
