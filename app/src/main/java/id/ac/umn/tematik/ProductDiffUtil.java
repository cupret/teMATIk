package id.ac.umn.tematik;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ProductDiffUtil extends DiffUtil.Callback {
    public static final String IMG = "IMG";

    private List<Product> oldList;
    private List<Product> newList;

    public ProductDiffUtil(List<Product> oldList, List<Product> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Boolean isSame = oldList.get(oldItemPosition).getId().intValue() == newList.get(newItemPosition).getId().intValue();
        Log.d("DEBUG", "areItemsTheSame: " + isSame.toString());
        return isSame;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Boolean isSame = true;
        if(oldList.get(oldItemPosition).getImages_url() != null && newList.get(newItemPosition).getImages_url() != null){
            if(oldList.get(oldItemPosition).getImages_url().get(0).compareTo(newList.get(newItemPosition).getImages_url().get(0)) != 0)
                isSame = false;
        }
        Log.d("DEBUG", "areContentsTheSame: " + isSame.toString());
        return isSame;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Bundle bundle = new Bundle();
        if(oldList.get(oldItemPosition).getImages_url() != null && newList.get(newItemPosition).getImages_url() != null){
            if(oldList.get(oldItemPosition).getImages_url().get(0).compareTo(newList.get(newItemPosition).getImages_url().get(0)) != 0)
                bundle.putString(IMG, newList.get(newItemPosition).getImages_url().get(0));
        }

        return bundle;
    }
}
