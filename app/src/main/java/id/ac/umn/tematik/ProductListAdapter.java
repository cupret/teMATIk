package id.ac.umn.tematik;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListView>  {
    List<Product> products = new ArrayList<Product>();
    private NavController navController;

    public ProductListAdapter(NavController navController){
        this.navController = navController;
    }

    public void SetData(List<Product> products){
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ProductDiffUtil(this.products, products), false);
        this.products = products;
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ProductListAdapter.ProductListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new ProductListAdapter.ProductListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductListView holder, int position) {
        Log.d("DEBUG", "Normal Bind Promo "+position);
        Product product = products.get(position);
        holder.bind(product.getCode(), product.getImages_url().get(0), product.getName(), product.getPrice());
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductListView holder, int position, @NonNull List<Object> payloads) {
        Product product = products.get(position);
        String img = product.getImages_url() != null ? product.getImages_url().get(0) : "";
        String title = product.getName();
        Integer price = product.getPrice();
        if(!payloads.isEmpty()){
            Bundle payload = (Bundle) payloads.get(0);
            if(payload.containsKey(ProductDiffUtil.IMG)){
                img = payload.getString(ProductDiffUtil.IMG);
            }
            if(payload.containsKey(ProductDiffUtil.TITLE)){
                title = payload.getString(ProductDiffUtil.TITLE);
            }
            if(payload.containsKey(ProductDiffUtil.PRICE)){
                price = payload.getInt(ProductDiffUtil.PRICE);
            }
        }

        holder.bind(product.getCode(), img, title, price);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductListView extends RecyclerView.ViewHolder{
        View view;
        ImageView productImg;
        TextView productTitle;
        TextView productPrice;

        public ProductListView(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            productImg = itemView.findViewById(R.id.product_list_img);
            productTitle = itemView.findViewById(R.id.detail_product_title);
            productPrice = itemView.findViewById(R.id.detail_product_price);
        }

        public void bind(final String code, String img, String title, Integer price){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(DetailPromoDirections.actionDetailPromoToDetailProduct(code));
                }
            });
            if(!img.isEmpty()) Picasso.get().load(img).into(productImg);
            productTitle.setText(title);
            productPrice.setText("IDR "+ currency(price));
        }
    }

    public String currency(Integer amount) {
        NumberFormat formatter = new DecimalFormat("#.###");
        return formatter.format(amount);
    }
}
