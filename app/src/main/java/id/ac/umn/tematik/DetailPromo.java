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

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TextView date;
    private TextView desc;
    private VideoView vid;
    private RecyclerView imgs;

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
        name = view.findViewById(R.id.detail_promo_title);
        date = view.findViewById(R.id.detail_promo_date);
        desc = view.findViewById(R.id.detail_promo_desc);
        vid = view.findViewById(R.id.detail_promo_vid);
        imgs = view.findViewById(R.id.detail_promo_imgs);

        // init recycle list
        layoutManager = new GridLayoutManager(getContext(), 3);
        productListAdapter = new ProductListAdapter(navController);

        // access promoId
        promoId = getArguments() != null ? DetailPromoArgs.fromBundle(getArguments()).getPromoId() : 0;
        final LifecycleOwner owner = this;
        LocalDatabase.getInstance(getContext()).promoQuery().getLiveDataPromo(promoId).observe(owner, new Observer<Promo>() {
            @Override
            public void onChanged(Promo promo) {
                name.setText(promo.getName());
                date.setText(promo.getStart_date() + " - " + promo.getEnd_date());
                desc.setText(promo.getDescription());

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
