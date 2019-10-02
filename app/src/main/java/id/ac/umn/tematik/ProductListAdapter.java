package id.ac.umn.tematik;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
        holder.bind(product.getId(), product.getImages_url().get(0));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductListView holder, int position, @NonNull List<Object> payloads) {
        Product product = products.get(position);
        String img = product.getImages_url() != null ? product.getImages_url().get(0) : "";
        if(!payloads.isEmpty()){
            Bundle payload = (Bundle) payloads.get(0);
            if(payload.containsKey(PromoDiffUtil.IMG)){
                img = payload.getString(PromoDiffUtil.IMG);
            }
        }

        holder.bind(product.getId(), img);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductListView extends RecyclerView.ViewHolder{
        View view;
        ImageView productImg;

        public ProductListView(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            productImg = itemView.findViewById(R.id.product_list_img);
        }

        public void bind(final Integer id, String img){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //navController.navigate(MainDirections.actionMainToDetailPromo(id));
                }
            });
            if(!img.isEmpty())
                Picasso.get().load(img).into(productImg);
        }
    }
}
