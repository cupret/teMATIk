package id.ac.umn.tematik;


import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProduct extends Fragment {
    private String productCode;

    private NavController navController;

    private TextView name;
    private CarouselView produkImg;
    private TextView produkPrice;
    private TextView produkDesc;
    private TextView produkColor;
    private TextView produkWeight;
    private LinearLayout spec;

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

    public DetailProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set toolbar
//        setHasOptionsMenu(true);
//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((MainActivity)getActivity()).setActionBar(toolbar);
//        ((MainActivity)getActivity()).setHomeButton(android.R.drawable.ic_delete);

        // set navController
        navController =  NavHostFragment.findNavController(this);

        // init views
        name = view.findViewById(R.id.detail_product_title);
        produkImg = view.findViewById(R.id.detail_product_list_img);
        produkPrice = view.findViewById(R.id.detail_product_price);
        produkDesc = view.findViewById(R.id.detail_product_desc);
        produkColor = view.findViewById(R.id.detail_product_color);
        produkWeight = view.findViewById(R.id.detail_product_weight);
        spec = view.findViewById(R.id.detail_product_diamond_spec);

        produkImg.setImageListener(imageListener);

        // access produkCode
        productCode = getArguments() != null ? DetailProductArgs.fromBundle(getArguments()).getProductCode() : "";
        final LifecycleOwner owner = this;
        LocalDatabase.getInstance(getContext()).productQuery().getLiveDataProduct(productCode).observe(owner, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                name.setText(product.getName());
                produkPrice.setText("Rp. "+currency(product.getPrice()));
                produkDesc.setText(product.getDescription());
                produkColor.setText("Color: "+product.getColor());
                produkWeight.setText("Weight Est.: "+product.getWeight_estimation()+" gram");

                for(int i =0; i<product.getDiamond_specification().size(); i++){
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 16);


                    Integer textColor = ContextCompat.getColor(getContext(), R.color.white);
                    Typeface typeface1 = ResourcesCompat.getFont(getContext(), R.font.proxima_nova_bold);
                    Typeface typeface2 = ResourcesCompat.getFont(getContext(), R.font.proxima_nova_regular);

                    TextView diamondType = new TextView(getContext());
                    diamondType.setText(product.getDiamond_specification().get(i).getGemType());
                    diamondType.setTextColor(textColor);
                    diamondType.setTextSize(32);
                    diamondType.setTypeface(typeface1);
                    linearLayout.addView(diamondType);

                    TextView diamondCut = new TextView(getContext());
                    diamondCut.setText("Cut: "+product.getDiamond_specification().get(i).getCut());
                    diamondCut.setTextColor(textColor);
                    diamondCut.setTextSize(24);
                    diamondCut.setTypeface(typeface2);
                    linearLayout.addView(diamondCut);

//                    TextView diamondShape = new TextView(getContext());
//                    diamondShape.setText("Shape: "+product.getDiamond_specification().get(i).getShape());
//                    diamondShape.setTextColor(getResources().getColor(R.color.white));
//                    linearLayout.addView(diamondShape);

                    TextView diamondQty = new TextView(getContext());
                    diamondQty.setText("Quantity: "+product.getDiamond_specification().get(i).getQuantity());
                    diamondQty.setTextColor(textColor);
                    diamondQty.setTextSize(24);
                    diamondQty.setTypeface(typeface2);
                    linearLayout.addView(diamondQty);

                    TextView diamondCarat = new TextView(getContext());
                    diamondCarat.setText("Carat Weight: "+product.getDiamond_specification().get(i).getCarat_weight()+" carat");
                    diamondCarat.setTextColor(textColor);
                    diamondCarat.setTextSize(24);
                    diamondCarat.setTypeface(typeface2);
                    linearLayout.addView(diamondCarat);

                    TextView diamondColor = new TextView(getContext());
                    diamondColor.setText("Color: "+product.getDiamond_specification().get(i).getColor());
                    diamondColor.setTextColor(textColor);
                    diamondColor.setTextSize(24);
                    diamondColor.setTypeface(typeface2);
                    linearLayout.addView(diamondColor);

                    TextView diamondClarity = new TextView(getContext());
                    diamondClarity.setText("Clarity: "+product.getDiamond_specification().get(i).getClarity());
                    diamondClarity.setTextColor(textColor);
                    diamondClarity.setTextSize(24);
                    diamondClarity.setTypeface(typeface2);
                    linearLayout.addView(diamondClarity);

                    spec.addView(linearLayout, layoutParams);
                }

                // assign detail promo list img carousel view
                urls = product.getImages_url();
                if(urls != null)
                    produkImg.setPageCount(urls.size());
                else
                    produkImg.setPageCount(0);
            }
        });
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

    public String currency(Integer amount) {
        NumberFormat formatter = new DecimalFormat("#.###");
        return formatter.format(amount);
    }

}
