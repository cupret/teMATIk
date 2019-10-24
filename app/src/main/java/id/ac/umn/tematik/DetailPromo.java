package id.ac.umn.tematik;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPromo extends Fragment {
    private int promoId;

    private ProductListAdapter productListAdapter;
    private GridLayoutManager layoutManager;
    private NavController navController;

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

    private TextView name;
    private TextView dateFrom;
    private TextView dateTo;
    private TextView desc;
    private VideoView vid;
    private RecyclerView imgs;
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

        // set toolbar
        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setActionBar(toolbar);
        ((MainActivity)getActivity()).setHomeButton(android.R.drawable.ic_delete);

        // set navController
        navController =  NavHostFragment.findNavController(this);

        // init views
        name = view.findViewById(R.id.detail_product_title);
        dateFrom = view.findViewById(R.id.detail_promo_date_from);
        dateTo = view.findViewById(R.id.detail_promo_date_to);
        desc = view.findViewById(R.id.detail_promo_desc);
        vid = view.findViewById(R.id.detail_promo_vid);
        imgs = view.findViewById(R.id.detail_promo_imgs);
        promoImg = view.findViewById(R.id.detail_promo_list_img);
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
                name.setText(promo.getName());
                dateFrom.setText("From: " + promo.getStart_date());
                dateTo.setText("To: " + promo.getEnd_date());
                desc.setText(promo.getDescription());

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
            }
        });

        imgs.setLayoutManager(layoutManager);
        imgs.setAdapter(productListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
