package id.ac.umn.tematik;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPromo extends Fragment {
    private int promoId;

    private ProductListAdapter productListAdapter;
    private GridLayoutManager layoutManager;
    private NavController navController;

    TextView name;
    TextView date;
    TextView desc;
    VideoView vid;
    RecyclerView imgs;

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
        LocalDatabase.getInstance(getContext()).promoQuery().getLiveDataPromo(promoId).observe(this, new Observer<Promo>() {
            @Override
            public void onChanged(Promo promo) {
                name.setText(promo.getName());
                date.setText(promo.getStart_date() + " - " + promo.getEnd_date());
                desc.setText(promo.getDescription());

                ArrayList<Product> products = new ArrayList<>();
                for(int id : promo.getProduct_list()){
                    products.add(LocalDatabase.getInstance(getContext()).productQuery().getProduct(id));
                }

                productListAdapter.SetData(products);
                productListAdapter.notifyDataSetChanged();
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
