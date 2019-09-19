package id.ac.umn.tematik;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PromoListAdapter extends RecyclerView.Adapter<PromoListAdapter.PromoListView> {
    List<Promo> promos = new ArrayList<Promo>();

    public PromoListAdapter(){}

    public void SetData(List<Promo> promos){
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new PromoDiffUtil(this.promos, promos), true);
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
        holder.bind(promo.getName(), promo.getDescription(), promo.getDate(), promo.getImages_url().get(0));
    }

    @Override
    public void onBindViewHolder(@NonNull PromoListView holder, int position, @NonNull List<Object> payloads) {
        Promo promo = promos.get(position);
        String name = promo.getName();
        String date = promo.getDate();
        String desc = promo.getDescription();
        String img = promo.getImages_url().get(0);
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
                img = payload.getString(PromoDiffUtil.IMG);
            }
        }

        holder.bind(name, date, desc, img);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PromoListView extends RecyclerView.ViewHolder{
        TextView nameText, descText, dateText;
        ImageView promoImg;

        public PromoListView(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.promo_list_title);
            dateText = itemView.findViewById(R.id.promo_list_date);
            descText = itemView.findViewById(R.id.promo_list_desc);
            promoImg = itemView.findViewById(R.id.promo_list_img);
        }

        public void bind(String name, String date, String desc, String img){
            nameText.setText(name);
            dateText.setText(date);
            descText.setText(desc);

        }
    }
}
