package id.ac.umn.tematik;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ProductDiffUtil extends DiffUtil.Callback {
    public static final String IMG = "IMG";
    public static final String TITLE = "TITLE";
    public static final String PRICE = "PRICE";

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
        Boolean isSame = oldList.get(oldItemPosition).getCode().compareTo(newList.get(newItemPosition).getCode()) == 0;
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
        if(oldList.get(oldItemPosition).getName().compareTo(newList.get(newItemPosition).getName()) != 0) isSame = false;
        if(oldList.get(oldItemPosition).getPrice() != newList.get(newItemPosition).getPrice()) isSame = false;

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

        if(oldList.get(oldItemPosition).getName().compareTo(newList.get(newItemPosition).getName()) != 0) bundle.putString(TITLE, newList.get(newItemPosition).getName());
        if(oldList.get(oldItemPosition).getPrice() != newList.get(newItemPosition).getPrice()) bundle.putInt(PRICE, newList.get(newItemPosition).getPrice());

        return bundle;
    }
}
