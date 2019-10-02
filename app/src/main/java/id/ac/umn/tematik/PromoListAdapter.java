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
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class PromoListAdapter extends RecyclerView.Adapter<PromoListAdapter.PromoListView> {
    List<Promo> promos = new ArrayList<Promo>();
    private NavController navController;

    public PromoListAdapter(NavController navController){
        this.navController = navController;
    }

    public void SetData(List<Promo> promos){
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new PromoDiffUtil(this.promos, promos), false);
        this.promos = promos;
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public PromoListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_list, parent, false);
        return new PromoListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoListView holder, int position) {
        Log.d("DEBUG", "Normal Bind Promo "+position);
        Promo promo = promos.get(position);
        holder.bind(promo.getId(), promo.getName(), promo.getDescription(), promo.getStart_date(), promo.getImages_url());
    }

    @Override
    public void onBindViewHolder(@NonNull PromoListView holder, int position, @NonNull List<Object> payloads) {
        Promo promo = promos.get(position);
        String name = promo.getName();
        String date = promo.getStart_date();
        String desc = promo.getDescription();
        ArrayList<String> img = promo.getImages_url();
        if(!payloads.isEmpty()){
            Bundle payload = (Bundle) payloads.get(0);
            if(payload.containsKey(PromoDiffUtil.NAME)){
                name = payload.getString(PromoDiffUtil.NAME);
            }

            if(payload.containsKey(PromoDiffUtil.DESCRIPTION)){
                desc = payload.getString(PromoDiffUtil.DESCRIPTION);
            }

            if(payload.containsKey(PromoDiffUtil.DATE)){
                date = payload.getString(PromoDiffUtil.DATE);
            }

            if(payload.containsKey(PromoDiffUtil.IMG)){
                img = payload.getStringArrayList(PromoDiffUtil.IMG);
            }
        }

        holder.bind(promo.getId(), name, date, desc, img);
    }

    @Override
    public int getItemCount() {
        return promos.size();
    }

    class PromoListView extends RecyclerView.ViewHolder{
        View view;
        TextView nameText, descText, dateText;
        ArrayList<String> urls;
        CarouselView promoImg;

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                String url = urls.get(position);
                if(!url.isEmpty())
                    Picasso.get().load(url).into(imageView);
            }
        };

        public PromoListView(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            nameText = itemView.findViewById(R.id.promo_list_title);
            dateText = itemView.findViewById(R.id.promo_list_date);
            descText = itemView.findViewById(R.id.promo_list_desc);
            promoImg = itemView.findViewById(R.id.promo_list_img);
            promoImg.setImageListener(imageListener);
        }

        public void bind(final Integer id, String name, String date, String desc, ArrayList<String> img){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(MainDirections.actionMainToDetailPromo(id));
                }
            });
            nameText.setText(name);
            dateText.setText(date);
            descText.setText(desc);
            urls = img;
            if(!img.isEmpty()){
                promoImg.setPageCount(img.size());
            }

        }
    }
}
